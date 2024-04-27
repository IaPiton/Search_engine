package searchengine.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartAndStopTest {

    @Test
    public void startAndStopTest() {
        StartAndStop startAndStop = new StartAndStop();
        startAndStop.setStart(true);

        assertEquals(true, StartAndStop.getStart());

    }
}
