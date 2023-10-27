package trafficlight;

public interface State {
    public void handleTransion(int numOfCars, int numOfPedestrians);
    public void initializeCount(int numOfCars, int numOfPedestrians);
}
