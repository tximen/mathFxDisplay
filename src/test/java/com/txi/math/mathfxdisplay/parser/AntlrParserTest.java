package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.ComplexNumber;
import com.txi.math.mathfxdisplay.expr.Expression;
import com.txi.math.mathfxdisplay.expr.IdentityExpression;
import com.txi.math.mathfxdisplay.expr.NumberExpression;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AntlrParserTest {
       
    @Test
    void complexExpr01() {
        Assertions.assertThat(analyse("3i")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(analyse("5 i")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(analyse("15  j")).isInstanceOf(ComplexNumber.class);
        Assertions.assertThat(analyse("i")).isInstanceOf(ComplexNumber.class);
    }

    @Test
    void sample01() {
        Expression expression = analyse("1 + 2 + 3");
    }

    @Test
    void sample02() {
        Expression expression = analyse("1 + 2 * 3");
    }

    @Test
    void sample03() {
        Expression expression = analyse("1 + 2 * 3 / 4 * 7");
    }

    @Test
    void complexExpr04() {
        Expression expression = analyse("x");
        Assertions.assertThat(expression).isInstanceOf(IdentityExpression.class);
    }

    @Test
    void complexExpr05() {
        Expression expression = analyse("1 + 3");
        Assertions.assertThat(expression).isInstanceOf(NumberExpression.class);
        NumberExpression  numberExpression = (NumberExpression) expression;
        Assertions.assertThat(numberExpression.value()).isEqualTo(4d);
    }

    private Expression analyse(String message) {
        System.out.println(message);
        ANTLRInputStream input = new ANTLRInputStream(message);
        MathExprLexer lexer = new MathExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathExprParser parser = new MathExprParser(tokens);

        ParseTree tree = parser.expr();
        MathExpressionListener listener = new MathExpressionListener("x");
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener.expression();
    }
}
