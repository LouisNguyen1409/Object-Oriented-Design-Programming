package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.entity.device.Desktop;
import unsw.entity.device.Handheld;
import unsw.entity.device.Laptop;
import unsw.entity.satellite.Relay;
import unsw.entity.satellite.Satellite;
import unsw.entity.satellite.Teleporting;
import unsw.entity.satellite.Standard;
import unsw.entity.Entity;
import unsw.file.Data;
import unsw.file.File;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

/**
 * The controller for the Blackout system.
 *
 * WARNING: Do not move this file or modify any of the existing method
 * signatures
 */
public class BlackoutController {
    private Data data = new Data();

    /**
     * Create a new device with the given ID, type, and position.
     * @param deviceId
     * @param type
     * @param position
     * @return void
     */
    public void createDevice(String deviceId, String type, Angle position) {
        switch (type) {
        case "DesktopDevice":
            data.addEntity(new Desktop(deviceId, type, position));
            break;
        case "LaptopDevice":
            data.addEntity(new Laptop(deviceId, type, position));
            break;
        case "HandheldDevice":
            data.addEntity(new Handheld(deviceId, type, position));
            break;
        default:
            break;
        }
    }

    /**
     * Remove the device with the given ID.
     * @param deviceId
     * @return void
     */
    public void removeDevice(String deviceId) {
        data.removeEntity(deviceId);
    }

    /**
     * Create a new satellite with the given ID, type, height, and position.
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     * @return void
     */
    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        switch (type) {
        case "StandardSatellite":
            data.addEntity(new Standard(satelliteId, type, height, position));
            break;
        case "TeleportingSatellite":
            data.addEntity(new Teleporting(satelliteId, type, height, position));
            break;
        case "RelaySatellite":
            data.addEntity(new Relay(satelliteId, type, height, position));
            break;
        default:
            break;
        }
    }

    /**
     * Remove the satellite with the given ID.
     * @param satelliteId
     * @return void
     */
    public void removeSatellite(String satelliteId) {
        data.removeEntity(satelliteId);
    }

    /**
     * Return the list of all entities in the system.
     * @return List<Entity>
     */
    public List<String> listDeviceIds() {
        List<String> deviceIds = new ArrayList<>();
        for (Entity e : data.getDeviceList()) {
            deviceIds.add(e.getEntityId());
        }
        return deviceIds;
    }

    /**
     * Return the list of all satellites in the system.
     * @return List<Entity>
     */
    public List<String> listSatelliteIds() {
        List<String> satelliteIds = new ArrayList<>();
        for (Entity e : data.getSatelliteList()) {
            satelliteIds.add(e.getEntityId());
        }
        return satelliteIds;
    }

    /**
     * Add a file to the device with the given ID.
     * @param deviceId
     * @param filename
     * @param content
     * @return void
     */
    public void addFileToDevice(String deviceId, String filename, String content) {
        File newFile = new File(filename, content);
        newFile.setOnTransferSize(content.length());
        newFile.setFrom(deviceId);
        newFile.setIsComplete();
        data.addFile(newFile, deviceId);
    }

    /**
     * Return the information about the device with the given ID.
     * @param id
     * @return EntityInfoResponse
     */
    public EntityInfoResponse getInfo(String id) {
        Entity entity = data.getEntity(id);
        if (entity != null) {
            return entity.getInfo();
        }
        return null;
    }

    /**
     * Simulate for one minute.
     */
    public void simulate() {
        for (Satellite satellite : data.getSatelliteList()) {
            satellite.move();
        }

        for (Entity entity : data.getEntityList()) {
            data.transferTo(entity);
        }
    }

    /**
     * Simulate for the specified number of minutes. You shouldn't need to modify
     * this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    /**
     * Return the list of entities that are in range of the given entity.
     * @param id
     * @return List<String>
     */
    public List<String> communicableEntitiesInRange(String id) {
        List<String> entitiesInRange = new ArrayList<String>();
        entitiesInRange = data.connection(id, entitiesInRange);
        entitiesInRange.removeIf(entity -> entity.equals(id));
        return entitiesInRange;
    }

    /**
     * Send the file with the given name from the entity with the given ID to the entity with the given ID.
     * @param fileName
     * @param fromId
     * @param toId
     * @return void
     */
    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        Entity sentEntity = data.getEntity(fromId);
        Entity receivedEntity = data.getEntity(toId);

        sentEntity.checkSentEntity(sentEntity, fileName);
        receivedEntity.checkReceivedEntity(receivedEntity, fileName, sentEntity);
        File newFile = new File(fileName, sentEntity.getFileContent(fileName));
        newFile.setFrom(fromId);
        newFile.setTo(toId);
        receivedEntity.receivedNewFiles(newFile);

        if (sentEntity instanceof Satellite) {
            Satellite satellite = (Satellite) sentEntity;
            satellite.setSentFiles(1);
        }
    }

    public void createDevice(String deviceId, String type, Angle position, boolean isMoving) {
        createDevice(deviceId, type, position);
        // TODO: Task 3
    }

    public void createSlope(int startAngle, int endAngle, int gradient) {
        // TODO: Task 3
        // If you are not completing Task 3 you can leave this method blank :)
    }
}
