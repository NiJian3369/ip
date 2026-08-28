package alice;

import java.util.Scanner;

public class Alice {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/alice.txt");
        TaskList tasks = new TaskList(storage.load());
        Scanner scanner = new Scanner(System.in);
        String input;

        ui.showWelcome();

        while (true) {
            input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    ui.showGoodbye();
                    break;

                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = Parser.parseTodoDescription(input);
                    tasks.add(new ToDos(description));
                    ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                    storage.save(tasks.getAll());

                } else if (input.startsWith("deadline ")) {
                    Deadlines deadline = Parser.parseDeadline(input);
                    tasks.add(deadline);
                    ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                    storage.save(tasks.getAll());

                } else if (input.startsWith("event")) {
                    Events event = Parser.parseEvent(input);
                    tasks.add(event);
                    ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                    storage.save(tasks.getAll());

                } else if (input.equals("list")) {
                    ui.showTaskList(tasks);

                } else if (input.startsWith("mark ")) {
                    int index = Parser.parseIndex(input, 5);
                    if (!tasks.isValidIndex(index)) {
                        throw new AliceException("That task number doesn't exist!");
                    }
                    tasks.get(index).markAsDone();
                    ui.showMarked(tasks.get(index));
                    storage.save(tasks.getAll());

                } else if (input.startsWith("unmark ")) {
                    int index = Parser.parseIndex(input, 7);
                    if (!tasks.isValidIndex(index)) {
                        throw new AliceException("That task number doesn't exist!");
                    }
                    tasks.get(index).markAsNotDone();
                    ui.showUnmarked(tasks.get(index));
                    storage.save(tasks.getAll());

                } else if (input.startsWith("delete ")) {
                    int index = Parser.parseIndex(input, 7);
                    if (!tasks.isValidIndex(index)) {
                        throw new AliceException("That task number doesn't exist!");
                    }
                    Task removedTask = tasks.remove(index);
                    ui.showDeleted(removedTask, tasks.size());
                    storage.save(tasks.getAll());

                } else {
                    tasks.add(new Task(input));
                    ui.showPlainAdded(input);
                    storage.save(tasks.getAll());
                }
            } catch (AliceException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showInvalidNumber();
            }
        }
    }
}