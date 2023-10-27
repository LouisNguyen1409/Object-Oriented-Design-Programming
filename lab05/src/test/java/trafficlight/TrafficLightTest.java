package trafficlight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TrafficLightTest {
    @Test
    public void testInitialStateIsRed() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        assertEquals("Red light", intersection.reportState(id));
    }

    @Test
    public void testRedLightLast6TicksByDefault() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        assertEquals("Red light", intersection.reportState(id));

        for (var i = 0; i < 6; i++) {
            intersection.tick(id, 20, 0);
            assertEquals("Red light", intersection.reportState(id));
            assertEquals(5 - i, intersection.timeRemaining(id));
        }

        intersection.tick(id, 20, 0);
        assertNotEquals("Red light", intersection.reportState(id));

    }

    @Test
    public void testRedLightDurationDoesNotChangeDuringRedLight() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        assertEquals("Red light", intersection.reportState(id));

        for (var i = 0; i < 6; i++) {
            intersection.tick(id, 0, 0);
            assertEquals("Red light", intersection.reportState(id));
            assertEquals(5 - i, intersection.timeRemaining(id));
        }

        intersection.tick(id, 20, 0);
        assertNotEquals("Red light", intersection.reportState(id));

    }

    @Test
    public void testGreenLightLast4Ticks() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Green light");
        assertEquals("Green light", intersection.reportState(id));

        for (var i = 0; i < 4; i++) {
            intersection.tick(id, 20, 0);
            assertEquals("Green light", intersection.reportState(id));
            assertEquals(3 - i, intersection.timeRemaining(id));
        }

        intersection.tick(id, 20, 0);
        assertNotEquals("Green light", intersection.reportState(id));
    }

    @Test
    public void testGreenLightDurationDoesNotChangeDuringGreenLight() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Green light");
        assertEquals("Green light", intersection.reportState(id));

        for (var i = 0; i < 4; i++) {
            intersection.tick(id, 20, 0);
            assertEquals("Green light", intersection.reportState(id));
            assertEquals(3 - i, intersection.timeRemaining(id));
        }

        intersection.tick(id, 20, 0);
        assertNotEquals("Green light", intersection.reportState(id));
    }

    @Test
    public void testYellowLightLast1Ticks() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Yellow light");
        assertEquals("Yellow light", intersection.reportState(id));

        intersection.tick(id, 20, 0);
        assertEquals("Yellow light", intersection.reportState(id));
        assertEquals(0, intersection.timeRemaining(id));

        intersection.tick(id, 20, 0);
        assertNotEquals("Yellow light", intersection.reportState(id));
    }

    @Test
    public void testRedLightDurationChangeWhenDemandLessThan10() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Yellow light");
        assertEquals("Yellow light", intersection.reportState(id));

        intersection.tick(id, 1, 0);

        intersection.tick(id, 0, 0);
        assertEquals("Red light", intersection.reportState(id));
        assertEquals(10, intersection.timeRemaining(id));
    }

    @Test
    public void testGreenLightDurationChangeWhenDemandGreaterThan100() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        assertEquals("Red light", intersection.reportState(id));

        for (var i = 0; i < 6; i++) {
            intersection.tick(id, 20, 0);
        }

        intersection.tick(id, 101, 0);
        assertEquals("Green light", intersection.reportState(id));
        assertEquals(6, intersection.timeRemaining(id));
    }

    @Test
    public void testSkipPedestrainLightWhenNoPedestrain() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        assertEquals("Red light", intersection.reportState(id));

        for (var i = 0; i < 6; i++) {
            intersection.tick(id, 20, 0);
        }

        intersection.tick(id, 10, 0);
        assertEquals("Green light", intersection.reportState(id));
        assertEquals(4, intersection.timeRemaining(id));
    }

    @Test
    public void testShowPedestrainLightWhenHasPedestrains() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        assertEquals("Red light", intersection.reportState(id));

        for (var i = 0; i < 6; i++) {
            intersection.tick(id, 20, 0);
        }

        intersection.tick(id, 101, 1);
        assertEquals("Pedestrian light", intersection.reportState(id));
        assertEquals(2, intersection.timeRemaining(id));
    }

    @Test
    public void testPedestrainLightLast1Tick() {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Pedestrian light");
        assertEquals("Pedestrian light", intersection.reportState(id));

        for (var i = 0; i < 2; i++) {
            intersection.tick(id, 20, 0);
        }

        intersection.tick(id, 10, 0);
        assertEquals("Green light", intersection.reportState(id));
        assertEquals(4, intersection.timeRemaining(id));
    }
}
