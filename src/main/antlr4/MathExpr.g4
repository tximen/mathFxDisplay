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
    : fctCall
    | value
    | braceExp
    | symbol
    ;

fctCall
   : SIN braceExp
   | COS braceExp
   | SQRT braceExp
   | EXPO braceExp
   ;

symbol : IDENTIFIER ;

value
   : Number
   | Number imaginary
   | imaginary

   ;

imaginary
   : 'i'
   | 'j'
   ;



braceExp
    : '(' expr ')'
    ;

SIN : 'sin';
COS : 'cos';
SQRT: 'sqrt';
EXPO : 'exp';


IDENTIFIER: Letter LetterOrDigit*;

fragment LetterOrDigit: Letter | [0-9];

fragment Letter:
    [a-zA-Z$_]                        // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF]   // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
;

fragment Digits: [0-9] ([0-9_]* [0-9])?;

// Lexer
Number      :   Digits ('.' Digits)? ;
PLUS_OR_MIUS   : '+' | '-' ;
MULTILY_OR_DIV : '*' |   '/' ;
EXP            : '^';
WS             : [ \t\r\n]+ -> skip ;
COMMENT        : '//' ~[\r\n]* -> skip ;