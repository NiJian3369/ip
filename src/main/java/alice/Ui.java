package alice;

import java.util.ArrayList;

/**
 * Handles all interactions with the user, including displaying messages,
 * task updates, and error notifications to the console.
 */

public class Ui {
    /**
     * Displays the welcome message shown when the program starts.
     */
    public void showWelcome() {
        System.out.println("Good day mate! Alice here! What can I do for ya today?");
    }

    /**
     * Displays the goodbye message shown when the user exits the program.
     */
    public void showGoodbye() {
        System.out.println("Good bye mate, I'm off! See ya around!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display.
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Displays an error message for when the user enters an invalid
     * (non-numeric) task number.
     */
    public void showInvalidNumber() {
        System.out.println("OOPS!!! Please enter a valid task number.");
    }

    /**
     * Displays a confirmation message after a task has been added.
     *
     * @param task the task that was added.
     * @param taskCount the total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays the full list of tasks currently in the task list.
     *
     * @param tasks the task list to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a confirmation message after a task has been marked as done.
     *
     * @param task the task that was marked.
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays a confirmation message after a task has been marked as not done.
     *
     * @param task the task that was unmarked.
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays a confirmation message after a task has been deleted.
     *
     * @param task the task that was removed.
     * @param remainingCount the number of tasks remaining after deletion.
     */
    public void showDeleted(Task task, int remainingCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingCount + " tasks in the list.");
    }

    /**
     * Displays a message for input that did not match any recognized
     * command, treated as a plain task addition.
     *
     * @param input the raw user input that was added as a task.
     */
    public void showPlainAdded(String input) {
        System.out.println("added: " + input);
    }

    /**
     * Displays the list of tasks matching a search keyword.
     *
     * @param matches the list of matching tasks to display.
     */
    public void showFoundTasks(ArrayList<Task> matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
    }
}