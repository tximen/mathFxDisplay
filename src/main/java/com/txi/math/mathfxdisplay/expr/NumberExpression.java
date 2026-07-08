package com.txi.math.mathfxdisplay.expr;

import org.apache.commons.numbers.complex.Complex;
import org.jspecify.annotations.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public record NumberExpression(double value) implements Expression {

    @Override
    public double eval(double x) {
        return this.value;
    }

    @Override
    public Complex eval(Complex x) {
        return Complex.ofCartesian(this.value, 0d);
    }


    @Override
    public String info() {
        if (this.value == Math.floor(this.value)) {
            return new DecimalFormat("##0").format(this.value);
        } else {
            return createFormatter().format(this.value);
        }
    }

    private static @NonNull DecimalFormat createFormatter() {
        DecimalFormat formatter = new DecimalFormat("###.###", DecimalFormatSymbols.getInstance(Locale.GERMAN));
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(3);
        return formatter;
    }
}
