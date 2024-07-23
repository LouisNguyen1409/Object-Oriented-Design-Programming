package dungeonmania.condition;

import dungeonmania.entities.Conductor;
import java.util.List;

public class CoAnd extends LogicalCondition {
    // private int time;
    public CoAnd(List<Conductor> conductor) {
        super(conductor);
    }

    @Override
    public boolean satisfyCondition() {
        if (getAdjConductor().size() < 2) {
            return false;
        }
        long activateTick = getAdjConductor().get(0).getActiveTick();
        System.out.println("activateTick " + activateTick);
        for (Conductor c : getAdjConductor()) {
            if (!c.isActive()) {
                return false;
            } else {
                System.out.println("activateTick " + activateTick);
                if (c.getActiveTick() != activateTick) {
                    return false;
                }
            }
        }
        return true;
    }
}
