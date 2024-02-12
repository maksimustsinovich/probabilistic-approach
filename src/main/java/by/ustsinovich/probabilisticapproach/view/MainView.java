package by.ustsinovich.probabilisticapproach.view;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.util.List;

import static java.util.FormatProcessor.FMT;

public class MainView {
    public AreaChart<Double, Double> chart;
    public TextField totalClassificationError;
    public TextField missing;
    public TextField falseAlarm;

    public MainView(AreaChart<Double, Double> chart,
                    TextField tfFalseAlarm, TextField tfMissing, TextField tfTotalClassificationError) {
        this.chart = chart;
        this.totalClassificationError = tfTotalClassificationError;
        this.falseAlarm = tfFalseAlarm;
        this.missing = tfMissing;
    }

    public void updateChart(List<Double> xValues, List<Double> y1Values, List<Double> y2Values) {
        chart.getData().clear();

        XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();

        for (int i = 0; i < xValues.size(); i++) {
            XYChart.Data<Double, Double> data1 = new XYChart.Data<>(xValues.get(i), y1Values.get(i));
            XYChart.Data<Double, Double> data2 = new XYChart.Data<>(xValues.get(i), y2Values.get(i));

            series1.getData().add(data1);
            series2.getData().add(data2);
        }

        chart.getData().add(series1);
        chart.getData().add(series2);
    }

    public void updateFields(List<Double> areas) {
        missing.setText(FMT."%.3f\{areas.getFirst() * 100}%%");
        falseAlarm.setText(FMT."%.3f\{areas.get(1) * 100}%%");
        totalClassificationError.setText(FMT."%.3f\{(areas.getFirst() + areas.get(1)) * 100}%%");
    }
}
