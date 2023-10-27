package metrics.statistic;

import java.util.List;
import java.util.ArrayList;

public class Average extends Statistic {
    @Override
    public List<Double> apply(List<Double> newData) {
        List<Double> newList = new ArrayList<>();
        double sum  = 0;
        int count = 0;

        for (int i = 0; i < newData.size(); i++) {
            if (count % 5 == 0) {
                newList.add(sum / 5);
                sum = 0;
            }
            sum += newData.get(i);
            count++;
        }
        return newList;
    }
}
