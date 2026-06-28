package com.txi.math.mathfxdisplay.parser;

import com.txi.math.mathfxdisplay.expr.Expression;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ExpressionParser(String identitySymbol) {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionParser.class);

    public Expression parse(String message) {
        LOGGER.info("parse [{}]", message);
        return walkTree(new MathExprParser(tokens(message)).expr());
    }

    private Expression walkTree(ParseTree tree) {
        MathExpressionListener listener = new MathExpressionListener(this.identitySymbol);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener.expression();
    }

    private CommonTokenStream tokens(String message) {
        return new CommonTokenStream(new MathExprLexer(CharStreams.fromString(message)));
    }
}
