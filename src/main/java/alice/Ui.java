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
        return print("Got it. I've added this task:"
                + "\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays the full list of tasks currently in the task list.
     *
     * @param tasks the task list to display.
     * @return the formatted task list.
     */
    public String showTaskList(TaskList tasks) {
        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }
        return print(message.toString());
    }

    /**
     * Displays a confirmation message after a task has been marked as done.
     *
     * @param task the task that was marked.
     * @return the formatted confirmation message.
     */
    public String showMarked(Task task) {
        return print("Nice! I've marked this task as done:"
                + "\n  " + task);
    }

    /**
     * Displays a confirmation message after a task has been marked as not done.
     *
     * @param task the task that was unmarked.
     * @return the formatted confirmation message.
     */
    public String showUnmarked(Task task) {
        return print("OK, I've marked this task as not done yet:"
                + "\n  " + task);
    }

    /**
     * Displays a confirmation message after a task has been deleted.
     *
     * @param task the task that was removed.
     * @param remainingCount the number of tasks remaining after deletion.
     * @return the formatted confirmation message.
     */
    public String showDeleted(Task task, int remainingCount) {
        return print("Noted. I've removed this task:"
                + "\n  " + task
                + "\nNow you have " + remainingCount + " tasks in the list.");
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
     * Displays the list of tasks matching a search keyword.
     *
     * @param matches the list of matching tasks to display.
     * @return the formatted list of matching tasks.
     */
    public String showFoundTasks(ArrayList<Task> matches) {
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            message.append("\n").append(i + 1).append(".").append(matches.get(i));
        }
        return print(message.toString());
    }

    /**
     * Prints a message to the console and returns it unchanged, so that
     * callers can send the same text to both the console and the GUI.
     *
     * @param message the message to print.
     * @return the same message, for reuse by the caller.
     */
    private String print(String message) {
        System.out.println(message);
        return message;
    }
}
