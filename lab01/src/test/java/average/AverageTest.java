package average;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AverageTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testAverage() {
        System.setOut(new PrintStream(outContent));

        Average.main(null);
        assertEquals("The average is 3.5", outContent.toString().trim());

        System.setOut(originalOut);
    }
}
