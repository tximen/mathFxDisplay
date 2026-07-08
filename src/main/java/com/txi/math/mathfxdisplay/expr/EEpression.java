package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public class EEpression implements Expression {

    @Override
    public double eval(double x) {
        return Math.E;
    }

    @Override
    public Complex eval(Complex x) {
        return Complex.ofCartesian(Math.E, 0d);
    }

    @Override
    public String info() {
        return "e";
    }
}
