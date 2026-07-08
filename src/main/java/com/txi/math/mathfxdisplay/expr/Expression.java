package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;

public interface Expression {

    double eval(double value);

    Complex eval(Complex value);

    String info();
}
