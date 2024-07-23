package dungeonmania.condition;

import java.util.List;

import dungeonmania.entities.Conductor;

public class Xor extends LogicalCondition {
    public Xor(List<Conductor> conductor) {
        super(conductor);
    }

    @Override
    public boolean satisfyCondition() {
        if (getAdjConductor().size() == 0) {
            return false;
        }
        return getAdjConductor().stream().filter(c -> c.isActive()).count() == 1;
    }
}
