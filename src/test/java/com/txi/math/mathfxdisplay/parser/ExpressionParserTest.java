package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.ComplexNumber;
import com.txi.math.mathfxdisplay.expr.Expression;
import com.txi.math.mathfxdisplay.expr.IdentityExpression;
import com.txi.math.mathfxdisplay.expr.NumberExpression;
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
    void complexExpr01() {
        Assertions.assertThat(this.parser.parse("3i")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(this.parser.parse("5 i")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(this.parser.parse("15  j")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(this.parser.parse("i")).isInstanceOf(ComplexNumber.class);
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

}
