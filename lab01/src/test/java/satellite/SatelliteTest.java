package satellite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SatelliteTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testSatellite() {
        System.setOut(new PrintStream(outContent));

        Satellite.main(null);
        assertEquals("I am Satellite A at position 122.0 degrees, 10000 km above the centre of the earth "
                + "and moving at a velocity of 55.0 metres per second\n2.129301687433082"
                + "\n0.04303052592865024\n4380.0\n", outContent.toString().replace("\r\n", "\n"));

        System.setOut(originalOut);
    }
}
