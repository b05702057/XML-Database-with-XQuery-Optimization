grammar Xpath;

@header {
package edu.ucsd.cse232b.Antlr4Xpath;
}
// absolute path
ap  : doc '/' rp   #singleAP
    | doc '//' rp  #doubleAP
    ;

// doc
doc: DOC '(' fileName ')';

// relative path
// "#..." defines the node type in the tree.
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

// A lexer and a parser work in sequence.
// The lexer scans the input and produces the matching tokens, the parser then scans the tokens and produces the parsing result.
// We can also use only parsers and produce same rules.
DOC: [dD][oO][cC];

 EQ: '=' | 'eq';
 IS: '==' | 'is';
 ID: [_a-zA-Z][a-zA-Z_0-9]*;

//string: STRING;
 STRING:
    '"'
    (
       ESCAPE
       | ~["\\] // excluding these characters
    )* '"'
    | '\''
    (
       ESCAPE
       | ~['\\]
    )* '\''
 ;

//escape :ESCAPE;
 ESCAPE:
    '\\'
    (
       ['"\\]
    )
 ;
 WHITESPACE:[ \t\n\r]+ -> skip;