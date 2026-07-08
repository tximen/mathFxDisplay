package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record AddExpression(Expression first, Expression second) implements Expression {

   @Override
   public double eval(double value) {
       return first.eval(value) + second.eval(value);
   }

    @Override
    public Complex eval(Complex value) {
        return first.eval(value).add(second.eval(value));
    }

    @Override
    public String info() {
       return "%s + %s".formatted(first.info(), second.info());
    }
}
