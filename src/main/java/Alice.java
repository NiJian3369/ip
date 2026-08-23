import java.util.Scanner;

public class Alice {
    public static void main(String[] args) {
        System.out.println("Good day mate! Alice here! What can I do for ya today?");

        Task[] tasks = new Task[100];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Good bye mate, I'm off! See ya around!");
                break;
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new ToDos(description);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);

            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9);
                String[] parts = rest.split(" /by ");
                tasks[taskCount] = new Deadlines(parts[0], parts[1]);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);

            } else if (input.startsWith("event ")) {
                String rest = input.substring(6);
                String[] fromSplit = rest.split(" /from ");
                String description = fromSplit[0];
                String[] toSplit = fromSplit[1].split(" /to ");
                String from = toSplit[0];
                String to = toSplit[1];
                tasks[taskCount] = new Events(description, from, to);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }
        }
    }

    private static void printAddedMessage(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}