package metrics.statistic;

import java.util.ArrayList;
import java.util.List;

public class Max extends Statistic {
    @Override
    public List<Double> apply(List<Double> newData) {
        List<Double> newList = new ArrayList<Double>();

        Double maxDouble = -1.0;
        int count = 0;
        for (int i = 0; i < newData.size(); i++) {
            if (count % 5 == 0) {
                newList.add(maxDouble);
                maxDouble = -1.0;
            }

            maxDouble = Math.max(maxDouble, newData.get(i));
            count++;
        }
        return newList;
    }
}
