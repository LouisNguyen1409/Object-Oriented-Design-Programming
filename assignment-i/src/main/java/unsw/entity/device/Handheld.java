package unsw.entity.device;

import unsw.utils.Angle;

public class Handheld extends Device {
    /**
     * Constructor for Handheld
     * @param deviceId
     * @param type
     * @param position
     */
    public Handheld(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
        setRange(50_000);
    }
}
