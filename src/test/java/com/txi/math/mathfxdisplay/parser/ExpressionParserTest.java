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

public class ExpressionParserTest {

    private ExpressionParser parser;

    @BeforeEach
    public void init() {
        this.parser = new ExpressionParser("x");
    }

    @Test
    void sample01() {
        Expression expression = this.parser.parse("1 + 2 + 3");
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
    }

    @Test
    void sample03() {
        Expression expression = this.parser.parse("1 + 2 * 3 / 4 * 7");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(11.5d);
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
