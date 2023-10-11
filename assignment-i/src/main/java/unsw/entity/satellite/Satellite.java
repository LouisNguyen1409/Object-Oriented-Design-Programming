package unsw.entity.satellite;

import unsw.utils.Angle;
import unsw.utils.MathsHelper;

import java.util.List;

import unsw.blackout.FileTransferException;
import unsw.blackout.FileTransferException.VirtualFileAlreadyExistsException;
import unsw.blackout.FileTransferException.VirtualFileNoBandwidthException;
import unsw.blackout.FileTransferException.VirtualFileNoStorageSpaceException;
import unsw.blackout.FileTransferException.VirtualFileNotFoundException;
import unsw.entity.Entity;
import unsw.file.File;

public abstract class Satellite extends Entity {
    // Constant variables
    protected static final double UPPER_ORIGIN = 360;
    protected static final double LOWER_ORIGIN = 0;
    protected static final double EDGE = 180;
    protected static final int INFINITY = 999_999;
    protected static final double LOWER_BOUND = 140;
    protected static final double UPPER_BOUND = 190;
    protected static final double THERSHOLD = 345;

    private int direction; // -1: clockwise, 1: anticlockwise
    private int currentStorage;
    private int currentFiles;
    private int sentFiles;
    private int receivedFiles;

    private int maxStorage;
    private int maxFiles;
    private int velocity;
    private int sentBandWidth;
    private int receivedBandWidth;

    /**
     * Constructor for Satellite.
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public Satellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.direction = 1;
        this.currentStorage = 0;
        this.currentFiles = 0;
        this.sentFiles = 0;
        this.receivedFiles = 0;
        this.maxStorage = 0;
        this.maxFiles = 0;
        this.velocity = 0;
        this.sentBandWidth = 0;
        this.receivedBandWidth = 0;
    }

    // Instance methods
    /**
     * Move the satellite.
     * @return void
     */
    public abstract void move();

    /**
     * Check connect to a satellite.
     * @param satellite
     * @return boolean
     */
    @Override
    public boolean connectSatellite(Entity satellite) {
        boolean visible = MathsHelper.isVisible(getHeight(), getPosition(), satellite.getHeight(),
                satellite.getPosition());
        double distance = MathsHelper.getDistance(getHeight(), getPosition(), satellite.getHeight(),
                satellite.getPosition());
        if (visible && distance <= getRange()) {
            return true;
        }
        return false;
    }

    /**
     * Check connect to a device.
     * @param device
     * @return boolean
     */
    public boolean connectDevice(Entity device) {
        boolean visible = MathsHelper.isVisible(getHeight(), getPosition(), device.getPosition());
        double distance = MathsHelper.getDistance(getHeight(), getPosition(), device.getPosition());
        if (visible && distance <= getRange()) {
            return true;
        }
        return false;
    }

    /**
     * Check send file to a entity.
     * @param entity
     * @param fileName
     * @throws FileTransferException
     */
    @Override
    public void checkSentEntity(Entity entity, String fileName) throws FileTransferException {
        Satellite satellite = (Satellite) entity;
        if (!entity.isFileExists(fileName) || !entity.isFileComplete(fileName)) {
            throw new VirtualFileNotFoundException(fileName);
        }

        if (!satellite.isSentFile()) {
            throw new VirtualFileNoBandwidthException(satellite.getEntityId());
        }
    }

    /**
     * Check receive file from a entity.
     * @param entity
     * @param fileName
     * @param sender
     * @throws FileTransferException
     */
    @Override
    public void checkReceivedEntity(Entity entity, String fileName, Entity sender) throws FileTransferException {
        Satellite satellite = (Satellite) entity;
        if (satellite.isFileExists(fileName)) {
            throw new VirtualFileAlreadyExistsException(fileName);
        }

        if (!satellite.isReceiveFile()) {
            throw new VirtualFileNoBandwidthException(satellite.getEntityId());
        }

        if (!satellite.isEnoughFiles()) {
            throw new VirtualFileNoStorageSpaceException("Max Files Reached");
        }

        if (!satellite.isEnoughStorage(sender.onTransferSize(fileName))) {
            throw new VirtualFileNoStorageSpaceException("Max Storage Reached");
        }
    }

    /**
     * Check is it able to send file.
     * @return boolean
     */
    public boolean isSentFile() {
        if (sentFiles < sentBandWidth) {
            return true;
        }
        return false;
    }

    /**
     * Check is it able to receive file.
     * @return boolean
     */
    public boolean isReceiveFile() {
        if (receivedFiles < receivedBandWidth) {
            return true;
        }
        return false;
    }

    /**
     * Check is it able to store file.
     * @return boolean
     */
    public boolean isEnoughFiles() {
        if (maxFiles - currentFiles != 0) {
            return true;
        }
        return false;
    }

    /**
     * Check is it able to store file.
     * @param fileSize
     * @return boolean
     */
    public boolean isEnoughStorage(int fileSize) {
        if (maxStorage - currentStorage >= fileSize) {
            return true;
        }
        return false;
    }

    /**
     * Receive new files from a entity.
     * @param newFile
     * @return void
     */
    @Override
    public void receivedNewFiles(File newFile) {
        List<File> files = getFiles();
        files.add(newFile);
        setFiles(files);
        setCurrentFiles();
        setCurrentStorage(newFile.getSize());
        setReceivedFiles(1);
    }

    /**
     * Check file transfer during teleport.
     * @param entity
     * @param inputFile
     * @param device
     * @return boolean
     */
    public boolean fileTeleportDevice(Entity entity, File inputFile, Entity device) {
        if (entity.getType().equals("TeleportingSatellite")) {
            for (File file : getFiles()) {
                if (file.getFileName().equals(inputFile.getFileName())) {
                    file.setOnTransferSize(0);
                    String newContent = file.getContent().replace("t", "");
                    File cloneFile = device.getFile(file);
                    cloneFile.setContent(newContent);
                    cloneFile.setOnTransferContent(newContent);
                    cloneFile.setOnTransferSize(file.getSize());
                    device.setFile(cloneFile);
                    return true;
                }
            }
        }
        return false;
    };

    // Getters and Setters
    /**
     * Get the direction of the satellite.
     * @return int
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Set the direction of the satellite.
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Get the current storage of the satellite.
     * @return int
     */
    public int getCurrentStorage() {
        return currentStorage;
    }

    /**
     * Set the current storage of the satellite.
     * @param newFile
     */
    public void setCurrentStorage(int newFile) {
        currentStorage += newFile;
    }

    /**
     * Get the current files of the satellite.
     * @return int
     */
    public int getCurrentFiles() {
        return currentFiles;
    }

    /**
     * Set the current files of the satellite.
     */
    public void setCurrentFiles() {
        currentFiles += 1;
    }

    /**
     * Get the sent files of the satellite.
     * @return int
     */
    public int getSentFiles() {
        return sentFiles;
    }

    /**
     * Set the sent files of the satellite.
     * @param sentFiles
     */
    public void setSentFiles(int sentFiles) {
        this.sentFiles += sentFiles;
    }

    /**
     * Get the received files of the satellite.
     * @return int
     */
    public int getReceivedFiles() {
        return receivedFiles;
    }

    /**
     * Set the received files of the satellite.
     * @param receivedFiles
     */
    public void setReceivedFiles(int receivedFiles) {
        this.receivedFiles += receivedFiles;
    }

    /**
     * Get the max storage of the satellite.
     * @return int
     */
    public int getMaxStorage() {
        return maxStorage;
    }

    /**
     * Set the max storage of the satellite.
     * @param maxStorage
     */
    public void setMaxStorage(int maxStorage) {
        this.maxStorage = maxStorage;
    }

    /**
     * Get the max files of the satellite.
     * @return int
     */
    public int getMaxFiles() {
        return maxFiles;
    }

    /**
     * Set the max files of the satellite.
     * @param maxFiles
     */
    public void setMaxFiles(int maxFiles) {
        this.maxFiles = maxFiles;
    }

    /**
     * Get the velocity of the satellite.
     * @return int
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * Set the velocity of the satellite.
     * @param velocity
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * Get the sent bandwidth of the satellite.
     * @return int
     */
    public int getSentBandWidth() {
        return sentBandWidth / sentFiles;
    }

    /**
     * Set the sent bandwidth of the satellite.
     * @param sentBandWidth
     */
    public void setSentBandWidth(int sentBandWidth) {
        this.sentBandWidth = sentBandWidth;
    }

    /**
     * Get the received bandwidth of the satellite.
     * @return int
     */
    public int getReceivedBandWidth() {
        return receivedBandWidth / receivedFiles;
    }

    /**
     * Set the received bandwidth of the satellite.
     * @param receivedBandWidth
     */
    public void setReceivedBandWidth(int receivedBandWidth) {
        this.receivedBandWidth = receivedBandWidth;
    }

}
