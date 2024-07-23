package dungeonmania.entities;

import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.Game;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logical.LogicalEntities;
import dungeonmania.map.GameMap;

public class Wire extends Entity implements Conductor {
    private boolean activated;
    private long activeTick;
    private List<LogicalEntities> logicEntity = new ArrayList<>();
    private List<Conductor> conductor = new ArrayList<Conductor>();
    private List<Bomb> bombs = new ArrayList<>();

    public Wire(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
        this.activated = false;
    }

    @Override
    public void activate(long activeTick, GameMap map) {
        if (!this.activated) {
            this.activated = true;
            this.activeTick = activeTick;
            bombs.stream().forEach(b -> Game.addBomb(b));
            // bombs.stream().forEach(b -> b.notify(map));
            logicEntity.stream().forEach(e -> Game.addLogicalEntities(e));
            // logicEntity.stream().forEach(e -> e.activate());
            for (Conductor c : conductor) {
                if (c instanceof Wire) {
                    c.activate(activeTick, map);
                }
            }
        }
    }

    @Override
    public void inactivate() {
        if (this.activated) {
            this.activated = false;
            logicEntity.stream().forEach(e -> e.inactivate());
            for (Conductor c : conductor) {
                if (c instanceof Wire) {
                    c.inactivate();
                }
            }
        }
    }

    @Override
    public void subscribe(Entity e) {
        if (e instanceof LogicalEntities) {
            logicEntity.add((LogicalEntities) e);
        } else if (e instanceof Conductor) {
            conductor.add((Conductor) e);
        } else if (e instanceof Bomb) {
            bombs.add((Bomb) e);
        }
    }

    @Override
    public void unsubscribe(Entity e) {
        if (e instanceof LogicalEntities) {
            logicEntity.remove((LogicalEntities) e);
        } else if (e instanceof Conductor) {
            conductor.remove((Conductor) e);
        } else if (e instanceof Bomb) {
            bombs.remove((Bomb) e);
        }
    }

    @Override
    public boolean isActive() {
        return this.activated;
    }

    @Override
    public long getActiveTick() {
        return this.activeTick;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
