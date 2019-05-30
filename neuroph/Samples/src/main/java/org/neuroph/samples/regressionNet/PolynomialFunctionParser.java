package org.neuroph.samples.regressionNet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PolynomialFunctionParser {

    private int unknowns = 0;

    private static Set<Character> ops = new HashSet<>();
    static {
        ops.add('+');
        ops.add('-');
        ops.add('*');
        ops.add('^');
    }

    public static class Operator {
        private char op;

        Operator(char op) {
            this.op = op;
        }

        public char getOp() {
            return op;
        }

        public Double eval(Double left, Double right) {
            switch (op) {
                case '+':
                    return left + right;
                case '-':
                    return left - right;
                case '*':
                    return left * right;
                case '^':
                    return Math.pow(left, right);
                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return Character.toString(op);
        }
    }

    public static class Unknown {
        private char letter;

        Unknown(char letter) {
            this.letter = letter;
        }

        @Override
        public String toString() {
            return Character.toString(letter);
        }
    }

    public PolynomialFunction parse(String expression) {
        ArrayList<Object> expr = new ArrayList<>();
        char[] chars = expression.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == 'x') {
                expr.add(new Unknown(ch));
                unknowns += 1;
            }
            else if (Character.isDigit(ch)) {
                i = parseTerm(i, chars, expr);
            }
            else if (ops.contains(ch)) {
                i = parseOp(i, chars, expr);
            }
        }
        PolynomialFunction fn = new PolynomialFunction(expr, unknowns);
        unknowns = 0;
        return fn;
    }

    private int parseOp(int i, char[] chars, ArrayList<Object> expr) {
        char ch = chars[i];
        if ((ch == '*' || ch == '^') && (expr.size() > 1 || expr.get(0) instanceof List)) {
            Object left = expr.get(expr.size()-1);
            i = parseTerm(i+1, chars, expr);
            Object right = expr.remove(expr.size()-1);
            ArrayList<Object> newExpr = new ArrayList<>();
            if (ch == '^' && left instanceof ArrayList) {
                ArrayList<Object> leftExpr = (ArrayList<Object>) left;
                Object leftExprLeft = leftExpr.remove(leftExpr.size()-1);
                newExpr.add(leftExpr.get(0));
                newExpr.add(leftExpr.get(1));
                ArrayList<Object> rightExpr = new ArrayList<>();
                rightExpr.add(leftExprLeft);rightExpr.add(new Operator(ch));rightExpr.add(right);
                newExpr.add(rightExpr);
            }
            else {
                newExpr.add(left);
                newExpr.add(new Operator(ch));
                newExpr.add(right);
            }
            expr.set(expr.size()-1, newExpr);

        }
        else {
            expr.add(new Operator(ch));
        }
        return i;
    }

    private int parseTerm(int i, char[] chars, ArrayList<Object> expr) {
        StringBuilder sb = new StringBuilder();
        for(; i<chars.length && Character.isDigit(chars[i]); i++) {
            sb.append(chars[i]);
        }
        if(sb.length() == 0 && Character.isLetter(chars[i])) {
            expr.add(new Unknown(chars[i]));
            unknowns += 1;
            return i;
        }
        else {
            expr.add(Integer.parseInt(sb.toString()));
            return i-1;
        }
    }
}