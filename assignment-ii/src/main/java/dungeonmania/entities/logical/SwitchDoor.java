package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntities {
    public SwitchDoor(Position position, String logic) {
        super(position.asLayer(DOOR_LAYER), logic);
        setStatus("switch_door_closed");
    }

    @Override
    public void activate() {
        super.activate();
        if (isActivated()) {
            setStatus("switch_door_opened");
        } else {
            setStatus("switch_door_closed");
        }
    }

    @Override
    public void inactivate() {
        super.inactivate();
        if (isActivated()) {
            setStatus("switch_door_opened");
        } else {
            setStatus("switch_door_closed");
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return isActivated();
    }
}
