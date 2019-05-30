package org.neuroph.samples.regressionNet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayChart {

    public static void displayChart(DataSet trainDs, String funcName,
                                    DataSet testDs) {
        double trainData[][] = toMatrix(trainDs);
        double testData[][] = toMatrix(testDs);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("chart");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            XYSeriesCollection dataset = new XYSeriesCollection();


            XYSeries trainSeries = makeSeries(trainData, funcName);
            dataset.addSeries(trainSeries);

            XYSeries testSeries = makeSeries(testData, "NN");
            dataset.addSeries(testSeries);

            JFreeChart chart = ChartFactory.createXYLineChart(funcName, "x", "f(x)",
                    dataset, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel cp = new ChartPanel(chart);

            frame.getContentPane().add(cp);
        });
    }

    private static XYSeries makeSeries(double[][] data, String name) {
        XYSeries series = new XYSeries(name);
        for(int i = 0; i<data[0].length; i++) {
            series.add(data[0][i], data[1][i]);
        }
        return series;
    }

    private static void sortMatrix(double[][] data) {
        Map<Double, Integer> valIndexes = new HashMap<>();
        for (int i = 0; i < data[0].length; i++) {
            valIndexes.put(data[0][i], i);
        }
        Arrays.sort(data[0]);
        for (int i = 0; i < data[0].length; i++) {
            int index = valIndexes.get(data[0][i]);
            double temp = data[1][i];
            data[1][i] = data[1][index];
            data[1][index] = temp;
        }
    }

    private static double[][] toMatrix(DataSet ds) {
        List<DataSetRow> rows = ds.getRows();
        int j = 0;
        double data[][] = new double[ds.getInputSize()+1][ds.size()];
        for (DataSetRow row : rows) {
            double[] doubles = row.toArray();
            for (int i = 0; i < doubles.length; i++) {
                data[i][j] = doubles[i];
            }
            j++;
        }
        return data;
    }
}
