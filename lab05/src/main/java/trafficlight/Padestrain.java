package trafficlight;

public class Padestrain implements State {
    private TrafficLight trafficLight;
    private static int count = 2;

    public Padestrain(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void handleTransion(int numOfCars, int numOfPedestrians) {
        trafficLight.setState(trafficLight.getGreenLightState());

    }

    @Override
    public void initializeCount(int numOfCars, int numOfPedestrians) {
        trafficLight.setCount(count);
    }
}
