grammar MathExpr;

@header {
package com.txi.math.mathfxdisplay.parser;
}

expr:   sum  EOF ;

sum :  product (PLUS_OR_MIUS product)* ;

// 3. Multiplikation und Division
product
    :   unary (MULTILY_OR_DIV unary)*
    ;

unary
    :   (PLUS_OR_MIUS)* power
    ;


power
    :   atom ('^' power)?
    ;

atom
    : value
    | symbol
    | functionCall
    | braceExp
    ;

symbol : ID ;

value
   : NUMBER
   | NUMBER imaginary
   | imaginary
   ;

imaginary
   : 'i'
   | 'j'
   ;

functionCall
    :   ID '(' argList? ')'
    ;

argList
    :   expr (',' expr)*
    ;

braceExp
    : '(' expr ')'
    ;

// Lexer-Regeln (unverändert)
NUMBER      :   [0-9]+ ('.' [0-9]+)? ;
ID          :   [a-zA-Z_][a-zA-Z0-9_]* ;
PLUS_OR_MIUS   : '+' | '-' ;
MULTILY_OR_DIV : '*' |   '/' ;
WS          :   [ \t\r\n]+ -> skip ;
COMMENT     :   '//' ~[\r\n]* -> skip ;