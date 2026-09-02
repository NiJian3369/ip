package alice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void add_singleTask_sizeIncreases() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void add_multipleTasks_correctOrderMaintained() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        tasks.add(new ToDos("return book"));
        assertEquals("read book", tasks.get(0).getDescription());
        assertEquals("return book", tasks.get(1).getDescription());
    }

    @Test
    public void remove_validIndex_taskRemovedAndReturned() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        tasks.add(new ToDos("return book"));

        Task removed = tasks.remove(0);

        assertEquals("read book", removed.getDescription());
        assertEquals(1, tasks.size());
        assertEquals("return book", tasks.get(0).getDescription());
    }

    @Test
    public void isValidIndex_indexWithinBounds_returnsTrue() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        assertTrue(tasks.isValidIndex(0));
    }

    @Test
    public void isValidIndex_negativeIndex_returnsFalse() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        assertFalse(tasks.isValidIndex(-1));
    }

    @Test
    public void isValidIndex_indexEqualToSize_returnsFalse() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        assertFalse(tasks.isValidIndex(1));
    }

    @Test
    public void isValidIndex_emptyList_returnsFalse() {
        TaskList tasks = new TaskList();
        assertFalse(tasks.isValidIndex(0));
    }

    @Test
    public void size_emptyList_returnsZero() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.size());
    }
}
