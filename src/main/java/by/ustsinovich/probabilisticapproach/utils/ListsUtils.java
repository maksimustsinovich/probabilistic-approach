package by.ustsinovich.probabilisticapproach.utils;

import java.util.ArrayList;
import java.util.List;

public class ListsUtils {
    @SafeVarargs
    public static <T> List<T> mergeLists(List<T>... lists) {
        List<T> newList = new ArrayList<>();

        for (var list : lists) {
            newList.addAll(list);
        }

        return newList;
    }

    public static List<Double> getInterval(List<Double> list) {
        List<Double> interval = new ArrayList<>();

        interval.add(list.stream().min(Double::compareTo).orElse(Double.MIN_VALUE));
        interval.add(list.stream().max(Double::compareTo).orElse(Double.MAX_VALUE));

        return interval;
    }

    public static List<Double> getDoubleListFromRange(double start, double end, double step) {
        List<Double> list = new ArrayList<>();

        double value = start;

        while (value <= end) {
            list.add(value);

            value += step;
        }

        return list;
    }
}
