package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.LogicalObserver;
import dungeonmania.util.Position;
import dungeonmania.condition.LogicalCondition;
import dungeonmania.condition.ConditionFactory;
import java.util.ArrayList;
import java.util.List;
import dungeonmania.entities.Conductor;

public class LogicalEntities extends Entity implements LogicalObserver {
    private LogicalCondition logic;
    private boolean activated;
    private List<Conductor> conductor = new ArrayList<>();
    private String status;

    public LogicalEntities(Position position, String logic) {
        super(position);
        this.logic = ConditionFactory.createLogic(logic, conductor);
        this.activated = false;
    }

    @Override
    public void activate() {
        if (logic.satisfyCondition()) {
            this.activated = true;
        } else {
            this.activated = false;
        }
    }

    @Override
    public void inactivate() {
        if (!logic.satisfyCondition()) {
            this.activated = false;
        } else {
            this.activated = true;
        }
    }

    @Override
    public void subscribe(Entity e) {
        if (e instanceof Conductor) {
            conductor.add((Conductor) e);
        }
    }

    @Override
    public void unsubscribe(Entity e) {
        if (e instanceof Conductor) {
            conductor.remove((Conductor) e);
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
