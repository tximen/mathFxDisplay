package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record PowerExpression(Expression first, Expression second) implements Expression {

    private static final double EPSILON = 1.0e-20;

    @Override
    public double eval(double x) {
        if (first instanceof ComplexNumber(Complex value)) {
            Complex result = evalExpWithReal(value, second.eval(x));
            if (Math.abs(result.getImaginary()) < EPSILON) {
                return result.getReal();
            } else {
                throw new NotRealException("not a real number %s".formatted(result));
            }
        } else {
            return Math.pow(first.eval(x), second.eval(x));
        }
    }

    private Complex evalExpWithReal(Complex value, double secondVal) {
        if (secondVal > 0d && secondVal  % 1 == 0) {
            return evalExpWithInt(value, (int) secondVal - 1);
        } else {
            return value.pow(secondVal);
        }
    }

    private Complex evalExpWithInt(Complex value, int secondVal) {
        Complex result = value;
        for (int i = 0; i < secondVal; i++) {
            result = result.multiply(value);
        }
        return result;
    }

    @Override
    public Complex eval(Complex x) {
        Complex firstValue = first.eval(x);
        Complex secondValue = second.eval(x);
        if (firstValue.getImaginary() == 0d && secondValue.getImaginary() == 0d) {
            return Complex.ofCartesian(Math.pow(firstValue.getReal(), secondValue.getReal()), 0d);
        }
        return first.eval(x).pow(second.eval(x));
    }


    @Override
    public String info() {
        return getClass().toString();
    }
}
