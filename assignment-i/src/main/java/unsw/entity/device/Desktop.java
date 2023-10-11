package unsw.entity.device;

import unsw.utils.Angle;

public class Desktop extends Device {
    /**
     * Constructor for Desktop
     * @param deviceId
     * @param type
     * @param position
     */
    public Desktop(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
        setRange(100_000);
    }
}
