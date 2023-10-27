package metrics;

public abstract class Provider {
    public abstract void notifyObservers();
    public abstract void registerObserver(Plot plot);
    public abstract void removeObserver(Plot plot);
}
