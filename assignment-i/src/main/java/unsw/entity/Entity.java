package unsw.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unsw.blackout.FileTransferException;
import unsw.entity.satellite.Satellite;
import unsw.file.File;
import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;
import unsw.utils.Angle;

import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;

public abstract class Entity {
    // Instance variables
    private String entityId;
    private String type;
    private Angle position;
    private List<File> files;
    private int range;
    private double height;

    /**
     * Constructor for Entity.
     * @param entityId
     * @param type
     * @param position
     */
    public Entity(String entityId, String type, Angle position) {
        this.entityId = entityId;
        this.type = type;
        this.position = position;
        this.range = 0;
        this.files = new ArrayList<>();
        this.height = RADIUS_OF_JUPITER;
    }

    /**
     * Constructor for Entity.
     * @param entityId
     * @param type
     * @param height
     * @param position
     */
    public Entity(String entityId, String type, double height, Angle position) {
        this(entityId, type, position);
        this.height = height;
    }

    // Instance methods
    /**
     * Add a file to the entity.
     * @param file
     * @return void
     */
    public void addFile(File file) {
        files.add(file);
    }

    /**
     * Get the information of the entity.
     * @return EntityInfoResponse
     */
    public EntityInfoResponse getInfo() {
        Map<String, FileInfoResponse> mapFiles = new HashMap<String, FileInfoResponse>();
        for (File file : files) {
            mapFiles.put(file.getFileName(), file.getFileInfo());
        }
        return new EntityInfoResponse(entityId, position, height, type, mapFiles);
    }

    /**
     * Check connect to a Relay.
     * @param satellite
     * @return boolean
     */
    public boolean connectRelay(Satellite satellite) {
        boolean isConnected = connectSatellite(satellite);
        if (isConnected) {
            if (satellite.getType().equals("RelaySatellite")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check connect to a satellite.
     * @param satellite
     * @return boolean
     */
    public abstract boolean connectSatellite(Entity satellite);

    /**
     * Check send file to a entity.
     * @param entity
     * @param fileName
     * @throws FileTransferException
     */
    public abstract void checkSentEntity(Entity entity, String fileName) throws FileTransferException;

    /**
     * Check receive file from a entity.
     * @param entity
     * @param fileName
     * @param sender
     * @throws FileTransferException
     */
    public abstract void checkReceivedEntity(Entity entity, String fileName, Entity sender)
            throws FileTransferException;

    /**
     * Check if the file is complete.
     * @param fileName
     * @return boolean
     */
    public boolean isFileComplete(String fileName) {
        return findFile(fileName).getIsComplete();
    }

    /**
     * Find the file.
     * @param fileName
     * @return File
     */
    public File findFile(String fileName) {
        for (File file : files) {
            if (file.getFileName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    /**
     * Get the file size on transfer.
     * @param fileName
     * @return int
     */
    public int onTransferSize(String fileName) {
        return findFile(fileName).getOnTransferSize();
    }

    /**
     * Get the file content.
     * @param fileName
     * @return String
     */
    public String getFileContent(String fileName) {
        return findFile(fileName).getContent();
    }

    /**
     * Receive new files from a entity.
     * @param newFile
     * @return void
     */
    public abstract void receivedNewFiles(File newFile);

    /**
     * Remove a file from the entity.
     * @param file
     * @return void
     */
    public void removeFile(File file) {
        files.remove(file);
    }

    /**
     * Check file transfer during teleporting.
     * @param entity
     * @param inputFile
     * @return boolean
     */
    public boolean fileTeleport(Entity entity, File inputFile) {
        if (entity.getType().equals("TeleportingSatellite")) {
            for (File file : getFiles()) {
                if (file.getFileName().equals(inputFile.getFileName())) {
                    String newContent = file.getOnTransferContent() + file.getRemainContent().replace("t", "");
                    file.setOnTransferContent(newContent);
                    file.setContent(newContent);
                    file.setOnTransferSize(file.getSize());
                    file.setIsComplete();
                    return true;
                }
            }
        }
        return false;
    };

    /**
     * Set the file.
     * @param inputFile
     * @return void
     */
    public void setFile(File inputFile) {
        for (File file : files) {
            if (file.getFileName().equals(inputFile.getFileName())) {
                file = inputFile;
                break;
            }
        }
    }

    /**
     * Get the file.
     * @param inputFile
     * @return File
     */
    public File getFile(File inputFile) {
        for (File file : files) {
            if (file.getFileName().equals(inputFile.getFileName())) {
                return file;
            }
        }
        return null;
    }

    /**
     * Check if the file exists.
     * @param fileName
     * @return boolean
     */
    public boolean isFileExists(String fileName) {
        for (File file : files) {
            if (file.getFileName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    /**
     * Get the entity id.
     * @return String
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * Set the entity id.
     * @param entityId
     * @return void
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     * Get the type.
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type.
     * @param type
     * @return void
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the position.
     * @return Angle
     */
    public Angle getPosition() {
        return position;
    }

    /**
     * Set the position.
     * @param position
     * @return void
     */
    public void setPosition(Angle position) {
        this.position = position;
    }

    /**
     * Get the files.
     * @return List<File>
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * Set the files.
     * @param files
     * @return void
     */
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * Get the range.
     * @return int
     */
    public int getRange() {
        return range;
    }

    /**
     * Set the range.
     * @param range
     * @return void
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Get the height.
     * @return double
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set the height.
     * @param height
     * @return void
     */
    public void setHeight(double height) {
        this.height = height;
    }

}
