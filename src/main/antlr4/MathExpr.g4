grammar MathExpr;

@header {
package com.txi.math.mathfxdisplay.parser;
}

expr:   sum  EOF ;


// 2. Addition und Subtraktion
sum
    :  product ( (PLUS | MINUS) product)*
    ;

// 3. Multiplikation und Division
product
    :   unary (MULTILY_OR_DIV unary)*
    ;

unary
    :   ((PLUS | MINUS))* power
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
PLUS: '+' ;
MINUS: '-' ;
MULTILY_OR_DIV: '*' |   '/' ;
WS          :   [ \t\r\n]+ -> skip ;
COMMENT     :   '//' ~[\r\n]* -> skip ;