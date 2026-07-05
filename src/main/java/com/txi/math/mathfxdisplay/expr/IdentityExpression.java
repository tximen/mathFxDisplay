package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public class IdentityExpression implements Expression {

    @Override
    public double eval(double x) {
        return x;
    }

    @Override
    public Complex eval(Complex x) {
        return x;
    }

    public String toString() {
        return "IdentityExpression[]";
    }
}
