package alice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Handles reading tasks from and writing tasks to the data file on disk,
 * so that tasks persist between runs of the program.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object for the given file path.
     *
     * @param filePath relative or absolute path to the data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given list of tasks to the data file, overwriting any
     * existing content. Creates the parent directory if it does not exist.
     *
     * @param tasks the list of tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("OOPS!!! Something went wrong saving your tasks.");
        }
    }

    /**
     * Loads tasks from the data file. If the file does not exist, returns
     * an empty list instead of throwing an error.
     *
     * @return the list of tasks loaded from the file, or an empty list if
     *         the file does not exist.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("OOPS!!! Something went wrong loading your tasks.");
        }

        return tasks;
    }

    /**
     * Parses a single line from the data file into a Task object, based on
     * its type marker (T, D, or E).
     *
     * @param line a single line of text from the data file.
     * @return the parsed Task, or null if the type marker is unrecognized.
     */
    private Task parseLine(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;
        if (type.equals("T")) {
            task = new ToDos(description);
        } else if (type.equals("D")) {
            LocalDateTime by = LocalDateTime.parse(parts[3].trim());
            task = new Deadlines(description, by);
        } else if (type.equals("E")) {
            LocalDateTime from = LocalDateTime.parse(parts[3].trim());
            LocalDateTime to = LocalDateTime.parse(parts[4].trim());
            task = new Events(description, from, to);
        } else {
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
