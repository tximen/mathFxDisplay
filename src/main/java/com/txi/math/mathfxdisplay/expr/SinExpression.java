package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record SinExpression(Expression innerExp) implements Expression {

    @Override
    public double eval(double value) {
        return Math.sin(innerExp.eval(value));
    }

    @Override
    public Complex eval(Complex value) {
        Complex evalX = innerExp.eval(value);
        return evalX.sin();
    }


    @Override
    public String info() {
        return "sin(%s)".formatted(innerExp.info());
    }
}
