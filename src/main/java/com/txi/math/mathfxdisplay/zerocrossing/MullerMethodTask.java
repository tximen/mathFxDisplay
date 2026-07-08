package com.txi.math.mathfxdisplay.zerocrossing;

import com.txi.math.mathfxdisplay.expr.ComplexNumber;
import com.txi.math.mathfxdisplay.expr.Expression;
import com.txi.math.mathfxdisplay.expr.NumberExpression;
import com.txi.math.mathfxdisplay.parser.ExpressionParser;
import javafx.concurrent.Task;
import org.apache.commons.numbers.complex.Complex;
import org.jspecify.annotations.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class MullerMethodTask extends Task<String> {

    private static final double EPSILON = 1e-15;
    private final Complex four;
    private final Complex minusTwo;
    private final NullStellenModel viewModel;
    private final ExpressionParser parser;
    private Expression function;
    private final DecimalFormat numberFormat;
    private Complex x0;
    private Complex fx0;
    private Complex x1;
    private Complex fx1;
    private Complex x2;
    private Complex fx2;
    private Complex a;
    private Complex b;
    private Complex c;
    private Complex x3;
    private int iteration;

    public MullerMethodTask(NullStellenModel viewModel) {
        this.viewModel = viewModel;
        this.four = Complex.ofCartesian(4d, 0);
        this.minusTwo = Complex.ofCartesian(-2d, 0);
        this.parser = new ExpressionParser("x");
        this.numberFormat = createFormatter();
    }

    @Override
    protected String call() throws Exception {
        parseFunction();
        readStart();
        double divTest;
        do {
            this.iteration++;
            evaluateFunctionValues();
            if (this.fx0.abs() < EPSILON) {
                return generateResult(this.x0);
            }
            if (this.fx1.abs() < EPSILON) {
                return generateResult(this.x1);
            }
            if (this.fx2.abs() < EPSILON) {
                return generateResult(this.x2);
            }
            step2();
            
            divTest = computeTest();
            this.viewModel.addMessage("test : " + divTest);
            relabel();
        } while (this.iteration < 1_000 && divTest > EPSILON);
        if (this.iteration < 1_000) {
            return generateResult(this.x2);
        } else {
            return "no solution found";
        }
    }

    private @NonNull String generateResult(Complex result) {
        this.viewModel.addMessage("****** Nullstelle ****");
        this.viewModel.addMessage(format(result));
        return "f(%s) = %s".formatted(formatX(result), formatX(this.function.eval(result)));
    }

    private String formatX(Complex value) {
        if (value.getReal() == 0.0) {
            if (value.getImaginary() == 0.0) {
                return "0";
            } else {
                return value.getImaginary() + "i";
            }
        }  else if (value.getImaginary() == 0.0) {
            return String.valueOf(value.getReal());
        } else {
            return value.getReal() + " + " + value.getImaginary() + "i";
        }

    }

    private void readStart() {
        this.x0 = eval(this.parser.parse(this.viewModel.getStartX0()));
        this.x1 = eval(this.parser.parse(this.viewModel.getStartX1()));
        this.x2 = eval(this.parser.parse(this.viewModel.getStartX2()));
    }

    private void relabel() {
        this.viewModel.addMessage("relabel");
        this.x0 = this.x1;
        this.x1 = this.x2;
        this.x2 = this.x3;
        this.viewModel.addMessage("x0 = %s, x1 = %s, x2 = %s".formatted(format(this.x0), format(this.x1), format(this.x2)));
    }

    private void parseFunction() {
        viewModel.addMessage("parse function");
        this.function = this.parser.parse(viewModel.getFunction());
        viewModel.addMessage(this.function.info());
    }

    /**
     * step 1: evaluate function
     */
    private void evaluateFunctionValues() {
        viewModel.addMessage("%d: evaluate function values".formatted(this.iteration));
        this.fx0 = this.function.eval(this.x0);
        this.fx1 = this.function.eval(this.x1);
        this.fx2 = this.function.eval(this.x2);
        viewModel.addMessage(evalInfo());
    }

    private String evalInfo() {
        StringBuilder builder = new StringBuilder(format(0, this.x0, this.fx0));
        builder.append(",  ");
        builder.append(format(1, this.x1, this.fx1));
        builder.append(",  ");
        builder.append(format(2, this.x2, this.fx2));
        return builder.toString();
    }



    private String format(int index, Complex x, Complex fct) {
        return "x%d = %s, fx%d = %s".formatted(index, format(x), index, format(fct));
    }


    private String format(Complex value) {
        if (Math.abs(value.getImaginary()) < EPSILON) {
            return format(value.getReal());
        } else if (value.getReal() == 0d) {
            return "%s i".formatted(format(value.getImaginary()));
        } else {
            return "%s + %s i".formatted(format(value.getReal()), format(value.getImaginary()));
        }
    }

    private String format(double value) {
        if (value == Math.floor(value)) {
            return String.valueOf((long) value);
        } else if (Math.abs(value) < EPSILON) {
            return "0";
        } else {
            return this.numberFormat.format(value);
        }
    }

    private Complex eval(Expression expression) {
        return switch(expression) {
            case ComplexNumber(Complex value) -> value;
            case NumberExpression(double value) -> Complex.ofCartesian(value, 0d);
            default -> throw new IllegalStateException("unexpected value: " + expression);
        };
    }

    private static @NonNull DecimalFormat createFormatter() {
        DecimalFormat formatter = new DecimalFormat("###.###", DecimalFormatSymbols.getInstance(Locale.GERMAN));
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(6);
        return formatter;
    }

    /**
     * step 1: evaluate function
     */
    private void step2() {
        this.c = this.fx2;
        Complex[] solution = new Cramer(createVectorA(), createVectorB(), createVectorC()).solve();
        this.a = solution[0];
        this.b = solution[1];
        this.viewModel.addMessage("a = %s, b = %s, c = %s".formatted(format(this.a), format(this.b), format(this.c)));
        computeX3();
    }

    private Complex[] createVectorA() {
        Complex div0 = this.x0.subtract(this.x2);
        Complex div1 = this.x1.subtract(this.x2);
        return new Complex[] { div0.multiply(div0), div1.multiply(div1)};
    }

    private Complex[] createVectorB() {
        Complex div0 = this.x0.subtract(this.x2);
        Complex div1 = this.x1.subtract(this.x2);
        return new Complex[] { div0, div1 };
    }

    private Complex[] createVectorC() {
        Complex div0 = this.fx0.subtract(this.c);
        Complex div1 = this.fx1.subtract(this.c);
        return new Complex[] { div0, div1 };
    }

    private void computeX3() {
        Complex sqrt = computeSquare();
        if (this.c.abs() == 0d || this.b.abs() == 0d || sqrt.abs() == 0) {
            this.x3 = this.x2;
        } else if (this.b.add(sqrt).abs() == 0) {
            this.x3 = x2.add(this.minusTwo.multiply(this.c).divide(this.b.subtract(sqrt)));;
        } else if (this.b.subtract(sqrt).abs() == 0) {
            this.x3 = x2.add(this.minusTwo.multiply(this.c).divide(this.b.add(sqrt)));
        } else {
            Complex v1 = x2.add(this.minusTwo.multiply(this.c).divide(this.b.add(sqrt)));
            Complex v2 = x2.add(this.minusTwo.multiply(this.c).divide(this.b.subtract(sqrt)));
            if (this.function.eval(v1).abs() < this.function.eval(v2).abs()) {
                this.x3 = v1;
            } else {
                this.x3 = v2;
            }
        }
        this.viewModel.addMessage("x3 = %s".formatted(format(this.x3)));
    }

    private Complex computeSquare() {
        return  (this.b.multiply(this.b).subtract(this.four.multiply(this.a).multiply(this.c))).sqrt();
    }

    private double computeTest() {
        return (this.x3.subtract(this.x2)).divide(this.x3).abs();
    }

}
