package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record NumberExpression(double value) implements Expression {

    @Override
    public double eval(double x) {
        return this.value;
    }

    @Override
    public Complex eval(Complex x) {
        return Complex.ofCartesian(this.value, 0d);
    }
}
