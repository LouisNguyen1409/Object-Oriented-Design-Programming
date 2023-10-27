package trafficlight;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * PLEASE DO NOT CHANGE THE PROTOTYPE OF THIS FILE
 */
public class Intersection {
    private Map<String, TrafficLight> trafficLights = new HashMap<>();

    /**
     * @param initState initial state of the traffic light
     * @return id of the newly added traffic light
     */
    public String addTrafficLight(String initState) {
        String id = UUID.randomUUID().toString();
        trafficLights.put(id, new TrafficLight(initState, id));
        return id;
    }

    public void tick(String id, int numOfCars, int numOfPedestrians) {
        trafficLights.get(id).change(numOfCars, numOfPedestrians);
    }

    public String reportState(String id) {
        return trafficLights.get(id).reportState();
    }

    public int timeRemaining(String id) {
        return trafficLights.get(id).timeRemaining();
    }
}
