package trafficlight;

public class GreenLight implements State {
    private TrafficLight trafficLight;
    public GreenLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void handleTransion(int numOfCars, int numOfPedestrians) {
        trafficLight.setState(trafficLight.getYellowLightState());
    }

    @Override
    public void initializeCount(int numOfCars, int numOfPedestrians) {
        int trafficDemand = numOfCars + numOfPedestrians;
        int count = trafficDemand > 100 ? 6 : 4;
        trafficLight.setCount(count);
    }
}
