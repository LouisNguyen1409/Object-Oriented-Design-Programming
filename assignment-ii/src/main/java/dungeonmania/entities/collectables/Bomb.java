package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Conductor;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.map.GameMap;

import dungeonmania.condition.LogicalCondition;
import dungeonmania.condition.ConditionFactory;

public class Bomb extends Collectable {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    private List<Switch> subs = new ArrayList<>();

    private LogicalCondition logic = null;
    private List<Conductor> conductor = new ArrayList<>();

    public Bomb(Position position, int radius) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
    }

    public Bomb(Position position, int radius, String logic) {
        this(position, radius);
        this.logic = ConditionFactory.createLogic(logic, conductor);
    }

    public void subscribe(Entity e) {
        if (e instanceof Conductor) {
            conductor.add((Conductor) e);
        }
    }

    public void unsubscribe(Entity e) {
        if (e instanceof Conductor) {
            conductor.remove((Conductor) e);
        }
    }

    public void subscribe(Switch s) {
        this.subs.add(s);
    }

    public void notify(GameMap map) {
        if (logic == null) {
            explode(map);
        } else {
            if (logic.satisfyCondition()) {
                explode(map);
            }
        }

    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public boolean onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return false;
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return false;
            subs.stream().forEach(s -> s.unsubscribe(this));
            map.destroyEntity(this);
            this.state = State.INVENTORY;
            return true;
        }
        this.state = State.INVENTORY;
        return false;
    }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(e -> (e instanceof Switch))
                    .collect(Collectors.toList());
            entities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
            entities.stream().map(Switch.class::cast).forEach(s -> this.subscribe(s));
        });
    }

    public void explode(GameMap map) {
        System.out.println("BOOM");
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities)
                    map.destroyEntity(e);
            }
        }
    }

    public State getState() {
        return state;
    }
}
