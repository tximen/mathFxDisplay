package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public record ComplexNumber(Complex value) implements Expression {

    @Override
    public double eval(double x) {
        if (this.value.getImaginary() != 0d) {
            throw new NotRealException("not a real number %s".formatted(this.value));
        } else  {
            return this.value.getReal();
        }
    }

    @Override
    public Complex eval(Complex x) {
        return this.value;
    }


    @Override
    public String info() {
        return getClass().toString();
    }
}
