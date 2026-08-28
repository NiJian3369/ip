package alice;

public class Ui {
    public void showWelcome() {
        System.out.println("Good day mate! alice.Alice here! What can I do for ya today?");
    }

    public void showGoodbye() {
        System.out.println("Good bye mate, I'm off! See ya around!");
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public void showInvalidNumber() {
        System.out.println("OOPS!!! Please enter a valid task number.");
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showDeleted(Task task, int remainingCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingCount + " tasks in the list.");
    }

    public void showPlainAdded(String input) {
        System.out.println("added: " + input);
    }
}