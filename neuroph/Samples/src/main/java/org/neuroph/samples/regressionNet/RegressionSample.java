package org.neuroph.samples.regressionNet;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.learning.BackPropagation;

import javax.swing.*;

public class RegressionSample {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Neural network linear regression example");

        String func = JOptionPane.showInputDialog(frame, "Function");

        double min = Double.parseDouble(JOptionPane.showInputDialog(frame, "training set min"));
        double max = Double.parseDouble(JOptionPane.showInputDialog(frame, "training set max"));
        double interval = Double.parseDouble(JOptionPane.showInputDialog(frame, "training set interval"));

        RegressionNetConfig conf = new RegressionNetConfig(new int[] {1, 16, 16, 1}, 0.1,
                1, 100000, new LearningListener());
        RegressionNet net = new RegressionNet(conf);
        net.printNetworkInfo();
        PolynomialFunctionParser parser = new PolynomialFunctionParser();
        PolynomialFunction fn = parser.parse(func);
        DataSet ds = fn.generateTrainingDataSet(min, max, interval);
        System.out.println(ds);
        net.learn(ds);

        min = Double.parseDouble(JOptionPane.showInputDialog(frame, "test set min"));
        max = Double.parseDouble(JOptionPane.showInputDialog(frame, "test set max"));
        interval = Double.parseDouble(JOptionPane.showInputDialog(frame, "test set interval"));


        DataSet dsTest = fn.generateTrainingDataSet(min, max, interval);
        System.out.println(dsTest);
        net.testNeuralNetwork(dsTest);


        DisplayChart.displayChart(ds, func, dsTest);
    }

    static class LearningListener implements LearningEventListener {

        @Override
        public void handleLearningEvent(LearningEvent event) {
            BackPropagation bp = (BackPropagation) event.getSource();
            System.out.println("Current iteration: " + bp.getCurrentIteration());
            System.out.println("Error: " + bp.getTotalNetworkError());
        }

    }
}
