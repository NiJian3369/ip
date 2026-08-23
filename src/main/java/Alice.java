import java.util.Scanner;

public class Alice {
    public static void main(String[] args) {
        System.out.println("Good day mate! Alice here! What can I do for ya today?");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Good bye mate, I'm off! See ya around!");
                break;
            } else {
                System.out.println(input);
            }
        }
    }
}