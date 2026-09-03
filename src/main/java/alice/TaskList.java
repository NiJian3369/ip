package alice;

import java.util.ArrayList;

/**
 * Represents a list of tasks and provides operations to add, remove,
 * retrieve, and query tasks within the list.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList backed by the given list of tasks, such as one
     * loaded from storage.
     *
     * @param tasks the initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index the zero-based index of the task to remove.
     * @return the removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index the zero-based index of the task.
     * @return the task at that index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the tasks whose description contains at least one of the
     * given keywords (a case-sensitive substring match). Passing a single
     * keyword behaves exactly as a single-keyword search always did;
     * passing several returns their union (any task matching at least one
     * keyword), with each matching task included only once even if it
     * matches more than one keyword, and in their original list order.
     *
     * @param keywords one or more keywords to search for.
     * @return a list of matching tasks.
     */
    public ArrayList<Task> find(String... keywords) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            for (String keyword : keywords) {
                if (task.getDescription().contains(keyword)) {
                    matches.add(task);
                    break;
                }
            }
        }
        return matches;
    }

    /**
     * Checks whether the given index refers to a valid position within
     * the list.
     *
     * @param index the zero-based index to check.
     * @return true if the index is within bounds, false otherwise.
     */
    public boolean isValidIndex(int index) {

        return index >= 0 && index < tasks.size();
    }

    /**
     * Returns the underlying list of all tasks.
     *
     * @return the full list of tasks.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}