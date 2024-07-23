package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

import dungeonmania.entities.logical.LogicalEntities;

public class Switch extends Entity implements Conductor {
    private boolean activated;
    private long activeTick;
    private List<Bomb> bombs = new ArrayList<>();

    private List<LogicalEntities> logicEntity = new ArrayList<>();
    private List<Conductor> conductor = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Entity e) {
        if (e instanceof Bomb) {
            bombs.add((Bomb) e);
        } else if (e instanceof LogicalEntities) {
            logicEntity.add((LogicalEntities) e);
        } else if (e instanceof Conductor) {
            conductor.add((Conductor) e);
        }
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Entity e) {
        if (e instanceof Bomb) {
            bombs.remove((Bomb) e);
        } else if (e instanceof LogicalEntities) {
            logicEntity.remove((LogicalEntities) e);
        } else if (e instanceof Conductor) {
            conductor.remove((Conductor) e);
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public boolean onOverlap(GameMap map, Entity entity) {

        activeTick = System.currentTimeMillis();
        if (entity instanceof Boulder) {
            activated = true;
            System.out.println("Switch overlapped");
            bombs.stream().forEach(b -> Game.addBomb(b));
            // bombs.stream().forEach(b -> b.notify(map));
            logicEntity.stream().forEach(e -> Game.addLogicalEntities(e));
            // logicEntity.stream().forEach(e -> e.activate());
            for (Conductor c : conductor) {
                if (c instanceof Wire) {
                    c.activate(activeTick, map);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            logicEntity.stream().forEach(e -> e.inactivate());
            for (Conductor c : conductor) {
                if (c instanceof Wire) {
                    c.inactivate();
                }
            }
        }
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public void activate(long activeTick, GameMap map) {
        return;
    }

    @Override
    public void inactivate() {
        this.activated = false;
    }

    @Override
    public boolean isActive() {
        return activated;
    }

    @Override
    public long getActiveTick() {
        return this.activeTick;
    }
}
