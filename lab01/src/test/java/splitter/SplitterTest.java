package splitter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class SplitterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayInputStream testIn;
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @Test
    public void testSplitter() {
        System.setOut(new PrintStream(outContent));

        final String testString = "Welcome to my humble abode";
        testIn = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testIn);

        Splitter.main(null);

        assertEquals("Enter a sentence specified by spaces only:\nWelcome\nto\nmy\nhumble\nabode\n",
                outContent.toString().replace("\r\n", "\n"));

        System.setOut(systemOut);
        System.setIn(systemIn);
    }
}
