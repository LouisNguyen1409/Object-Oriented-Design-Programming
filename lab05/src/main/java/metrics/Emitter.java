package metrics;

import java.util.ArrayList;

public class Emitter extends Provider {
    private ArrayList<Plot> plots = new ArrayList<>();
    private Double metric;

    public void emitMetric(double xValue) {
        metric = Math.sin(Math.toRadians(xValue));
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Plot plot : plots) {
            plot.update(metric);
        }
    }

    @Override
    public void registerObserver(Plot plot) {
        if (!plots.contains(plot)) {
            plots.add(plot);
        }
    }

    @Override
    public void removeObserver(Plot plot) {
        plots.remove(plot);
    }
}
