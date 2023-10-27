package trafficlight;

public class YellowLight implements State {
    private TrafficLight trafficLight;
    private static int count = 1;

    public YellowLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void handleTransion(int numOfCars, int numOfPedestrians) {
        trafficLight.setState(trafficLight.getRedLightState());
    }

    @Override
    public void initializeCount(int numOfCars, int numOfPedestrians) {
        trafficLight.setCount(count);
    }
}
