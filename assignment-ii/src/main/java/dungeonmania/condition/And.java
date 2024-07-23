package dungeonmania.condition;

import dungeonmania.entities.Conductor;
import java.util.List;

public class And extends LogicalCondition {
    public And(List<Conductor> conductor) {
        super(conductor);
    }

    @Override
    public boolean satisfyCondition() {
        if (getAdjConductor().size() < 2) {
            return false;
        }

        return getAdjConductor().stream().allMatch(c -> c.isActive());
    }

}
