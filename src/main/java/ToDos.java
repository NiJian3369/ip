public class ToDos extends Task {
    public ToDos(String description) {
        super(description);
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}