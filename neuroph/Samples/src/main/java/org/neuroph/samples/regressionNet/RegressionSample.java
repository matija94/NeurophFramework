package org.neuroph.samples.regressionNet;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.learning.BackPropagation;

import javax.swing.*;
import java.util.Arrays;

public class RegressionSample {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Neural network primer");

        String func = JOptionPane.showInputDialog(frame, "Function");

        double min = Double.parseDouble(JOptionPane.showInputDialog(frame, "min"));
        double max = Double.parseDouble(JOptionPane.showInputDialog(frame, "max"));
        double interval = Double.parseDouble(JOptionPane.showInputDialog(frame, "interval"));

        RegressionNetConfig conf = new RegressionNetConfig(new int[] {1, 16, 16, 1}, 0.01,
                1, 100000, new LearningListener());
        RegressionNet net = new RegressionNet(conf);
        net.printNetworkInfo();
        PolynomialFunctionParser parser = new PolynomialFunctionParser();
        PolynomialFunction fn = parser.parse(func);
        DataSet ds = fn.generateTrainingDataSet(min, max, interval);
        System.out.println(ds);
        net.learn(ds);
        System.out.println("Testing NN");
        double output[] = net.calculate(new double[]{2.1});
        System.out.println(Arrays.toString(output));

        DataSet dsTest = fn.generateTrainingDataSet(0.5, 50, 0.001);
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
