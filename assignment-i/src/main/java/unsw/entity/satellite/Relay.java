package unsw.entity.satellite;

import unsw.utils.Angle;

public class Relay extends Satellite {
    /**
     * Constructor for Relay.
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public Relay(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        setRange(300_000);
        setVelocity(1_500);
        double posDeg = position.toDegrees();
        if ((posDeg >= UPPER_BOUND &&  posDeg < THERSHOLD)
            || (posDeg >= LOWER_BOUND && posDeg < UPPER_BOUND)) {
            setDirection(-1);
        } else  {
            setDirection(1);
        }
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
            if (getPosition().toDegrees() >= UPPER_BOUND && getPosition().toDegrees() < THERSHOLD) {
                setPosition(afterClock);
                setDirection(-1);
            } else {
                setPosition(afterAnti);
            }
        } else if (getDirection() == -1) {
            if (getPosition().toDegrees() <= LOWER_BOUND || getPosition().toDegrees() >= THERSHOLD) {
                setPosition(afterAnti);
                setDirection(1);
            } else {
                setPosition(afterClock);
            }
        }
    }
}
