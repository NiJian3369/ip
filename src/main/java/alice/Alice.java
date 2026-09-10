package alice;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point and command dispatcher for the Alice chatbot.
 *
 * <p>An {@code Alice} instance owns the {@link Ui}, {@link Storage} and
 * {@link TaskList} it needs, and exposes {@link #getResponse(String)} as the
 * single place where a line of user input is parsed and executed. Both the
 * text UI (the {@link #main} command loop below) and the JavaFX GUI (see
 * {@link MainWindow}) call this same method, so the two front ends can never
 * drift apart in behaviour.
 */
public class Alice {
    private static final String DEFAULT_STORAGE_PATH = "./data/alice.txt";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs an Alice chatbot backed by the default data file.
     */
    public Alice() {
        this(DEFAULT_STORAGE_PATH);
    }

    /**
     * Constructs an Alice chatbot backed by the given data file, loading
     * any tasks already saved there.
     *
     * @param storageFilePath path to the file used to persist tasks.
     */
    public Alice(String storageFilePath) {
        this.ui = new Ui();
        this.storage = new Storage(storageFilePath);
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Runs the Alice chatbot as a text-based command-line application.
     * Reads user input from the console in a loop and prints Alice's
     * replies, until the user types "bye".
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Alice alice = new Alice();
        Scanner scanner = new Scanner(System.in);

        alice.ui.showWelcome();
        while (true) {
            String input = scanner.nextLine();
            alice.getResponse(input);
            if (alice.isExit(input)) {
                break;
            }
        }
    }

    /**
     * Returns Alice's fixed greeting, for front ends (such as the GUI) that
     * want to show it without going through {@link #getResponse}.
     *
     * @return the greeting message.
     */
    public String getGreeting() {
        return ui.showWelcome();
    }

    /**
     * Returns whether the given input should end the chatbot session.
     *
     * @param input the raw user input.
     * @return true if the input is the exit command "bye".
     */
    public boolean isExit(String input) {
        return input.equals("bye");
    }

    /**
     * Parses and executes a single line of user input, returning Alice's
     * reply as a String so it can be printed (text UI) or shown in a dialog
     * box (GUI).
     *
     * @param input the raw line of user input.
     * @return Alice's reply to the input.
     */
    public String getResponse(String input) {
        try {
            if (input.isBlank()) {
                // Guards against silently saving a task with an empty
                // description (e.g. from pressing Enter on an empty line),
                // which previously produced a corrupted line in the data
                // file that crashed loading on the next run.
                throw new AliceException("I didn't quite catch that - please type a command.");

            } else if (input.equals("bye")) {
                return ui.showGoodbye();

            } else if (input.equals("todo") || input.startsWith("todo ")) {
                String description = Parser.parseTodoDescription(input);
                tasks.add(new Todo(description));
                return saveAndRespond(ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size()));

            } else if (input.startsWith("deadline ")) {
                Deadline deadline = Parser.parseDeadline(input);
                tasks.add(deadline);
                return saveAndRespond(ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size()));

            } else if (input.startsWith("event")) {
                Event event = Parser.parseEvent(input);
                tasks.add(event);
                return saveAndRespond(ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size()));

            } else if (input.equals("list")) {
                return ui.showTaskList(tasks);

            } else if (input.equals("find") || input.startsWith("find ")) {
                String[] keywords = Parser.parseFindKeywords(input);
                ArrayList<Task> matches = tasks.find(keywords);
                return ui.showFoundTasks(matches);

            } else if (input.startsWith("mark ")) {
                int zeroBasedIndex = parseValidIndex(input, 5);
                tasks.get(zeroBasedIndex).markAsDone();
                return saveAndRespond(ui.showMarked(tasks.get(zeroBasedIndex)));

            } else if (input.startsWith("unmark ")) {
                int zeroBasedIndex = parseValidIndex(input, 7);
                tasks.get(zeroBasedIndex).markAsNotDone();
                return saveAndRespond(ui.showUnmarked(tasks.get(zeroBasedIndex)));

            } else if (input.startsWith("delete ")) {
                int zeroBasedIndex = parseValidIndex(input, 7);
                Task removedTask = tasks.remove(zeroBasedIndex);
                return saveAndRespond(ui.showDeleted(removedTask, tasks.size()));

            } else {
                tasks.add(new Task(input));
                return saveAndRespond(ui.showPlainAdded(input));
            }
        } catch (AliceException e) {
            return ui.showError(e.getMessage());
        } catch (NumberFormatException e) {
            return ui.showInvalidNumber();
        }
    }

    /**
     * Parses a zero-based task index from a command string and checks that
     * it refers to an existing task, so that every command needing an
     * index (mark, unmark, delete) validates it the same way.
     *
     * @param input the full raw user input, e.g. "mark 3".
     * @param prefixLength the number of characters before the index number.
     * @return the validated zero-based index.
     * @throws AliceException if the index does not refer to an existing task.
     * @throws NumberFormatException if the remaining text is not a valid number.
     */
    private int parseValidIndex(String input, int prefixLength) throws AliceException {
        int zeroBasedIndex = Parser.parseIndex(input, prefixLength);
        if (!tasks.isValidIndex(zeroBasedIndex)) {
            throw new AliceException("That task number doesn't exist!");
        }
        return zeroBasedIndex;
    }

    /**
     * Persists the current task list to storage and returns the given reply
     * unchanged, so every command that mutates the task list can save and
     * respond in one line instead of repeating both steps.
     *
     * @param reply the reply to return after saving.
     * @return the same reply that was passed in.
     */
    private String saveAndRespond(String reply) {
        storage.save(tasks.getAllTasks());
        return reply;
    }
}
