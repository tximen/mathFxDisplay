package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record BraceExpression(Expression expression) implements Expression {

    @Override
    public double eval(double x) {
        return this.expression.eval(x);
    }

    @Override
    public Complex eval(Complex x) {
        return this.expression.eval(x);
    }


    @Override
    public String info() {
        return "(%s)".formatted(this.expression.info());
    }
}
