package ch.heig.dai.lab.protocoldesign;

public class Calculator {
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
        double result = values[0];
        for (int i = 1; i < values.length; ++i) {
            result /= values[i];
        }
        return result;
    }

    public static double pow(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public static double sqrt(double value) {
        return Math.sqrt(value);
    }

    public static double fact(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        double result = 1;
        for (int i = 2; i <= value; ++i) {
            result *= i;
        }
        return result;
    }

    public static double log(double base, double value) {
        return Math.log(value) / Math.log(base);
    }
}
