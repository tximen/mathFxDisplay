package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record UnaryMinusExpression (Expression innerExp ) implements Expression {

    @Override
    public double eval(double value) {
        return - innerExp.eval(value);
    }

    @Override
    public Complex eval(Complex value) {
        return innerExp.eval(value).multiply(-1d);
    }
}
