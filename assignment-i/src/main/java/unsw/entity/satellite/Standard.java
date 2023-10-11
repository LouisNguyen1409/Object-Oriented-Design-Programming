package unsw.entity.satellite;

import unsw.entity.Entity;
// import unsw.entity.device.Device;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public class Standard extends Satellite {
    /**
     * Constructor for Standard.
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public Standard(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        setRange(150_000);
        setVelocity(2_500);
        setMaxStorage(80);
        setMaxFiles(3);
        setSentBandWidth(1);
        setReceivedBandWidth(1);
        setDirection(-1);
    }

    /**
     * Move the satellite.
     * @return void
     */
    @Override
    public void move() {
        double moveAngle = getVelocity() / getHeight();
        double finalDegree = getPosition().subtract(Angle.fromRadians(moveAngle)).toDegrees();
        if (finalDegree < LOWER_ORIGIN) {
            Angle finalAngle = Angle.fromDegrees(UPPER_ORIGIN + finalDegree);
            setPosition(finalAngle);
        } else {
            setPosition(getPosition().subtract(Angle.fromRadians(moveAngle)));
        }
    }

    /**
     * Check if the device is connectable.
     * @param device
     * @return boolean
     */
    @Override
    public boolean connectDevice(Entity device) {
        super.connectDevice(device);
        boolean visible = MathsHelper.isVisible(getHeight(), getPosition(), device.getPosition());
        double distance = MathsHelper.getDistance(getHeight(), getPosition(), device.getPosition());
        if (visible && distance <= getRange() && !device.getType().equals("DesktopDevice")) {
            return true;
        }
        return false;
    }
}
