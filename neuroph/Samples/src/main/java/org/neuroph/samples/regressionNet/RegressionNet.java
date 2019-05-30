package org.neuroph.samples.regressionNet;

import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.List;

public class RegressionNet {

    private MultiLayerPerceptron mlp;

    public RegressionNet(RegressionNetConfig conf) {
        mlp = createNetwork(conf);
    }

    public MultiLayerPerceptron learn(DataSet trainingSet) {
        mlp.learn(trainingSet);
        return mlp;
    }

    private MultiLayerPerceptron createNetwork(RegressionNetConfig conf) {
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(conf.getNeuronsInLayers());
        mlp.getLearningRule().setLearningRate(conf.getLearningRate());
        mlp.getLearningRule().setMaxError(conf.getMaxError());
        mlp.getLearningRule().setMaxIterations(conf.getMaxIterations());
        mlp.getLearningRule().addListener(conf.getLearningEventListener());
        mlp.getOutputNeurons().get(0).setTransferFunction(new Linear());
        return mlp;
    }

    public double[] calculate(double[] input) {
        mlp.setInput(input);
        mlp.calculate();
        return mlp.getOutput();
    }

    public void testNeuralNetwork(DataSet testSet) {
        for(DataSetRow testSetRow : testSet.getRows()) {
            double[] networkOutput = calculate(testSetRow.getInput());
            testSetRow.setDesiredOutput(networkOutput);
        }
    }

    public void printNetworkInfo() {
        System.out.println(mlp.getLearningRule().getErrorFunction());
        List<Layer> layers = mlp.getLayers();
        for (int i=0; i<layers.size(); i++) {
            Layer l = layers.get(i);
            List<Neuron> neurons = l.getNeurons();
            for (int j=0; j<neurons.size(); j++) {
                System.out.println(String.format("For %d layer at %d neuron, transfer-fun: %s",
                        i, j, neurons.get(j).getTransferFunction()));
            }
        }
    }
}
