import java.util.Scanner;

public class Alice {
    public static void main(String[] args) {
        System.out.println("Good day mate! Alice here! What can I do for ya today?");

        String[] tasks = new String[100];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Good bye mate, I'm off! See ya around!");
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("added: " + input);
            }
        }
    }
}