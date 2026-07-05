package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record SubExpression(Expression first, Expression second) implements Expression {

    @Override
    public double eval(double x) {
        return first.eval(x) - second.eval(x);
    }

    @Override
    public Complex eval(Complex x) {
        return first.eval(x).subtract(second.eval(x));
    }
}
