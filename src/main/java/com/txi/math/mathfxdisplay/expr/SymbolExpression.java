package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record SymbolExpression(String symbol) implements Expression {

    @Override
    public double eval(double x) {
        throw new UnsupportedOperationException("symbol " + symbol + " is not supported");
    }

    @Override
    public Complex eval(Complex x) {
        throw new UnsupportedOperationException("symbol " + symbol + " is not supported");
    }
}
