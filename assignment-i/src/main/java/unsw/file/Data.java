package unsw.file;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

import unsw.entity.Entity;
import unsw.entity.device.Device;
import unsw.entity.satellite.Satellite;

public class Data {
    private HashMap<String, Entity> entities = new HashMap<String, Entity>();

    /**
     * Add an entity to the data.
     * @param entity
     * @return void
     */
    public void addEntity(Entity entity) {
        entities.put(entity.getEntityId(), entity);
    }

    /**
     * Remove an entity from the data.
     * @param name
     * @return void
     */
    public void removeEntity(String name) {
        entities.remove(name);
    }

    /**
     * Get an entity from the data.
     * @param name
     * @return Entity
     */
    public Entity getEntity(String name) {
        return entities.get(name);
    }

    /**
     * Get all entities from the data.
     * @return List<Entity>
     */
    public List<Entity> getEntityList() {
        List<Entity> entityList = new ArrayList<Entity>();
        for (Entity entity : entities.values()) {
            entityList.add(entity);
        }
        return entityList;
    }

    /**
     * Get all devices from the data.
     * @return List<Device>
     */
    public List<Device> getDeviceList() {
        List<Device> deviceList = new ArrayList<Device>();
        for (Entity entity : entities.values()) {
            if (entity instanceof Device) {
                deviceList.add((Device) entity);
            }
        }
        return deviceList;
    }

    /**
     * Get all satellites from the data.
     * @return List<Satellite>
     */
    public List<Satellite> getSatelliteList() {
        List<Satellite> satelliteList = new ArrayList<Satellite>();
        for (Entity entity : entities.values()) {
            if (entity instanceof Satellite) {
                satelliteList.add((Satellite) entity);
            }
        }
        return satelliteList;
    }

    /**
     * Get entities from the data.
     * @return HashMap<String, Entity>
     */
    public HashMap<String, Entity> getEntities() {
        return entities;
    }

    /**
     * Find a device from the data.
     * @param deviceId
     * @return Device
     */
    public Device findDevice(String deviceId) {
        return (Device) entities.get(deviceId);
    }

    /**
     * Find a satellite from the data.
     * @param satelliteId
     * @return Satellite
     */
    public Satellite findSatellite(String satelliteId) {
        return (Satellite) entities.get(satelliteId);
    }

    /**
     * Find an entity from the data.
     * @param entityId
     * @return Entity
     */
    public Entity findEntity(String entityId) {
        return entities.get(entityId);
    }

    /**
     * Add a file to the data.
     * @param file
     * @param entityId
     * @return void
     */
    public void addFile(File file, String entityId) {
        Entity entity = entities.get(entityId);
        entity.addFile(file);
    }

    /**
     * Connect entities in range.
     * @param entityId
     * @param entitiesInRange
     * @return List<String>
     */
    public List<String> connection(String entityId, List<String> entitiesInRange) {
        List<String> relayList = new ArrayList<String>();
        boolean isRelay = false;
        boolean isSatellite = false;
        Entity target = entities.get(entityId);
        if (target instanceof Satellite) {
            isSatellite = true;
        }

        for (Satellite satellite : getSatelliteList()) {
            if (target.connectRelay(satellite)) {
                relayList.add(satellite.getEntityId());
                isRelay = true;
            }
        }

        if (isRelay) {
            entitiesInRange.addAll(findAllRelays(relayList));
            if (isSatellite) {
                entitiesInRange.addAll(findAllEntities(relayList, entitiesInRange));
            } else {
                entitiesInRange.addAll(findAllSatellites(relayList, entitiesInRange));
            }
        }

        if (isSatellite) {
            Satellite newTarget = (Satellite) target;
            for (Device device : getDeviceList()) {
                if (newTarget.connectDevice(device) && !entitiesInRange.contains(device.getEntityId())) {
                    entitiesInRange.add(device.getEntityId());
                }
            }
        }
        System.out.println(entities);
        for (Satellite satellite : getSatelliteList()) {
            if (target.connectSatellite(satellite) && !entitiesInRange.contains(satellite.getEntityId())) {
                entitiesInRange.add(satellite.getEntityId());
            }
        }
        return entitiesInRange;
    }

    /**
     * Find all relays.
     * @param relayList
     * @return List<String>
     */
    public List<String> findAllRelays(List<String> relayList) {
        List<String> connectedSatList = new ArrayList<String>();
        for (String satelliteId : relayList) {
            Satellite satellite = findSatellite(satelliteId);
            for (Satellite satellite2 : getSatelliteList()) {
                if (satellite.connectSatellite(satellite2) && !connectedSatList.contains(satellite2.getEntityId())
                        && !relayList.contains(satellite2.getEntityId())) {
                    connectedSatList.add(satellite2.getEntityId());
                }
            }
        }
        return connectedSatList;
    }

    /**
     * Find all satellites.
     * @param relayList
     * @param entitiesInRange
     * @return List<String>
     */
    public List<String> findAllSatellites(List<String> relayList, List<String> entitiesInRange) {
        List<String> connectedSatList = new ArrayList<String>();
        for (String satelliteId : relayList) {
            Satellite satellite = findSatellite(satelliteId);
            for (Satellite satellite2 : getSatelliteList()) {
                if (satellite.connectSatellite(satellite2) && !connectedSatList.contains(satellite2.getEntityId())
                        && !relayList.contains(satellite2.getEntityId())
                        && !entitiesInRange.contains(satellite2.getEntityId())) {
                    connectedSatList.add(satellite2.getEntityId());
                }
            }
        }
        return connectedSatList;
    }

    /**
     * Find all entities.
     * @param relayList
     * @param entitiesInRange
     * @return List<String>
     */
    public List<String> findAllEntities(List<String> relayList, List<String> entitiesInRange) {
        List<String> connectedEntityList = new ArrayList<String>();
        for (String satelliteId : relayList) {
            Satellite satellite = findSatellite(satelliteId);
            for (Entity entity : getEntityList()) {
                if (entity instanceof Device && satellite.connectDevice(entity)
                        && !connectedEntityList.contains(entity.getEntityId())
                        && !entitiesInRange.contains(entity.getEntityId())) {
                    connectedEntityList.add(entity.getEntityId());
                }

                if (entity instanceof Satellite && satellite.connectSatellite(entity)
                        && !connectedEntityList.contains(entity.getEntityId())
                        && !relayList.contains(entity.getEntityId())
                        && !entitiesInRange.contains(entity.getEntityId())) {
                    connectedEntityList.add(entity.getEntityId());
                }

            }
        }
        return connectedEntityList;
    }

    /**
     * Check if two entities can communicate.
     * @param sender
     * @param entity
     * @return boolean
     */
    public boolean isCommunicate(Entity sender, Entity entity) {
        List<String> entitiesInRange = new ArrayList<String>();
        String id = sender.getEntityId();
        entitiesInRange = connection(id, entitiesInRange);
        entitiesInRange.removeIf(e -> e.equals(id));
        return entitiesInRange.contains(entity.getEntityId());
    }

    /**
     * Transfer file to an entity.
     * @param receiver
     * @return void
     */
    public void transferTo(Entity receiver) {
        List<File> files = receiver.getFiles();
        for (File file : files) {
            if (!file.getIsComplete()) {
                Entity sender = findEntity(file.getFrom());
                System.out.println(isCommunicate(sender, receiver));
                if (!isCommunicate(sender, receiver)) {
                    checkTeleport(sender, receiver, file);
                    System.out.println("Run 1");
                    break;
                }
                System.out.println("Run");
                transferring(sender, receiver, file);
            }
        }
    }

    /**
     * Check teleport.
     * @param sender
     * @param receiver
     * @param file
     * @return void
     */
    public void checkTeleport(Entity sender, Entity receiver, File file) {
        if (sender instanceof Device && receiver instanceof Satellite) {
            Satellite satellite = (Satellite) receiver;
            satellite.fileTeleportDevice(receiver, file, sender);
            receiver.removeFile(file);
        } else if (sender instanceof Satellite && receiver instanceof Satellite) {
            if (sender.getType().equals("TeleportingSatellite")) {
                receiver.fileTeleport(sender, file);
            } else {
                if (!receiver.fileTeleport(receiver, file)) {
                    receiver.removeFile(file);
                }
            }
        } else {
            if (!receiver.fileTeleport(sender, file)) {
                receiver.removeFile(file);
            }
        }
    }

    /**
     * Transfer file.
     * @param sender
     * @param receiver
     * @param file
     * @return void
     */
    public void transferring(Entity sender, Entity receiver, File file) {
        if (sender instanceof Device && receiver instanceof Satellite) {
            Satellite satellite = (Satellite) receiver;
            int bandWidth = satellite.getReceivedBandWidth();
            file.setOnTransferSize(file.getOnTransferSize() + bandWidth);
            file.setIsComplete();
            if (file.getIsComplete()) {
                satellite.setReceivedFiles(-1);
            }
        } else if (sender instanceof Satellite && receiver instanceof Satellite) {
            Satellite satelliteFrom = (Satellite) sender;
            Satellite satelliteTo = (Satellite) receiver;
            int bandWidthFrom = satelliteFrom.getSentBandWidth();
            int bandWidthTo = satelliteTo.getReceivedBandWidth();
            int finalBandWidth = Math.min(bandWidthFrom, bandWidthTo);

            file.setOnTransferSize(file.getOnTransferSize() + finalBandWidth);
            file.setIsComplete();
            if (file.getIsComplete()) {
                satelliteFrom.setSentFiles(-1);
                satelliteTo.setReceivedFiles(-1);
            }
        } else {
            Satellite satellite = (Satellite) sender;
            int bandWidth = satellite.getSentBandWidth();
            file.setOnTransferSize(file.getOnTransferSize() + bandWidth);
            file.setIsComplete();
            if (file.getIsComplete()) {
                satellite.setSentFiles(-1);
            }
        }
    }
}
