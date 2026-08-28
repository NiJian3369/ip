package alice;

/**
 * Represents a basic task with a description and a completion status.
 * Serves as the base class for more specific task types such as ToDos,
 * Deadlines, and Events.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description. The task is initially
     * marked as not done.
     *
     * @param description description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether this task is done.
     *
     * @return "X" if the task is done, or " " (a space) if not.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns whether this task is done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of this task suitable for saving to
     * the data file.
     *
     * @return formatted string for file storage.
     */
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of this task, including its
     * completion status and description.
     *
     * @return formatted string for display to the user.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}