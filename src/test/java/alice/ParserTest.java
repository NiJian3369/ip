package alice;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parseTodoDescription_validInput_returnsDescription() throws AliceException {
        String description = Parser.parseTodoDescription("todo read book");
        assertEquals("read book", description);
    }

    @Test
    public void parseTodoDescription_emptyDescription_exceptionThrown() {
        assertThrows(AliceException.class, () -> Parser.parseTodoDescription("todo"));
    }

    @Test
    public void parseTodoDescription_onlyWhitespace_exceptionThrown() {
        assertThrows(AliceException.class, () -> Parser.parseTodoDescription("todo    "));
    }

    @Test
    public void parseDeadline_validInput_returnsCorrectDeadline() throws AliceException {
        Deadlines deadline = Parser.parseDeadline("deadline return book /by 2/12/2019 1800");
        assertEquals("return book", deadline.getDescription());
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), deadline.getBy());
    }

    @Test
    public void parseDeadline_missingByKeyword_exceptionThrown() {
        assertThrows(AliceException.class, () -> Parser.parseDeadline("deadline return book"));
    }

    @Test
    public void parseDeadline_emptyDescription_exceptionThrown() {
        assertThrows(AliceException.class, () -> Parser.parseDeadline("deadline  /by 2/12/2019 1800"));
    }

    @Test
    public void parseDeadline_invalidDateFormat_exceptionThrown() {
        assertThrows(AliceException.class, () -> Parser.parseDeadline("deadline return book /by tomorrow"));
    }

    @Test
    public void parseEvent_validInput_returnsCorrectEvent() throws AliceException {
        Events event = Parser.parseEvent("event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
        assertEquals("project meeting", event.getDescription());
        assertEquals(LocalDateTime.of(2019, 12, 2, 14, 0), event.getFrom());
        assertEquals(LocalDateTime.of(2019, 12, 2, 16, 0), event.getTo());
    }

    @Test
    public void parseEvent_missingFromKeyword_exceptionThrown() {
        assertThrows(AliceException.class,
                () -> Parser.parseEvent("event project meeting /to 2/12/2019 1600"));
    }

    @Test
    public void parseEvent_missingToKeyword_exceptionThrown() {
        assertThrows(AliceException.class,
                () -> Parser.parseEvent("event project meeting /from 2/12/2019 1400"));
    }

    @Test
    public void parseIndex_validInput_returnsZeroBasedIndex() {
        int index = Parser.parseIndex("mark 3", 5);
        assertEquals(2, index);
    }

    @Test
    public void parseIndex_nonNumericInput_exceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Parser.parseIndex("mark abc", 5));
    }
}