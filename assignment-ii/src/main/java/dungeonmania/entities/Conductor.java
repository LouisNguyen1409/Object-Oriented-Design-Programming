package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Conductor {
    public void activate(long activeTick, GameMap map);
    public void inactivate();
    public void subscribe(Entity e);
    public void unsubscribe(Entity e);
    public boolean isActive();
    public long getActiveTick();
}
