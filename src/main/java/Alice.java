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
}