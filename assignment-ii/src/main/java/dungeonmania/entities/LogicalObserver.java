package dungeonmania.entities;

public interface LogicalObserver {
    public void activate();
    public void inactivate();
    public void subscribe(Entity e);
    public void unsubscribe(Entity e);
    public boolean isActivated();
}
