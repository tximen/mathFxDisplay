package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.AddExpression;
import com.txi.math.mathfxdisplay.expr.ComplexNumber;
import com.txi.math.mathfxdisplay.expr.DevideExpression;
import com.txi.math.mathfxdisplay.expr.Expression;
import com.txi.math.mathfxdisplay.expr.IdentityExpression;
import com.txi.math.mathfxdisplay.expr.MultiplyExpression;
import com.txi.math.mathfxdisplay.expr.NumberExpression;
import com.txi.math.mathfxdisplay.expr.SymbolExpression;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.numbers.complex.Complex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MathExpressionListener implements MathExprListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MathExpressionListener.class);


    private final ExpressionBuilder builder;


    public MathExpressionListener(String identitySymbol) {
        this.builder = new ExpressionBuilder(identitySymbol);

    }

    public Expression expression() {
       /* if (stack.size() != 1) {
            LOGGER.error("stack entries {}", this.stack);
            throw new IllegalStateException("stack has more than one expression");
        }
        return stack.pop();


        */
        return null;
    }

    @Override
    public void enterExpr(MathExprParser.ExprContext context) {
        System.out.println("enterExpr");
        //LOGGER.info("enterExpr");

    }

    @Override
    public void exitExpr(MathExprParser.ExprContext ctx) {
        System.out.println("exitExpr");
        LOGGER.info("exitExpr");
        /*
        if (stack.isEmpty()) {
            throw new ParseException("empty expression stack");
        }
        if (stack.size() == 1) {
            LOGGER.info("exitExpr: {}", stack.peek() );
        } else {
            throw new ParseException("more than one expression");
        }

         */
    }

    @Override
    public void enterSymbol(MathExprParser.SymbolContext context) {
        System.out.println("enterSymbol");
        //LOGGER.info("enterSymbol");
        this.builder.processSymbol(context.ID().getText());
    }



    @Override
    public void exitSymbol(MathExprParser.SymbolContext ctx) {
        System.out.println("exitSymbol");
        //LOGGER.info("exitSymbol");
    }

    @Override
    public void enterValue(MathExprParser.ValueContext ctx) {
        System.out.println("enterValue");
        //LOGGER.info("enterValue");
        this.builder.processNumber(ctx.NUMBER());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitValue(MathExprParser.ValueContext ctx) {
        System.out.println("exitValue");
        //LOGGER.info("exitValue");
    }


    @Override
    public void enterImaginary(MathExprParser.ImaginaryContext ctx) {
        System.out.println("enterImaginary");
        this.builder.createImaginary();
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitImaginary(MathExprParser.ImaginaryContext ctx) {
        System.out.println("exitImaginary");
        //LOGGER.info("exitImaginary");
    }

    @Override
    public void enterSum(MathExprParser.SumContext ctx) {
        System.out.println("enterSum");
        //LOGGER.info("enterSum");
    }

    @Override
    public void exitSum(MathExprParser.SumContext context) {
        System.out.println("exitSum");
        LOGGER.info("exitSum");
        /*
        if (context.sum() != null && context.sum().size() == 2) {
            String operator = context.PLUS_MINUS().getText();
            switch (operator) {
                case "+" -> processAddExpression();
                case "-" -> processSubExpression();
                default -> {
                    LOGGER.error("Invalid operator: {}", operator);
                    throw new ParseException("unknown operator: " + operator);
                }
            }
        }
*/
    }

    @Override
    public void enterProduct(MathExprParser.ProductContext ctx) {
        System.out.println("enterProduct");
        //LOGGER.info("enterProduct");
    }

    @Override
    public void exitProduct(MathExprParser.ProductContext context) {
        int size = context.MULTILY_OR_DIV().size();
        LOGGER.debug("exitProduct size {}", size);
        if (size > 0) {
            this.builder.processProduct(context.MULTILY_OR_DIV());
        }
    }

    @Override
    public void enterUnary(MathExprParser.UnaryContext ctx) {
        System.out.println("enterUnary");
        //LOGGER.info("enterUnary");
    }

    @Override
    public void exitUnary(MathExprParser.UnaryContext ctx) {
        System.out.println("exitUnary");
        //LOGGER.info("exitUnary");
    }

    @Override
    public void enterPower(MathExprParser.PowerContext ctx) {
        System.out.println("enterPower");
        //LOGGER.info("enterPower");
    }

    @Override
    public void exitPower(MathExprParser.PowerContext ctx) {
        System.out.println("exitPower");
        //LOGGER.info("exitPower");
    }

    @Override
    public void enterAtom(MathExprParser.AtomContext ctx) {
        System.out.println("enterAtom");
        //LOGGER.info("enterAtom");
    }

    @Override
    public void exitAtom(MathExprParser.AtomContext ctx) {
        System.out.println("exitAtom");
        //LOGGER.info("exitAtom");
    }

    @Override
    public void enterFunctionCall(MathExprParser.FunctionCallContext ctx) {
        System.out.println("enterFunctionCall");
        //LOGGER.info("enterFunctionCall");
    }

    @Override
    public void exitFunctionCall(MathExprParser.FunctionCallContext ctx) {
        System.out.println("exitFunctionCall");
        //LOGGER.info("exitFunctionCall");
    }

    @Override
    public void enterArgList(MathExprParser.ArgListContext ctx) {
        System.out.println("enterArgList");
        //LOGGER.info("enterArgList");
    }

    @Override
    public void exitArgList(MathExprParser.ArgListContext ctx) {
        System.out.println("exitArgList");
        //LOGGER.info("exitArgList");
    }

    @Override
    public void enterBraceExp(MathExprParser.BraceExpContext ctx) {
        System.out.println("enterBraceExp");
        //LOGGER.info("enterBraceExp");
    }

    @Override
    public void exitBraceExp(MathExprParser.BraceExpContext ctx) {
        System.out.println("exitBraceExp");
        //LOGGER.info("exitBraceExp");
    }



    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext context) {
    }

    @Override
    public void visitTerminal(TerminalNode node) {
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println("visitErrorNode");
        //LOGGER.info("visitErrorNode");
    }

/**
    private void processAddExpression() {
        Expression second = this.stack.pop();
        switch (second) {
            case NumberExpression secondExpr -> processAdd(secondExpr);
            case ComplexNumber    secondExpr -> processAdd(secondExpr);
            default -> processAnyAdd(this.stack.pop(), second);
        }
    }

    private void processAdd(NumberExpression secondExpr) {
        Expression firstExpr = this.stack.pop();
        switch (firstExpr) {
            case NumberExpression firstNum  -> this.stack.push(new NumberExpression(firstNum.value() + secondExpr.value()));
            case ComplexNumber firstComplex -> this.stack.push(new ComplexNumber(firstComplex.value().add(secondExpr.value())));
            default -> processAnyAdd(firstExpr, secondExpr);
        }
    }

    private void processAdd(ComplexNumber secondExpr) {
        Expression firstExpr = this.stack.pop();
        switch (firstExpr) {
            case NumberExpression firstNum  -> this.stack.push(new ComplexNumber(secondExpr.value().add(firstNum.value())));
            case ComplexNumber firstComplex -> this.stack.push(new ComplexNumber(firstComplex.value().add(secondExpr.value())));
            default -> processAnyAdd(firstExpr, secondExpr);
        }
    }

    private void processAnyAdd(Expression firstExpr, Expression secondExpr) {
        this.stack.push(new AddExpression(firstExpr, secondExpr));
    }

    private void processSubExpression() {
        System.out.println("processing add expression");
    }
 */
}
