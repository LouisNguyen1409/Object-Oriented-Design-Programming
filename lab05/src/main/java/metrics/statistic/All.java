package metrics.statistic;

import java.util.List;

public class All extends Statistic {
    @Override
    public List<Double> apply(List<Double> newData) {
        return newData;
    }
}
