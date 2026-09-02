package alice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a description and a deadline date/time by which
 * it must be completed.
 */
public class Deadlines extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    private LocalDateTime by;

    /**
     * Constructs a Deadlines task.
     *
     * @param description description of the task.
     * @param by the date and time by which the task must be completed.
     */
    public Deadlines(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline date/time of this task.
     *
     * @return the deadline as a LocalDateTime.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of this deadline task, including its
     * type marker, completion status, description, and formatted deadline.
     *
     * @return formatted string for display to the user.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a string representation of this task suitable for saving to
     * the data file, using ISO date-time format for the deadline.
     *
     * @return formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + by;
    }
}
