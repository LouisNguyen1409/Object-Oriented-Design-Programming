package metrics.statistic;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Statistic {
    @Override
    public List<Double> apply(List<Double> newData) {
        List<Double> newList = new ArrayList<Double>();

        double sum = 0;
        int count = 0;
        for (int i = 0; i < newData.size(); i++) {
            if (count == 5) {
                newList.add(sum);
                sum = 0;
                count = 0;
            }
            sum += newData.get(i);
            count++;
        }
        return newList;
    }
}
