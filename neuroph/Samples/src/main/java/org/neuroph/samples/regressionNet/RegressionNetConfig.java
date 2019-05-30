package org.neuroph.samples.regressionNet;

import org.neuroph.core.events.LearningEventListener;

public class RegressionNetConfig {

    private int[] neuronsInLayers;

    private double learningRate;

    private double maxError;

    private int maxIterations;

    private LearningEventListener learningEventListener;

    public RegressionNetConfig(int[] neuronsInLayers, double learningRate, double maxError, int maxIterations) {
        this.neuronsInLayers = neuronsInLayers;
        this.learningRate = learningRate;
        this.maxError = maxError;
        this.maxIterations = maxIterations;
    }

    public RegressionNetConfig(int[] neuronsInLayers, double learningRate, double maxError, int maxIterations,
                               LearningEventListener learningEventListener) {
        this(neuronsInLayers, learningRate, maxError, maxIterations);
        this.learningEventListener = learningEventListener;
    }

    public int[] getNeuronsInLayers() {
        return neuronsInLayers;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getMaxError() {
        return maxError;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public LearningEventListener getLearningEventListener() {
        return learningEventListener;
    }
}
