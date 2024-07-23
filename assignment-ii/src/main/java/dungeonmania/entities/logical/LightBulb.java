package dungeonmania.entities.logical;

import dungeonmania.util.Position;

public class LightBulb extends LogicalEntities {
    public LightBulb(Position position, String logic) {
        super(position.asLayer(ITEM_LAYER), logic);
        setStatus("light_bulb_off");
    }

    @Override
    public void activate() {
        super.activate();
        if (isActivated()) {
            setStatus("light_bulb_on");
        } else {
            setStatus("light_bulb_off");
        }
    }

    @Override
    public void inactivate() {
        super.inactivate();
        if (isActivated()) {
            setStatus("light_bulb_on");
        } else {
            setStatus("light_bulb_off");
        }
    }
}
