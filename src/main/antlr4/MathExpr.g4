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
    :   atom (EXP power)?
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
    :   FCT_NAME braceExp
    ;

braceExp
    : '(' expr ')'
    ;

// Lexer
NUMBER      :   [0-9]+ ('.' [0-9]+)? ;
ID          :   [a-zA-Z_][a-zA-Z0-9_]* ;
PLUS_OR_MIUS   : '+' | '-' ;
MULTILY_OR_DIV : '*' |   '/' ;
FCT_NAME       : 'sin' | 'cos' | 'exp' | 'ln';
EXP            : '^';
WS             :   [ \t\r\n]+ -> skip ;
COMMENT        :   '//' ~[\r\n]* -> skip ;