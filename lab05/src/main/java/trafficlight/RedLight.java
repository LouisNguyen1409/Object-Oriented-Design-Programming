package trafficlight;

public class RedLight implements State {
    private TrafficLight trafficLight;

    public RedLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void handleTransion(int numOfCars, int numOfPedestrians) {
        if (numOfPedestrians > 0) {
            trafficLight.setState(trafficLight.getPadestrainState());
            return;
        }
        trafficLight.setState(trafficLight.getGreenLightState());
    }

    @Override
    public void initializeCount(int numOfCars, int numOfPedestrians) {
        int trafficDemand = numOfCars + numOfPedestrians;
        int count = trafficDemand < 10 ? 10 : 6;
        trafficLight.setCount(count);
    }
}
