package unsw.entity.device;

import java.util.List;

import unsw.blackout.FileTransferException;
import unsw.blackout.FileTransferException.VirtualFileAlreadyExistsException;
import unsw.blackout.FileTransferException.VirtualFileNotFoundException;
import unsw.entity.Entity;
import unsw.file.File;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public abstract class Device extends Entity {
    /**
     * Constructor for Device.
     * @param deviceId
     * @param type
     * @param position
     */
    public Device(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
    }

    // Instance methods
    /**
     * Check connect to a entity.
     * @param satellite
     * @return boolean
     */
    public boolean connectSatellite(Entity satellite) {
        boolean visible = MathsHelper.isVisible(satellite.getHeight(), satellite.getPosition(), getPosition());
        double distance = MathsHelper.getDistance(satellite.getHeight(), satellite.getPosition(), getPosition());
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
        if (!entity.isFileExists(fileName) || !entity.isFileComplete(fileName)) {
            throw new VirtualFileNotFoundException(fileName);
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
        if (entity.isFileExists(fileName)) {
            throw new VirtualFileAlreadyExistsException(fileName);
        }
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
    }

}
