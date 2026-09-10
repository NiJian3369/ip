package alice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DeadlineTest {

    @Test
    public void snooze_positiveDays_pushesDateBack() {
        Deadline deadline = new Deadline("return book", LocalDateTime.of(2019, 12, 2, 18, 0));
        deadline.snooze(3);
        assertEquals(LocalDateTime.of(2019, 12, 5, 18, 0), deadline.getBy());
    }

    @Test
    public void snooze_calledTwice_accumulatesOffset() {
        Deadline deadline = new Deadline("return book", LocalDateTime.of(2019, 12, 2, 18, 0));
        deadline.snooze(2);
        deadline.snooze(5);
        assertEquals(LocalDateTime.of(2019, 12, 9, 18, 0), deadline.getBy());
    }
}
