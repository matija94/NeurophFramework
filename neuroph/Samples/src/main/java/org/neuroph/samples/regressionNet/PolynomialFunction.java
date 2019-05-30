package org.neuroph.samples.regressionNet;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.util.ArrayList;
import java.util.List;

public class PolynomialFunction {

    private ArrayList<Object> data;
    private int unknowns;

    PolynomialFunction(ArrayList<Object> data, int unknowns) {
        this.data = data;
        this.unknowns = unknowns;
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public DataSet generateTrainingDataSet(double min, double max, double interval) {

        DataSet ds = new DataSet(1, 1);
        for (double i = min; i <= max; i+=interval) {
            double inputVector[] = new double[unknowns];
            for (int j = 0; j < unknowns; j++) {
                inputVector[j] = i;
            }
            insertRowInto(ds, inputVector);
        }
        return ds;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    private void insertRowInto(DataSet ds, double[] inputVector) {
        ArrayList<Double> vector = new ArrayList<>();
        for (Double e : inputVector) {
            vector.add(e);
        }
        double y = evaluateFunction(data, vector);
        ds.addRow(new DataSetRow(new double[]{inputVector[0]}, new double[]{y}));
    }

    private Double evaluateFunction(Object term, ArrayList<Double> vector) {
        if (term instanceof Integer) {
            int num = (Integer) term;
            return new Double(num);
        }
        else if (term instanceof Double) {
            return (Double) term;
        }
        else if (term instanceof PolynomialFunctionParser.Unknown) {
            Double unknown = vector.remove(0);
            return unknown;
        }
        else if (term instanceof List) {
            ArrayList<Object> expr = new ArrayList<>((ArrayList<Object>) term);
            while (expr.size() >= 3) {
                Object left = evaluateFunction(expr.remove(0), vector);
                PolynomialFunctionParser.Operator op = (PolynomialFunctionParser.Operator) expr.remove(0);
                Object right = evaluateFunction(expr.remove(0), vector);
                expr.add(0, op.eval((Double) left, (Double) right));
            }
            return (Double) expr.get(0);
        }
        return null;
    }
}
