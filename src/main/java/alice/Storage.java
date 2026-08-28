package alice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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