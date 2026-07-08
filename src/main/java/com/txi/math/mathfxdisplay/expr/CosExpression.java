package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record CosExpression(Expression innerExp) implements Expression {

    @Override
    public double eval(double value) {
        return Math.cos(innerExp.eval(value));
    }

    @Override
    public Complex eval(Complex value) {
        Complex evalX = innerExp.eval(value);
        return evalX.cos();
    }


    @Override
    public String info() {
        return "cos(%s)".formatted(innerExp.info());
    }

}
