package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record DevideExpression(Expression first, Expression second) implements Expression {

    @Override
    public double eval(double x) {
        return first.eval(x) / second.eval(x);
    }

    @Override
    public Complex eval(Complex x) {
        return first.eval(x).divide(second.eval(x));
    }


    @Override
    public String info() {
        return "%s/%s".formatted(first.info(), second.info());
    }
}
