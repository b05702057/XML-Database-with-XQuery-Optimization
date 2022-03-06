grammar Xquery;

@header {
// The package keyword encapsulates a group of classes, sub packages and interfaces.
package edu.ucsd.cse232b.Antlr4Xquery; // add the package name at the beginning of ANTLR generated Java files
}

xq  : var                                               #varXQ
    | STRING                                            #stringXQ
    | ap                                                #apXQ
    | '(' xq ')'                                        #braceXQ
    | xq ',' xq                                         #commaXQ
    | xq '/' rp                                         #singleSlashXQ
    | xq '//' rp                                        #doubleSlashXQ
    | openTag '{' xq '}' closeTag                       #tagXQ
    | forClause letClause? whereClause? returnClause    #FLWR
    | letClause xq                                      #letXQ
    | joinClause                                        #joinXQ
    ;

forClause: 'for' var 'in' xq (',' var 'in' xq)* ;
letClause: 'let' var ':=' xq (',' var ':=' xq)* ;
whereClause: 'where' cond ;
returnClause: 'return' xq ;
joinClause: 'join' '(' xq ',' xq ',' idList ',' idList ')';

cond : xq EQ xq                                                 #eqCond
     | xq IS xq                                                 #isCond
     | 'empty' '(' xq ')'                                       #emptyCond
     | 'some' var 'in' xq (',' var 'in' xq)* 'satisfies' cond   #parSatisfyCond
     | '(' cond ')'                                             #braceCond
     | cond 'and' cond                                          #andCond
     | cond 'or' cond                                           #orCond
     | 'not' cond                                               #notCond
     ;

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
    | rp EQ STRING #stringFilter
    | rp EQ rp      #eqFilter
    | rp IS rp      #isFilter
    | '(' f ')'     #braceFilter
    | f 'and' f     #andFilter
    | f 'or' f      #orFilter
    | 'not' f       #notFilter
    ;

var: '$' ID ;
openTag: '<' tagName '>' ;
closeTag: '</' tagName '>' ;

 tagName: ID;
 attName: ID;
 fileName: STRING;

// A lexer and a parser work in sequence.
// The lexer scans the input and produces the matching tokens, the parser then scans the tokens and produces the parsing result.
// We can also use only parsers and produce same rules.
DOC: [dD][oO][cC] | 'document';

 EQ: '=' | 'eq';
 STRINGEQ: '=';
 IS: '==' | 'is';
 ID: [_a-zA-Z][a-zA-Z_0-9]*;
 idList: '[' ID (',' ID)* ']';

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
