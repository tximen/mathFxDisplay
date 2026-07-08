package com.txi.math.mathfxdisplay.zerocrossing;

import org.apache.commons.numbers.complex.Complex;

public record Cramer(Complex[] a, Complex[] b, Complex[] c) {

    public Complex[] solve() {
        Complex det = a[0].multiply(b[1]).subtract(b[0].multiply(a[1]));
        if (det.isNaN()) {
            throw new InvalidNumberException("invalid det " + det);
        } else if (det.abs() == 0d) {
            throw new InvalidNumberException("determinate is zero " + det);
        }

        return new Complex[]{
              (c[0].multiply(b[1]).subtract(b[0].multiply(c[1]))).divide(det),
              (a[0].multiply(c[1]).subtract(c[0].multiply(a[1]))).divide(det)
        };
    }


}
