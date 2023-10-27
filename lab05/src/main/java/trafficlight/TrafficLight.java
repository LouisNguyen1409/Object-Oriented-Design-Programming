package trafficlight;

public class TrafficLight {
    private State state;
    private String id;

    private int count = 0;

    public void setCount(int count) {
        this.count = count;
    }

    private State redLightState;
    private State greenLightState;
    private State yellowLightState;
    private State padestrainState;

    public State getRedLightState() {
        return redLightState;
    }

    public State getGreenLightState() {
        return greenLightState;
    }

    public State getYellowLightState() {
        return yellowLightState;
    }

    public State getPadestrainState() {
        return padestrainState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public TrafficLight(String state, String id) {
        redLightState = new RedLight(this);
        greenLightState = new GreenLight(this);
        yellowLightState = new YellowLight(this);
        padestrainState = new Padestrain(this);
        this.id = id;
        if (state.equals("Red light")) {
            this.count = 6;
            setState(getRedLightState());
        } else if (state.equals("Green light")) {
            this.count = 4;
            setState(getGreenLightState());
        } else if (state.equals("Yellow light")) {
            this.count = 1;
            setState(getYellowLightState());
        } else if (state.equals("Pedestrian light")) {
            this.count = 2;
            setState(getPadestrainState());
        }
    }

    public void change(int numOfCars, int numOfPedestrians) {
        if (count > 0) {
            count -= 1;
            return;
        }
        state.handleTransion(numOfCars, numOfPedestrians);
        state.initializeCount(numOfCars, numOfPedestrians);
    }

    public int timeRemaining() {
        return count;
    }

    public String reportState() {
        if (state.equals(getRedLightState())) {
            return "Red light";
        } else if (state.equals(getGreenLightState())) {
            return "Green light";
        } else if (state.equals(getYellowLightState())) {
            return "Yellow light";
        } else if (state.equals(getPadestrainState())) {
            return "Pedestrian light";
        }
        return "Oops, unknown light!";
    }
}
