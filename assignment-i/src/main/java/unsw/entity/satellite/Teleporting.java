package unsw.entity.satellite;

import unsw.utils.Angle;

public class Teleporting extends Satellite {
    /**
     * Constructor for Teleporting.
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public Teleporting(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        setRange(200_000);
        setVelocity(1_000);
        setMaxStorage(200);
        setMaxFiles(INFINITY);
        setSentBandWidth(10);
        setReceivedBandWidth(15);
    }

    /**
     * Move the satellite.
     * @return void
     */
    @Override
    public void move() {
        double moveAngle = getVelocity() / getHeight();
        Angle afterAnti = getPosition().add(Angle.fromRadians(moveAngle));
        Angle afterClock = getPosition().subtract(Angle.fromRadians(moveAngle));
        if (getDirection() == 1 && afterAnti.toDegrees() > UPPER_ORIGIN) {
            afterAnti = Angle.fromDegrees(afterAnti.toDegrees() - UPPER_ORIGIN);
        } else if (getDirection() == -1 && afterAnti.toDegrees() < LOWER_ORIGIN) {
            afterClock = Angle.fromDegrees(UPPER_ORIGIN + afterClock.toDegrees());
        }

        if (getDirection() == 1) {
            if (afterAnti.toDegrees() >= EDGE && getPosition().toDegrees() < EDGE) {
                setPosition(Angle.fromRadians(0));
                setDirection(-1);
            } else {
                setPosition(afterAnti);
            }
        } else if (getDirection() == -1) {
            if (afterClock.toDegrees() <= EDGE && getPosition().toDegrees() > EDGE) {
                setPosition(Angle.fromRadians(0));
                setDirection(1);
            } else {
                setPosition(afterClock);
            }
        }
    }
}
