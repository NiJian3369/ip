package alice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AliceTest {

    @TempDir
    private Path tempDir;

    private Alice newAlice() {
        return new Alice(tempDir.resolve("alice.txt").toString());
    }

    @Test
    public void getResponse_unknownCommand_reportsErrorAndAddsNoTask() {
        Alice alice = newAlice();

        Response response = alice.getResponse("lsit");

        assertTrue(response.isError());
        assertTrue(response.getText().contains("lsit"));
        assertFalse(alice.getResponse("list").getText().contains("lsit"));
    }

    @Test
    public void getResponse_markWithoutNumber_reportsErrorInsteadOfAddingTask() {
        Alice alice = newAlice();

        Response response = alice.getResponse("mark");

        assertTrue(response.isError());
        assertFalse(alice.getResponse("list").getText().contains("mark"));
    }

    @Test
    public void getResponse_eventWithSwappedMarkers_reportsErrorWithoutCrashing() {
        Alice alice = newAlice();

        Response response = alice.getResponse("event meeting /to 2/12/2019 1600 /from 2/12/2019 1400");

        assertTrue(response.isError());
    }

    @Test
    public void getResponse_validTodo_isNotAnError() {
        Alice alice = newAlice();

        Response response = alice.getResponse("todo read book");

        assertFalse(response.isError());
        assertTrue(alice.getResponse("list").getText().contains("read book"));
    }

    @Test
    public void getResponse_blankInput_reportsError() {
        assertTrue(newAlice().getResponse("   ").isError());
    }

    @Test
    public void getResponse_markOutOfRange_reportsHowManyTasksExist() {
        Alice alice = newAlice();
        alice.getResponse("todo read book");

        Response response = alice.getResponse("mark 5");

        assertTrue(response.isError());
        assertTrue(response.getText().contains("1 task(s)"));
    }

    @Test
    public void getStartupWarning_cleanDataFile_isEmpty() {
        assertTrue(newAlice().getStartupWarning().isEmpty());
    }

    @Test
    public void getResponse_todoSurvivesReload_storedAsTodo() {
        String path = tempDir.resolve("reload.txt").toString();
        new Alice(path).getResponse("todo read book");

        Response listed = new Alice(path).getResponse("list");

        assertEquals(true, listed.getText().contains("[T][ ] read book"));
    }
}
