package metrics.statistic;

import java.util.List;

public abstract class Statistic {
    public abstract List<Double> apply(List<Double> newData);
}
