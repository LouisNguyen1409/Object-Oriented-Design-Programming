package dungeonmania.condition;

import dungeonmania.entities.Conductor;
import java.util.List;

public class Or extends LogicalCondition {
    public Or(List<Conductor> conductor) {
        super(conductor);
    }

    @Override
    public boolean satisfyCondition() {
        if (getAdjConductor().size() < 1) {
            return false;
        }
        return getAdjConductor().stream().anyMatch(c -> c.isActive());
    }
}
