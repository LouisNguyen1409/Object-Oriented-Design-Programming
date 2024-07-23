package dungeonmania.condition;
import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Conductor;

public abstract class LogicalCondition {
    private List<Conductor> adjConductor = new ArrayList<>();

    public LogicalCondition(List<Conductor> conductor) {
        this.adjConductor = conductor;
    }

    public abstract boolean satisfyCondition();

    public List<Conductor> getAdjConductor() {
        return adjConductor;
    }
}
