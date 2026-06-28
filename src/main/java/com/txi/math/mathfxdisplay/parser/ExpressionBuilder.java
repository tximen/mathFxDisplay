package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.ComplexNumber;
import com.txi.math.mathfxdisplay.expr.DevideExpression;
import com.txi.math.mathfxdisplay.expr.Expression;
import com.txi.math.mathfxdisplay.expr.IdentityExpression;
import com.txi.math.mathfxdisplay.expr.MultiplyExpression;
import com.txi.math.mathfxdisplay.expr.NumberExpression;
import com.txi.math.mathfxdisplay.expr.SymbolExpression;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.numbers.complex.Complex;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ExpressionBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionBuilder.class);

    private final String identitySymbol;
    private final Deque<Expression> stack;

    public ExpressionBuilder(String identitySymbol) {
        this.identitySymbol = identitySymbol;
        this.stack = new ArrayDeque<>();
    }

    public void processSymbol(String otherSymbol) {
        Expression expr = createSymbolOrIdentity(otherSymbol);
        LOGGER.info("symbol push {}", expr);
        stack.push(expr);
    }

    private Expression createSymbolOrIdentity(String otherSymbol) {
        if (this.identitySymbol.equals(otherSymbol)) {
            return new IdentityExpression();
        } else {
            return new SymbolExpression(otherSymbol);
        }
    }

    public void createImaginary() {
        var expr = createImaginaryOrIdentity();
        LOGGER.info("imaginary push {}", expr);
        stack.push(expr);
    }

    private Expression createImaginaryOrIdentity() {
        if (this.stack.peek() instanceof NumberExpression entry) {
            var top = this.stack.pop();
            LOGGER.info("pop {}", top);
            return new ComplexNumber(Complex.ofCartesian(0d, entry.value()));
        } else  {
            return new ComplexNumber(Complex.I);
        }
    }
    public void processNumber(TerminalNode numberNode) {
        if (numberNode != null) {
            var expression = new NumberExpression(Double.parseDouble(numberNode.getSymbol().getText()));
            LOGGER.info("number push {}", expression);
            this.stack.push(expression);
        } else {
            LOGGER.info("no number");
        }
    }

    public void processProduct(List<TerminalNode> operatorNodes) {
        int size = operatorNodes.size();
        List<Expression> expressions = createExpressions(size);
        Expression firstExpr = this.stack.pop();
        LOGGER.info("pop {}", firstExpr);
        for (int i = 0; i < size; i++) {
            var secondExpr = expressions.get(i);
            firstExpr = processMulOrDiv(operatorNodes.get(i), firstExpr, secondExpr);
        }
        LOGGER.info("product push {}", firstExpr);
        this.stack.push(firstExpr);
    }

    private @NonNull List<Expression> createExpressions(int size) {
        List<Expression> expressions = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var top = this.stack.pop();
            LOGGER.info("pop {}", top);
            expressions.add(top);
        }
        return expressions.reversed();
    }

    private Expression processMulOrDiv(TerminalNode node, Expression first,  Expression second) {
        LOGGER.debug("operator {} first {} second {}", node.getSymbol().getText(), first, second);
        return switch (node.getText()) {
            case "*" -> processMultiply(first,  second);
            case "/" -> processDivide(first,  second);
            default ->  throw new ParseException("unrecognized operator " + node.getText());
        };
    }

    private Expression processMultiply(Expression first,  Expression second) {
        return switch (second) {
            case NumberExpression secondExpr -> processMultiplyNumber(first, secondExpr);
            case ComplexNumber    secondExpr -> processMultiplyComplex(first, secondExpr);
            default -> processAnyMultiply(first, second);
        };
    }

    private Expression processMultiplyNumber(Expression first, NumberExpression secondExpr) {
        return switch (first) {
            case NumberExpression firstNum  -> multiply(secondExpr, firstNum);
            case ComplexNumber firstComplex -> new ComplexNumber(firstComplex.value().multiply(secondExpr.value()));
            default -> processAnyMultiply(first, secondExpr);
        };
    }

    private static @NonNull NumberExpression multiply(NumberExpression secondExpr, NumberExpression firstNum) {
        var result = new NumberExpression(firstNum.value() * secondExpr.value());
        LOGGER.debug("multiply {} * {} = {}", firstNum.value(),  secondExpr.value(), result.value());
        return result;
    }

    private Expression processMultiplyComplex(Expression first, ComplexNumber secondExpr) {
        return switch (first) {
            case NumberExpression firstExpr  -> new ComplexNumber(secondExpr.value().multiply(firstExpr.value()));
            case ComplexNumber firstComplex ->  new ComplexNumber(firstComplex.value().multiply(secondExpr.value()));
            default -> processAnyMultiply(first, secondExpr);
        };
    }

    private MultiplyExpression processAnyMultiply(Expression firstExpr, Expression secondExpr) {
        return new MultiplyExpression(firstExpr, secondExpr);
    }

    private Expression processDivide(Expression first,  Expression second) {
        return switch (second) {
            case NumberExpression secondExpr -> processDivideNum(first, secondExpr);
            case ComplexNumber    secondExpr -> processDivideComplex(first, secondExpr);
            default -> processAnyDivide(first, second);
        };
    }

    private Expression processDivideNum(Expression first, NumberExpression secondExpr) {
        return switch (first) {
            case NumberExpression firstExpr  -> devide(firstExpr, secondExpr);
            case ComplexNumber firstComplex -> new ComplexNumber(firstComplex.value().divide(secondExpr.value()));
            default -> processAnyDivide(first, secondExpr);
        };
    }

    private static @NonNull NumberExpression devide(NumberExpression firstNum, NumberExpression secondExpr) {
        var result = new NumberExpression(firstNum.value() / secondExpr.value());
        LOGGER.debug("devide {} / {} = {}", firstNum.value(),  secondExpr.value(), result.value());
        return result;
    }

    private Expression processDivideComplex(Expression first, ComplexNumber secondExpr) {
        return switch (first) {
            case NumberExpression firstExpr  -> new ComplexNumber(secondExpr.value().divide(firstExpr.value()));
            case ComplexNumber firstComplex ->  new ComplexNumber(firstComplex.value().divide(secondExpr.value()));
            default -> processAnyDivide(first, secondExpr);
        };
    }

    private DevideExpression processAnyDivide(Expression firstExpr, Expression secondExpr) {
        return new DevideExpression(firstExpr, secondExpr);
    }
}
