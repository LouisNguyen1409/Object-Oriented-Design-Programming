package blackout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import unsw.blackout.BlackoutController;
import unsw.blackout.FileTransferException;
import unsw.response.models.FileInfoResponse;
import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;

import java.util.Arrays;
import java.util.Collections;

import static blackout.TestHelpers.assertListAreEqualIgnoringOrder;

@TestInstance(value = Lifecycle.PER_CLASS)
public class Task2ExampleTests {
    @Test
    public void testEntitiesInRange() {
        // Task 2
        // Example from the specification
        BlackoutController controller = new BlackoutController();

        // Creates 1 satellite and 2 devices
        // Gets a device to send a file to a satellites and gets another device to download it.
        // StandardSatellites are slow and transfer 1 byte per minute.
        controller.createSatellite("Satellite1", "StandardSatellite", 1000 + RADIUS_OF_JUPITER, Angle.fromDegrees(320));
        controller.createSatellite("Satellite2", "StandardSatellite", 1000 + RADIUS_OF_JUPITER, Angle.fromDegrees(315));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceC", "HandheldDevice", Angle.fromDegrees(320));
        controller.createDevice("DeviceD", "HandheldDevice", Angle.fromDegrees(180));
        controller.createSatellite("Satellite3", "StandardSatellite", 2000 + RADIUS_OF_JUPITER, Angle.fromDegrees(175));

        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceC", "Satellite2"),
                controller.communicableEntitiesInRange("Satellite1"));
        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceB", "DeviceC", "Satellite1"),
                controller.communicableEntitiesInRange("Satellite2"));
        assertListAreEqualIgnoringOrder(Arrays.asList("Satellite2"), controller.communicableEntitiesInRange("DeviceB"));

        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceD"), controller.communicableEntitiesInRange("Satellite3"));
    }

    @Test
    public void testSomeExceptionsForSend() {
        // just some of them... you'll have to test the rest
        BlackoutController controller = new BlackoutController();

        // Creates 1 satellite and 2 devices
        // Gets a device to send a file to a satellites and gets another device to download it.
        // StandardSatellites are slow and transfer 1 byte per minute.
        controller.createSatellite("Satellite1", "StandardSatellite", 5000 + RADIUS_OF_JUPITER, Angle.fromDegrees(320));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceC", "HandheldDevice", Angle.fromDegrees(320));

        String msg = "Hey";
        controller.addFileToDevice("DeviceC", "FileAlpha", msg);
        assertThrows(FileTransferException.VirtualFileNotFoundException.class,
                () -> controller.sendFile("NonExistentFile", "DeviceC", "Satellite1"));

        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceC", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg.length() * 2);
        assertThrows(FileTransferException.VirtualFileAlreadyExistsException.class,
                () -> controller.sendFile("FileAlpha", "DeviceC", "Satellite1"));
    }

    @Test
    public void testMovement() {
        // Task 2
        // Example from the specification
        BlackoutController controller = new BlackoutController();

        // Creates 1 satellite and 2 devices
        // Gets a device to send a file to a satellites and gets another device to download it.
        // StandardSatellites are slow and transfer 1 byte per minute.
        controller.createSatellite("Satellite1", "StandardSatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(340));
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(340), 100 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        controller.simulate();
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(337.95), 100 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
    }

    @Test
    public void testExample() {
        // Task 2
        // Example from the specification
        BlackoutController controller = new BlackoutController();

        // Creates 1 satellite and 2 devices
        // Gets a device to send a file to a satellites and gets another device to download it.
        // StandardSatellites are slow and transfer 1 byte per minute.
        controller.createSatellite("Satellite1", "StandardSatellite", 10000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceC", "HandheldDevice", Angle.fromDegrees(320));

        String msg = "Hey";
        controller.addFileToDevice("DeviceC", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceC", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));

        controller.simulate(msg.length() * 2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));

        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));

        controller.simulate(msg.length());
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));

        // Hints for further testing:
        // - What about checking about the progress of the message half way through?
        // - Device/s get out of range of satellite
        // ... and so on.
    }

    @Test
    public void testRelayMovement() {
        // Task 2
        // Example from the specification
        BlackoutController controller = new BlackoutController();

        // Creates 1 satellite and 2 devices
        // Gets a device to send a file to a satellites and gets another device to download it.
        // StandardSatellites are slow and transfer 1 byte per minute.
        controller.createSatellite("Satellite1", "RelaySatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(180));

        // moves in negative direction
        assertEquals(
                new EntityInfoResponse("Satellite1", Angle.fromDegrees(180), 100 + RADIUS_OF_JUPITER, "RelaySatellite"),
                controller.getInfo("Satellite1"));
        controller.simulate();
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(178.77), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
        controller.simulate();
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(177.54), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
        controller.simulate();
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(176.31), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));

        controller.simulate(5);
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(170.18), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
        controller.simulate(24);
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(140.72), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
        // edge case
        controller.simulate();
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(139.49), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
        // coming back
        controller.simulate(1);
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(140.72), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
        controller.simulate(5);
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(146.85), 100 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite1"));
    }

    @Test
    public void testTeleportingMovement() {
        // Test for expected teleportation movement behaviour
        BlackoutController controller = new BlackoutController();

        controller.createSatellite("Satellite1", "TeleportingSatellite", 10000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(0));

        controller.simulate();
        Angle clockwiseOnFirstMovement = controller.getInfo("Satellite1").getPosition();
        controller.simulate();
        Angle clockwiseOnSecondMovement = controller.getInfo("Satellite1").getPosition();
        assertTrue(clockwiseOnSecondMovement.compareTo(clockwiseOnFirstMovement) == 1);

        // It should take 250 simulations to reach theta = 180.
        // Simulate until Satellite1 reaches theta=180
        controller.simulate(250);

        // Verify that Satellite1 is now at theta=0
        assertTrue(controller.getInfo("Satellite1").getPosition().toDegrees() % 360 == 0);
    }

    @Test
    public void testOutOfRange() {
        // Test when a device is out of range of all satellites
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 1000 + RADIUS_OF_JUPITER, Angle.fromDegrees(320));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(310));
        assertEquals(Collections.emptyList(), controller.communicableEntitiesInRange("DeviceB"));
    }

    @Test
    public void testMultipleSatellitesInRange() {
        // Test when a device is in range of multiple satellites
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 1000 + RADIUS_OF_JUPITER, Angle.fromDegrees(320));
        controller.createSatellite("Satellite2", "StandardSatellite", 1000 + RADIUS_OF_JUPITER, Angle.fromDegrees(315));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(310));
        assertEquals(Arrays.asList("Satellite2"), controller.communicableEntitiesInRange("DeviceB"));
        assertEquals(Arrays.asList("DeviceB", "Satellite1"), controller.communicableEntitiesInRange("Satellite2"));
    }

    @Test
    public void testEntitiesViaRelay() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(170));
        controller.createSatellite("Satellite2", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(250));
        controller.createSatellite("Satellite3", "RelaySatellite", 20000 + RADIUS_OF_JUPITER, Angle.fromDegrees(200));
        controller.createSatellite("Satellite4", "RelaySatellite", 130956, Angle.fromDegrees(213));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));
        // System.out.println(controller.communicableEntitiesInRange("Satellite1"));
        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceA", "Satellite2", "Satellite3", "Satellite4"),
                controller.communicableEntitiesInRange("Satellite1"));
        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceA", "Satellite1", "Satellite3", "Satellite4"),
                controller.communicableEntitiesInRange("Satellite2"));
        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceA", "Satellite1", "Satellite2", "Satellite4"),
                controller.communicableEntitiesInRange("Satellite3"));
        assertListAreEqualIgnoringOrder(Arrays.asList("DeviceA", "Satellite1", "Satellite2", "Satellite3"),
                controller.communicableEntitiesInRange("Satellite4"));
        assertListAreEqualIgnoringOrder(Arrays.asList("Satellite3", "Satellite1", "Satellite2", "Satellite4"),
                controller.communicableEntitiesInRange("DeviceA"));
    }

    @Test
    public void testMovement2() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(180));
        controller.createSatellite("Satellite2", "TeleportingSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(190));
        controller.createSatellite("Satellite3", "RelaySatellite", 20000 + RADIUS_OF_JUPITER, Angle.fromDegrees(0));
        controller.createSatellite("Satellite4", "RelaySatellite", 20000 + RADIUS_OF_JUPITER, Angle.fromDegrees(200));

        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(180), 20000 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(190), 20000 + RADIUS_OF_JUPITER,
                "TeleportingSatellite"), controller.getInfo("Satellite2"));
        assertEquals(
                new EntityInfoResponse("Satellite3", Angle.fromDegrees(0), 20000 + RADIUS_OF_JUPITER, "RelaySatellite"),
                controller.getInfo("Satellite3"));
        assertEquals(new EntityInfoResponse("Satellite4", Angle.fromDegrees(200), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite4"));

        controller.simulate(1);

        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(178.4068), 20000 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(190.6372), 20000 + RADIUS_OF_JUPITER,
                "TeleportingSatellite"), controller.getInfo("Satellite2"));
        assertEquals(new EntityInfoResponse("Satellite3", Angle.fromDegrees(0.9558), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite3"));
        assertEquals(new EntityInfoResponse("Satellite4", Angle.fromDegrees(199.0441), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite4"));

        controller.simulate(5);

        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(170.4412), 20000 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(193.82349965052657),
                20000 + RADIUS_OF_JUPITER, "TeleportingSatellite"), controller.getInfo("Satellite2"));
        assertEquals(new EntityInfoResponse("Satellite3", Angle.fromDegrees(5.7352), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite3"));
        assertEquals(new EntityInfoResponse("Satellite4", Angle.fromDegrees(194.2647), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite4"));

        controller.simulate(10);

        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(154.51), 20000 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(200.1959), 20000 + RADIUS_OF_JUPITER,
                "TeleportingSatellite"), controller.getInfo("Satellite2"));
        assertEquals(new EntityInfoResponse("Satellite3", Angle.fromDegrees(15.2939), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite3"));
        assertEquals(new EntityInfoResponse("Satellite4", Angle.fromDegrees(184.706), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite4"));

        controller.simulate(60);

        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(58.9225), 20000 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(238.4309), 20000 + RADIUS_OF_JUPITER,
                "TeleportingSatellite"), controller.getInfo("Satellite2"));
        assertEquals(new EntityInfoResponse("Satellite3", Angle.fromDegrees(72.6464), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite3"));
        assertEquals(new EntityInfoResponse("Satellite4", Angle.fromDegrees(152.2062), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite4"));

        controller.simulate(120);

        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(227.7475), 20000 + RADIUS_OF_JUPITER,
                "StandardSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(314.9009), 20000 + RADIUS_OF_JUPITER,
                "TeleportingSatellite"), controller.getInfo("Satellite2"));
        assertEquals(new EntityInfoResponse("Satellite3", Angle.fromDegrees(187.3514), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite3"));
        assertEquals(new EntityInfoResponse("Satellite4", Angle.fromDegrees(165.5885), 20000 + RADIUS_OF_JUPITER,
                "RelaySatellite"), controller.getInfo("Satellite4"));
    }

    @Test
    public void testExceptionsForSentDevice() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createSatellite("Satellite2", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(315));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(320));

        String msg1 = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg1);
        assertThrows(FileTransferException.VirtualFileNotFoundException.class,
                () -> controller.sendFile("NonExistentFile", "DeviceA", "Satellite1"));

        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg1.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg1.length() * 2);
        assertThrows(FileTransferException.VirtualFileAlreadyExistsException.class,
                () -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg1.length(), false),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
        assertThrows(FileTransferException.VirtualFileNotFoundException.class,
                () -> controller.sendFile("FileAlpha", "DeviceB", "Satellite2"));

    }

    @Test
    public void testExceptionsForReceivedDevice() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));

        String msg1 = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg1);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg1.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg1.length() * 2);
        assertThrows(FileTransferException.VirtualFileAlreadyExistsException.class,
                () -> controller.sendFile("FileAlpha", "Satellite1", "DeviceA"));

    }

    @Test
    public void testExceptionsForSentSatellite() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(320));

        String msg1 = "Hey";
        String msg2 = "Hey2";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg1);
        controller.addFileToDevice("DeviceA", "FileBeta", msg2);
        assertThrows(FileTransferException.VirtualFileNotFoundException.class,
                () -> controller.sendFile("NonExistentFile", "Satellite1", "DeviceB"));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg1.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        assertThrows(FileTransferException.VirtualFileNotFoundException.class,
                () -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        controller.simulate((msg1.length()) * 2);
        assertDoesNotThrow(() -> controller.sendFile("FileBeta", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileBeta", "", msg2.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileBeta"));
        controller.simulate((msg2.length()) * 2);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg1.length(), false),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
        assertThrows(FileTransferException.VirtualFileNoBandwidthException.class,
                () -> controller.sendFile("FileBeta", "Satellite1", "DeviceB"));
    }

    @Test
    public void testExceptionsForReceivedSatellite() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createSatellite("Satellite2", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(315));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(320));

        String msg = "Hey";
        String msg2 = "Hello there, I am a super long message which will take up a lot of space in the satellite";
        String msg3 = "Hey3";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        controller.addFileToDevice("DeviceA", "FileBeta", msg2);
        controller.addFileToDevice("DeviceA", "FileGamma", msg3);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        assertThrows(FileTransferException.VirtualFileAlreadyExistsException.class,
                () -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertThrows(FileTransferException.VirtualFileNoBandwidthException.class,
                () -> controller.sendFile("FileBeta", "DeviceA", "Satellite1"));
        controller.simulate((msg.length()) * 2);
        assertThrows(FileTransferException.VirtualFileNoStorageSpaceException.class,
                () -> controller.sendFile("FileBeta", "DeviceA", "Satellite1"));
        assertThrows(FileTransferException.VirtualFileNoStorageSpaceException.class,
                () -> controller.sendFile("FileBeta", "DeviceA", "Satellite2"));
    }

    @Test
    public void testSendFileFromDeviceToSatellite() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));
        String msg = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg.length() * 2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
    }

    @Test
    public void testSendFileFromSatelliteToDevice() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(320));

        String msg = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg.length() * 2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
        controller.simulate(msg.length());
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
    }

    @Test
    public void testSendFileFromSatelliteToSatellite() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(320));
        controller.createSatellite("Satellite2", "StandardSatellite", 20000 + RADIUS_OF_JUPITER,
                Angle.fromDegrees(315));
        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(310));
        String msg = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg.length() * 2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "Satellite2"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
        controller.simulate(msg.length());
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
    }

    @Test
    public void testFailedToSendFileFromDeviceToSatellite() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 90000, Angle.fromDegrees(155));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hey there";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));

        controller.simulate(10);
        assertEquals(null, controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
    }

    @Test
    public void testFailedToSendFileFromSatelliteToDevice() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 90000, Angle.fromDegrees(155));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));

        controller.simulate(3);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(10);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
        controller.simulate(3);
        assertEquals(null, controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
    }

    @Test
    public void testSendFileFromDeviceToSatelliteWithRelay() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 90000, Angle.fromDegrees(155));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hey there";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.createSatellite("Satellite2", "RelaySatellite", 90000, Angle.fromDegrees(180));
        controller.simulate(10);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
    }

    @Test
    public void testSendFileFromSatelliteToDeviceWithRelay() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 90000, Angle.fromDegrees(155));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(170));

        String msg = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));

        controller.simulate(3);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(10);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceB"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
        controller.createSatellite("Satellite2", "RelaySatellite", 90000, Angle.fromDegrees(180));
        controller.simulate(3);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("DeviceB").getFiles().get("FileAlpha"));
    }

    @Test
    public void testSendFileFromSatelliteToSatelliteWithRelay() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 90000, Angle.fromDegrees(155));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hey";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg.length() * 2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));

        controller.createSatellite("Satellite2", "StandardSatellite", 90000, Angle.fromDegrees(40));
        controller.createSatellite("Satellite3", "RelaySatellite", 90000, Angle.fromDegrees(90));

        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "Satellite2"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
        controller.simulate(msg.length());
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
    }

    @Test
    public void testFailedToSendFileFromDeviceToTeleport() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "TeleportingSatellite", 90000, Angle.fromDegrees(179));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hello there I am a super long msg ttt";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(1);
        assertEquals(new FileInfoResponse("FileAlpha", "Hello there I a", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(1);
        assertEquals(null, controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        assertEquals(new FileInfoResponse("FileAlpha", "Hello here I am a super long msg ", msg.length() - 4, true),
                controller.getInfo("DeviceA").getFiles().get("FileAlpha"));
    }

    @Test
    public void testFailedToSendFileFromTeleportToDevice() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "TeleportingSatellite", 90000, Angle.fromDegrees(177));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(170));

        String msg = "Hello there I am long msg ttt";
        controller.addFileToDevice("DeviceB", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceB", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceA"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("DeviceA").getFiles().get("FileAlpha"));
        controller.simulate(3);
        assertEquals(new FileInfoResponse("FileAlpha", "Hello there I am long msg ", msg.length() - 3, true),
                controller.getInfo("DeviceA").getFiles().get("FileAlpha"));
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
    }

    @Test
    public void testFailedToSendFileFromTeleportToSatellite() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "TeleportingSatellite", 90000, Angle.fromDegrees(177));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hello there I am long msg ttt";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(2);
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.createSatellite("Satellite2", "StandardSatellite", 90000, Angle.fromDegrees(170));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "Satellite2"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
        controller.simulate(3);
        assertEquals(new FileInfoResponse("FileAlpha", "Hello here I am long msg ", msg.length() - 4, true),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
    }

    @Test
    public void testFailedToSendFileFromSatelliteToTeleport() {
        BlackoutController controller = new BlackoutController();
        controller.createSatellite("Satellite1", "StandardSatellite", 90000, Angle.fromDegrees(210));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(180));

        String msg = "Hey there I am msg ttt";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite1"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.simulate(msg.length());
        assertEquals(new FileInfoResponse("FileAlpha", msg, msg.length(), true),
                controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
        controller.createSatellite("Satellite2", "TeleportingSatellite", 90000, Angle.fromDegrees(178));
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "Satellite2"));
        assertEquals(new FileInfoResponse("FileAlpha", "", msg.length(), false),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
        controller.simulate(4);
        assertEquals(new FileInfoResponse("FileAlpha", "Hey here I am msg ", msg.length() - 4, true),
                controller.getInfo("Satellite2").getFiles().get("FileAlpha"));
    }
}
