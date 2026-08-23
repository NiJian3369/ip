import java.util.Scanner;
import java.util.ArrayList;

public class Alice {
    public static void main(String[] args) {
        System.out.println("Good day mate! Alice here! What can I do for ya today?");

        ArrayList<Task> tasks = new ArrayList<>();
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Good bye mate, I'm off! See ya around!");
                    break;
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.length() > 4 ? input.substring(5).trim() : "";
                    if (description.isEmpty()) {
                        throw new AliceException("The description of a todo cannot be empty.");
                    }
                    taskCount++;
                    tasks.add(new ToDos(description));
                    printAddedMessage(tasks.get(taskCount - 1), taskCount);

                } else if (input.startsWith("deadline ")) {
                    if (!input.contains(" /by ")) {
                        throw new AliceException("A deadline needs a description and a /by date.");
                    }
                    String rest = input.substring(9);
                    String[] parts = rest.split(" /by ");
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new AliceException("A deadline needs both a description and a /by date.");
                    }
                    tasks.add(new Deadlines(parts[0].trim(), parts[1].trim()));
                    taskCount++;
                    printAddedMessage(tasks.get(taskCount - 1), taskCount);

                } else if (input.startsWith("event")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new AliceException("An event needs a description, /from, and /to.");
                    }
                    String rest = input.substring(6);
                    String[] fromSplit = rest.split(" /from ");
                    String description = fromSplit[0];
                    String[] toSplit = fromSplit[1].split(" /to ");
                    String from = toSplit[0];
                    String to = toSplit[1];
                    tasks.add(new Events(description, from, to));
                    taskCount++;
                    printAddedMessage(tasks.get(taskCount - 1), taskCount);

                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                } else if (input.startsWith("mark ")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= taskCount) {
                        throw new AliceException("That task number doesn't exist!");
                    }
                    tasks.get(index).markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(index));

                } else if (input.startsWith("unmark ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= taskCount) {
                        throw new AliceException("That task number doesn't exist!");
                    }
                    tasks.get(index).markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(index));
                } else if (input.startsWith("delete ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new AliceException("That task number doesn't exist!");
                    }
                    taskCount--;
                    Task removedTask = tasks.get(index);
                    tasks.remove(index);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    tasks.add(new Task(input));
                    taskCount++;
                    System.out.println("added: " + input);
                }
            } catch (AliceException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("OOPS!!! Please enter a valid task number.");
            }
        }
    }

    private static void printAddedMessage(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}