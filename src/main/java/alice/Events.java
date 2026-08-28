package alice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a period of time, with a description,
 * a start date/time, and an end date/time.
 */
public class Events extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    /**
     * Constructs an Events task.
     *
     * @param description description of the event.
     * @param from the date and time the event starts.
     * @param to the date and time the event ends.
     */
    public Events(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date/time of this event.
     *
     * @return the start time as a LocalDateTime.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date/time of this event.
     *
     * @return the end time as a LocalDateTime.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of this event, including its type
     * marker, completion status, description, and formatted start/end times.
     *
     * @return formatted string for display to the user.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a string representation of this task suitable for saving to
     * the data file, using ISO date-time format for both start and end times.
     *
     * @return formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription()
                + " | " + from + " | " + to;
    }
}