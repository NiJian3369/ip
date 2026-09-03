package alice;

import java.util.ArrayList;

/**
 * Handles all interactions with the user.
 *
 * <p>Every {@code show*} method here prints its message to the console (for
 * the text UI) <em>and</em> returns that same message as a String. This lets
 * the JavaFX GUI (see {@link MainWindow}) display the exact same wording in
 * a dialog box, without duplicating the message-formatting logic in two
 * places.
 */
public class Ui {
    /**
     * Displays the welcome message shown when the program starts.
     *
     * @return the welcome message.
     */
    public String showWelcome() {
        return print("Good day mate! Alice here! What can I do for ya today?");
    }

    /**
     * Displays the goodbye message shown when the user exits the program.
     *
     * @return the goodbye message.
     */
    public String showGoodbye() {
        return print("Good bye mate, I'm off! See ya around!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display.
     * @return the formatted error message.
     */
    public String showError(String message) {
        return print("OOPS!!! " + message);
    }

    /**
     * Displays an error message for when the user enters an invalid
     * (non-numeric) task number.
     *
     * @return the formatted error message.
     */
    public String showInvalidNumber() {
        return print("OOPS!!! Please enter a valid task number.");
    }

    /**
     * Displays a confirmation message after a task has been added.
     *
     * @param task the task that was added.
     * @param taskCount the total number of tasks after adding.
     * @return the formatted confirmation message.
     */
    public String showTaskAdded(Task task, int taskCount) {
        return print("Got it. I've added this task:",
                "  " + task,
                "Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays the full list of tasks currently in the task list.
     *
     * @param tasks the task list to display.
     * @return the formatted task list.
     */
    public String showTaskList(TaskList tasks) {
        String[] lines = new String[tasks.size() + 1];
        lines[0] = "Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            lines[i + 1] = (i + 1) + "." + tasks.get(i);
        }
        return print(lines);
    }

    /**
     * Displays a confirmation message after a task has been marked as done.
     *
     * @param task the task that was marked.
     * @return the formatted confirmation message.
     */
    public String showMarked(Task task) {
        return print("Nice! I've marked this task as done:", "  " + task);
    }

    /**
     * Displays a confirmation message after a task has been marked as not done.
     *
     * @param task the task that was unmarked.
     * @return the formatted confirmation message.
     */
    public String showUnmarked(Task task) {
        return print("OK, I've marked this task as not done yet:", "  " + task);
    }

    /**
     * Displays a confirmation message after a task has been deleted.
     *
     * @param task the task that was removed.
     * @param remainingCount the number of tasks remaining after deletion.
     * @return the formatted confirmation message.
     */
    public String showDeleted(Task task, int remainingCount) {
        return print("Noted. I've removed this task:",
                "  " + task,
                "Now you have " + remainingCount + " tasks in the list.");
    }

    /**
     * Displays a message for input that did not match any recognized
     * command, treated as a plain task addition.
     *
     * @param input the raw user input that was added as a task.
     * @return the formatted confirmation message.
     */
    public String showPlainAdded(String input) {
        return print("added: " + input);
    }

    /**
     * Displays the list of tasks matching a search.
     *
     * @param matches the list of matching tasks to display.
     * @return the formatted list of matching tasks.
     */
    public String showFoundTasks(ArrayList<Task> matches) {
        String[] lines = new String[matches.size() + 1];
        lines[0] = "Here are the matching tasks in your list:";
        for (int i = 0; i < matches.size(); i++) {
            lines[i + 1] = (i + 1) + "." + matches.get(i);
        }
        return print(lines);
    }

    /**
     * Prints one or more lines to the console, joined with newlines, and
     * returns the joined text so callers can send the exact same content to
     * the GUI. Passing a single line behaves exactly as it did before this
     * method took varargs, since Java wraps a lone argument into a
     * one-element array automatically.
     *
     * @param lines the line(s) making up the message.
     * @return the lines joined into a single message.
     */
    private String print(String... lines) {
        String message = String.join("\n", lines);
        System.out.println(message);
        return message;
    }
}
