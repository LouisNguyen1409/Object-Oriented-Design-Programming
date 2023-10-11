package unsw.entity.device;

import unsw.utils.Angle;

public class Laptop extends Device {
    /**
     * Constructor for Laptop
     * @param deviceId
     * @param type
     * @param position
     */
    public Laptop(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
        setRange(200_000);
    }
}
