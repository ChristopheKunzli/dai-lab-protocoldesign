package ch.heig.dai.lab.protocoldesign;

import java.util.Arrays;
import java.util.Set;

public class Calculator {
    static Set<String> supportedOperations = Set.of("ADD", "SUB", "MUL", "DIV", "POW", "SQRT", "FACT", "LOG_N");

    public static double[] parseValues(String[] values) {
        double[] parsedValues = new double[values.length];
        for (int i = 0; i < values.length; ++i) {
            parsedValues[i] = Double.parseDouble(values[i]);
        }
        return parsedValues;
    }

    public static double add(double[] values) {
        double result = 0;
        for (double value : values) {
            result += value;
        }
        return result;
    }

    public static double sub(double[] values) {
        double result = values[0];
        for (int i = 1; i < values.length; ++i) {
            result -= values[i];
        }
        return result;
    }

    public static double mul(double[] values) {
        double result = 1;
        for (double value : values) {
            result *= value;
        }
        return result;
    }

    public static double div(double[] values) {
        if (Arrays.stream(values).anyMatch(value -> value == 0)) throw new IllegalArgumentException("Division by zero");
        double result = values[0];
        for (int i = 1; i < values.length; ++i) {
            result /= values[i];
        }
        return result;
    }

    public static double pow(double[] values) {
        return Math.pow(values[0], values[1]);
    }

    public static double sqrt(double[] values) {
        if (values[0] < 0) throw new IllegalArgumentException("Square root of negative number");
        return Math.sqrt(values[0]);
    }

    public static double fact(double[] values) {
        if (values[0] < 0) throw new IllegalArgumentException("Factorial of negative number");
        double result = 1;
        for (int i = 1; i <= (int) values[0]; ++i) {
            result *= i;
        }
        return result;
    }

    public static double log(double[] values) {
        if (values[0] <= 0 || values[1] <= 0) throw new IllegalArgumentException("Logarithm of negative number");
        return Math.log(values[1]) / Math.log(values[0]);
    }
}
