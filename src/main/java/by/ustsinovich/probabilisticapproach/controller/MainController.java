package by.ustsinovich.probabilisticapproach.controller;

import by.ustsinovich.probabilisticapproach.utils.ClassificationUtils;
import by.ustsinovich.probabilisticapproach.utils.ListsUtils;
import by.ustsinovich.probabilisticapproach.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class MainController {
    @FXML
    public Button btnRun;
    @FXML
    public AreaChart<Double, Double> acProbability;
    @FXML
    public Spinner<Double> spnProbability1;
    @FXML
    public Spinner<Double> spnDeviation1;
    @FXML
    public Spinner<Double> spnMean1;
    @FXML
    public Spinner<Double> spnDeviation2;
    @FXML
    public Spinner<Double> spnMean2;
    @FXML
    public TextField tfTotalClassificationError;
    @FXML
    public TextField tfMissing;
    @FXML
    public TextField tfFalseAlarm;

    @FXML
    public void onBtnRunAction(ActionEvent event) {
        int count = 10;

        double mean1 = spnMean1.getValue();
        double mean2 = spnMean2.getValue();

        double probability1 = spnProbability1.getValue();
        double probability2 = 1 - probability1;

        double deviation1 = spnDeviation1.getValue();
        double deviation2 = spnDeviation2.getValue();

        double step = 0.001;

        var vector1 = ClassificationUtils.generateVector(count, mean1, deviation1);
        var vector2 = ClassificationUtils.generateVector(count, mean2, deviation2);

        DoubleUnaryOperator firstFunction = ClassificationUtils.generateProbabilityDensityFunction(
                ClassificationUtils.calculateMean(vector1),
                ClassificationUtils.calculateStandardDeviation(vector1),
                probability1
        );
        DoubleUnaryOperator secondFunction = ClassificationUtils.generateProbabilityDensityFunction(
                ClassificationUtils.calculateMean(vector2),
                ClassificationUtils.calculateStandardDeviation(vector2),
                probability2
        );

        var interval = ListsUtils.getInterval(ListsUtils.mergeLists(vector1, vector2));

        var xValues = ListsUtils.getDoubleListFromRange(interval.getFirst(), interval.getLast(), step);
        var y1Values = xValues.stream().map(firstFunction::applyAsDouble).toList();
        var y2Values = xValues.stream().map(secondFunction::applyAsDouble).toList();

        MainView view = new MainView(acProbability, tfFalseAlarm, tfMissing, tfTotalClassificationError);

        view.updateChart(xValues, y1Values, y2Values);

        int separatorIndex = ClassificationUtils.findSeparatorIndex(y1Values, y2Values, xValues);

        var areas = ClassificationUtils.getAreas(y1Values, y2Values, step, xValues, separatorIndex);

        view.updateFields(areas);
    }


}
