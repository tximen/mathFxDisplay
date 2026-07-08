package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.ComplexNumber;
import com.txi.math.mathfxdisplay.expr.Expression;
import com.txi.math.mathfxdisplay.expr.IdentityExpression;
import com.txi.math.mathfxdisplay.expr.NumberExpression;
import com.txi.math.mathfxdisplay.expr.PowerExpression;
import com.txi.math.mathfxdisplay.expr.SubExpression;
import org.apache.commons.numbers.complex.Complex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.offset;

public class ExpressionParserTest {

    private ExpressionParser parser;

    @BeforeEach
    public void init() {
        this.parser = new ExpressionParser("x");
    }

    @Test
    void sample01() {
        Expression expression = this.parser.parse("1 + 2 + 3");
        Assertions.assertThat(expression.info()).isEqualTo("6");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(6d);
    }

    @Test
    void sample02() {
        Expression expression = this.parser.parse("1 + 2 * 3");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(7d);
        Assertions.assertThat(expression.info()).isEqualTo("7");
    }

    @Test
    void sample03() {
        Expression expression = this.parser.parse("1 + 2 * 3 / 4 * 7");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(11.5d);
        Assertions.assertThat(expression.info()).isEqualTo("11,5");
    }

    @Test
    void sample04() {
        Expression expression = this.parser.parse("(1 + 2) * (3 + 4)");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(21d);
    }

    @Test
    void sample05() {
        Expression expression = this.parser.parse("1 + (2 + 3) * 4");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(21d);
    }

    @Test
    void sample06() {
        Expression expression = this.parser.parse("1 + 2*3 + 14");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(21d);
    }

    @Test
    void sample07() {
        Expression expression = this.parser.parse("1 + -2 * 3 + 14");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(9d);
    }

    @Test
    void sample08() {
        Expression expression = this.parser.parse("1 + x");
        Assertions.assertThat(expression.info()).isEqualTo("1 + x");
    }

    @Test
    void sample09() {
        Expression expression = this.parser.parse("1 + x+2");
        Assertions.assertThat(expression.info()).isEqualTo("1 + x + 2");
    }

    @Test
    void sample10() {
        Expression expression = this.parser.parse("2 * x");
        Assertions.assertThat(expression.info()).isEqualTo("2 * x");
    }

    @Test
    void sample11() {
        Expression expression = this.parser.parse("2 * x + 11");
        Assertions.assertThat(expression.info()).isEqualTo("2 * x + 11");
    }

    @Test
    void sample12() {
        Expression expression = this.parser.parse("(2 + x) * (3 + 1)");
        Assertions.assertThat(expression.info()).isEqualTo("(2 + x) * 4");
    }

    @Test
    void sample13() {
        Expression expression = this.parser.parse("(2 + x) * (x + 1)");
        Assertions.assertThat(expression.info()).isEqualTo("(2 + x) * (x + 1)");
        Assertions.assertThat(expression.eval(0)).isEqualTo(2d);
        Assertions.assertThat(expression.eval(1)).isEqualTo(6d);
    }


    @Test
    void sample14() {
        Expression expression = this.parser.parse("e");
        Assertions.assertThat(expression.info()).isEqualTo("e");
        Assertions.assertThat(expression.eval(0)).isEqualTo(Math.E);
    }

    @Test
    void sample15() {
        Expression expression = this.parser.parse("pi");
        Assertions.assertThat(expression.info()).isEqualTo("pi");
        Assertions.assertThat(expression.eval(0)).isEqualTo(Math.PI);
    }

    @Test
    void sample16() {
        Expression expression = this.parser.parse("sin(x)");
        Assertions.assertThat(expression.info()).isEqualTo("sin(x)");
        Assertions.assertThat(expression.eval(0d)).isEqualTo(0d);
        Assertions.assertThat(expression.eval(Math.PI/2)).isEqualTo(1);
    }

    @Test
    void sample17() {
        Expression expression = this.parser.parse("cos(x)");
        Assertions.assertThat(expression.info()).isEqualTo("cos(x)");
        Assertions.assertThat(expression.eval(0d)).isEqualTo(1d);
        Assertions.assertThat(expression.eval(Math.PI/2)).isCloseTo(0, offset(0.00001));
    }

    @Test
    void sample18() {
        Expression expression = this.parser.parse("cos (pi/2 + x )");
        Assertions.assertThat(expression.info()).isEqualTo("cos(pi/2 + x)");
        Assertions.assertThat(expression.eval(0d)).isCloseTo(0, offset(0.00001));
        Assertions.assertThat(expression.eval(Math.PI/2)).isCloseTo(-1, offset(0.00001));
    }

    @Test
    void sample19() {
        Expression expression = this.parser.parse("cos (x) * 4");
        Assertions.assertThat(expression.info()).isEqualTo("cos(x) * 4");
        Assertions.assertThat(expression.eval(0d)).isCloseTo(4, offset(0.00001));
        Assertions.assertThat(expression.eval(Math.PI/2)).isCloseTo(0, offset(0.00001));
    }

    @Test
    void complexExpr01() {
        Assertions.assertThat(this.parser.parse("3i")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(this.parser.parse("5 i")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(this.parser.parse("15  j")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(this.parser.parse("i")).isInstanceOf(ComplexNumber.class);
    }


    @Test
    void complexExpr02() {
        Expression expression = this.parser.parse("1 - i");
        Assertions.assertThat(expression).isInstanceOf(ComplexNumber.class);
        Complex value = ((ComplexNumber) expression).value();
        Assertions.assertThat(value.real()).isEqualTo(1d);
        Assertions.assertThat(value.imag()).isEqualTo(-1d);
    }

    @Test
    void complexExpr03() {
        Expression expression = this.parser.parse("1 + i*j");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression number = (NumberExpression) expression;
        Assertions.assertThat(number.value()).isEqualTo(0d);
    }

    @Test
    void complexExpr04() {
        Expression expression = this.parser.parse("x");
        Assertions.assertThat(expression).isInstanceOf(IdentityExpression.class);
    }

    @Test
    void complexExpr05() {
        Expression expression = this.parser.parse("1 + 3");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(4d);
    }

    @Test
    void functionExpr01() {
        Expression expression = this.parser.parse("x");
        Assertions.assertThat(expression.eval(4d)).isEqualTo(4d);
    }

    @Test
    void functionExpr02() {
        Expression expression = this.parser.parse("x*x");
        Assertions.assertThat(expression.eval(4d)).isEqualTo(16d);
    }

    @Test
    void functionExpr03() {
        Expression expression = this.parser.parse("x + 2");
        Assertions.assertThat(expression.eval(4d)).isEqualTo(6d);
    }

    @Test
    void functionExpr04() {
        Expression expression = this.parser.parse("x - 2");
        Assertions.assertThat(expression).isInstanceOf(SubExpression.class);
        Assertions.assertThat(expression.eval(4d)).isEqualTo(2d);
    }

    @Test
    void functionExpr05() {
        Expression expression = this.parser.parse("x^2");
        Assertions.assertThat(expression).isInstanceOf(PowerExpression.class);
        Assertions.assertThat(expression.eval(4d)).isEqualTo(16d);
    }

    @Test
    void functionExpr06() {
        Expression expression = this.parser.parse("i^2");
        Assertions.assertThat(expression).isInstanceOf(PowerExpression.class);
        Assertions.assertThat(expression.eval(4d)).isEqualTo(-1d);
    }


}
