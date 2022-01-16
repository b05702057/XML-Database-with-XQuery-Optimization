grammar Xpath;

@header {
package edu.ucsd.cse232b.Antlr4Xpath;
}

// absolute path
ap  : doc '/' rp   #singleAP
    | doc '//' rp  #doubleAP
    ;

// doc
doc : DOC '(' '"'filename'"' ')';

// relative path
rp  : tagName       #tagRP
    | '*'           #childrenRP
    | '.'           #selfRP
    | '..'          #parentRP
    | 'text()'      #textRP
    | '@' attName   #attRP
    | '(' rp ')'    #commaRP
    | rp '/' rp     #singleSlashRP
    | rp '//' rp    #doubleSlashRP
    | rp '[' f ']'  #filterRP
    | rp ',' rp     #commaRP
    ;

// path filter
f   : rp            #rpFilter
    | rp EQ rp      #eqFilter
    | rp IS rp      #isFilter
    | '(' f ')'     #braceFilter
    | f 'and' f     #andFilter
    | f 'or' f      #orFilter
    | 'not' f       #notFilter
    ;

 tagName: ID;
 attName: ID;

 EQ: '=' | 'eq';
 IS: '==' | 'is';
 ID: [a-zA-Z0-9_-]+ ;

 DOC: 'doc' | 'document';
 filename: FILENAME;
 FILENAME: [a-zA-Z0-9._]+;

 WHITESPACE:[ \t\n\r]+ -> skip;