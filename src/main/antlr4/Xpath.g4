grammar Xpath;

@header {
package edu.ucsd.cse232b.Antlr4Xpath;
}
// absolute path
ap  : doc '/' rp   #singleAP
    | doc '//' rp  #doubleAP
    ;

// doc
doc: 'doc(' fileName ')' | 'document(' fileName ')';

// relative path
rp  : tagName       #tagRP
    | '*'           #childrenRP
    | '.'           #selfRP
    | '..'          #parentRP
    | 'text()'      #textRP
    | '@' attName   #attRP
    | '(' rp ')'    #braceRP
    | rp '/' rp     #singleSlashRP
    | rp '//' rp    #doubleSlashRP
    | rp '[' f ']'  #filterRP
    | rp ',' rp     #commaRP
    ;

// path filter
f   : rp            #rpFilter
    | rp EQ rp      #eqFilter
    | rp IS rp      #isFilter
    | rp '=' STRING #stringFilter
    | '(' f ')'     #braceFilter
    | f 'and' f     #andFilter
    | f 'or' f      #orFilter
    | 'not' f       #notFilter
    ;

 tagName: ID;
 attName: ID;
 fileName: STRING;

 EQ: '=' | 'eq';
 IS: '==' | 'is';
 ID: [_a-zA-Z][a-zA-Z_0-9]*;

 STRING:
    '"'
    (
       ESCAPE
       | ~["\\]
    )* '"'
    | '\''
    (
       ESCAPE
       | ~['\\]
    )* '\''
 ;

 ESCAPE:
    '\\'
    (
       ['"\\]
    )
 ;
 WHITESPACE:[ \t\n\r]+ -> skip;