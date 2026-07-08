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
        this.builder.processSymbol(context.IDENTIFIER().getText());
    }

    @Override
    public void exitSymbol(MathExprParser.SymbolContext ctx) {
         LOGGER.info("exitSymbol");
    }

    @Override
    public void enterValue(MathExprParser.ValueContext ctx) {
        LOGGER.debug("enterValue");
        this.builder.processNumber(ctx.Number());
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
        LOGGER.debug("exitImaginary");
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
        LOGGER.debug("enterProduct");
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
        this.builder.processUnary(context.PLUS_OR_MIUS());
    }

    @Override
    public void enterPower(MathExprParser.PowerContext ctx) {
    }

    @Override
    public void exitPower(MathExprParser.PowerContext context) {
        if (context.EXP() != null && "^".equals(context.EXP().getText())) {
            this.builder.processPower();
        }
    }

    @Override
    public void enterAtom(MathExprParser.AtomContext ctx) {
        LOGGER.debug("enterAtom");
    }

    @Override
    public void exitAtom(MathExprParser.AtomContext context) {

        LOGGER.debug("exitAtom");
    }



    public void enterFctCall(MathExprParser.FctCallContext context) {
        LOGGER.debug("enterFctCall");

    }
    /**
     * Exit a parse tree produced by {@link MathExprParser#fctCall}.
     * @param context the parse tree
     */
    public void exitFctCall(MathExprParser.FctCallContext context) {
        LOGGER.debug("exitFctCall");
        this.builder.processFunctionCall(context.getChild(0).getText());
    }


    @Override
    public void enterBraceExp(MathExprParser.BraceExpContext ctx) {
    }

    @Override
    public void exitBraceExp(MathExprParser.BraceExpContext context) {
        this.builder.processBrace();

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
