package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.Expression;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathExpressionListener implements MathExprListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MathExpressionListener.class);

    private final ExpressionBuilder builder;

    public MathExpressionListener(String identitySymbol) {
        this.builder = new ExpressionBuilder(identitySymbol);
    }

    public Expression expression() {
        this.builder.validateEndStack();
        return this.builder.expression();
    }

    @Override
    public void enterExpr(MathExprParser.ExprContext context) {
        LOGGER.debug("enterExpr");
    }

    @Override
    public void exitExpr(MathExprParser.ExprContext ctx) {
        LOGGER.debug("exitExpr");

    }

    @Override
    public void enterSymbol(MathExprParser.SymbolContext context) {
        LOGGER.debug("enterSymbol");
        this.builder.processSymbol(context.ID().getText());
    }

    @Override
    public void exitSymbol(MathExprParser.SymbolContext ctx) {
         LOGGER.info("exitSymbol");
    }

    @Override
    public void enterValue(MathExprParser.ValueContext ctx) {
        LOGGER.debug("enterValue");
        this.builder.processNumber(ctx.NUMBER());
    }

    @Override
    public void exitValue(MathExprParser.ValueContext ctx) {
        LOGGER.debug("exitValue");
    }

    @Override
    public void enterImaginary(MathExprParser.ImaginaryContext ctx) {
        LOGGER.debug("enterImaginary");
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
    public void enterSum(MathExprParser.SumContext context) {
        LOGGER.debug("enterSum");
    }

    @Override
    public void exitSum(MathExprParser.SumContext context) {
        LOGGER.debug("exitSum");
        this.builder.processSumOrProduct(context.PLUS_OR_MIUS());
    }

    @Override
    public void enterProduct(MathExprParser.ProductContext ctx) {
        System.out.println("enterProduct");
        //LOGGER.info("enterProduct");
    }

    @Override
    public void exitProduct(MathExprParser.ProductContext context) {
        LOGGER.debug("exitProduct");
        this.builder.processSumOrProduct(context.MULTILY_OR_DIV());
    }

    @Override
    public void enterUnary(MathExprParser.UnaryContext ctx) {
        LOGGER.debug("enterUnary");
    }

    @Override
    public void exitUnary(MathExprParser.UnaryContext context) {
        LOGGER.debug("exitUnary");
        this.builder.processUnary(context.PLUS_OR_MIUS());
    }

    @Override
    public void enterPower(MathExprParser.PowerContext ctx) {
        System.out.println("enterPower");
        //LOGGER.info("enterPower");
    }

    @Override
    public void exitPower(MathExprParser.PowerContext context) {
        if (context.EXP() != null && "^".equals(context.EXP().getText())) {
            this.builder.processPower();
        }
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
    public void enterBraceExp(MathExprParser.BraceExpContext ctx) {
    }

    @Override
    public void exitBraceExp(MathExprParser.BraceExpContext ctx) {
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
    }

}
