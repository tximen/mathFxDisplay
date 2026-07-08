package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public class PiExpression implements Expression {

    @Override
    public double eval(double x) {
        return Math.PI;
    }

    @Override
    public Complex eval(Complex x) {
        return Complex.ofCartesian(Math.PI, 0d);
    }

    @Override
    public String info() {
        return "pi";
    }
}
