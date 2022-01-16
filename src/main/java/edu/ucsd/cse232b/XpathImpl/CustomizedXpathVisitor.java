package edu.ucsd.cse232b.XpathImpl;

import edu.ucsd.cse232b.Antlr4Xpath.*;
import java.util.LinkedList;

import org.w3c.dom.Node;

public class CustomizedXpathVisitor extends XpathBaseVisitor<LinkedList>{

    @Override
    public LinkedList<Node> visitDoubleAP(XpathParser.DoubleAPContext ctx) {
        visit(ctx.doc());
        return visit(ctx.rp());
    }
}
