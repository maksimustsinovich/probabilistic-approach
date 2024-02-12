package by.ustsinovich.probabilisticapproach.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;

public class ClassificationUtils {
    private static final Random RANDOM = new Random();

    private static double calculateGaussian(double x, double mean, double derivation) {
        return 1 / (derivation * sqrt(2 * PI)) * exp(-0.5 * pow((x - mean) / derivation, 2));
    }

    public static double calculateMean(List<Double> doubles) {
        double sum = 0;

        for (var value : doubles) {
            sum += value;
        }

        return sum / doubles.size();
    }

    public static double calculateVariance(List<Double> doubles) {
        double mean = calculateMean(doubles);

        double variance = 0;

        for (double value : doubles) {
            variance += pow(value - mean, 2);
        }

        return variance / doubles.size();
    }

    public static double calculateStandardDeviation(List<Double> doubles) {
        return sqrt(calculateVariance(doubles));
    }

    public static List<Double> generateVector(int len, double mean, double derivation) {
        List<Double> vector = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            vector.add(RANDOM.nextGaussian(mean, derivation));
        }

        return vector;
    }

    public static DoubleUnaryOperator generateProbabilityDensityFunction(double mean,
                                                                         double deviation,
                                                                         double probability) {
        return x -> calculateGaussian(x, mean, deviation) * probability;
    }

    public static List<Double> getAreas(List<Double> y1Values, List<Double> y2Values, double step, List<Double> xValues, int separator) {
        double falseAlarm = 0;
        for (int i = 0; i < separator; i++) {
            falseAlarm += step * y2Values.get(i);
        }

        double detectionMistake = 0;
        for (int i = separator; i < xValues.size(); i++) {
            detectionMistake += step * y1Values.get(i);
        }

        List<Double> areas = new ArrayList<>();

        areas.add(detectionMistake);
        areas.add(falseAlarm);

        return areas;
    }

    public static int findSeparatorIndex(List<Double> y1Values, List<Double> y2Values, List<Double> xValues) {
        boolean firstIsBigger = y1Values.getFirst() > y2Values.getFirst();
        int separatorI = 0;

        for (int i = 0; i < xValues.size(); i++) {
            if ((firstIsBigger && y2Values.get(i) >= y1Values.get(i)) ||
                    (!firstIsBigger && y1Values.get(i) > y2Values.get(i))) {
                separatorI = i;
                break;
            }
        }

        return separatorI;
    }
}
