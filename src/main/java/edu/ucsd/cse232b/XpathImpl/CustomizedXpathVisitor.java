package edu.ucsd.cse232b.XpathImpl;

import edu.ucsd.cse232b.Antlr4Xpath.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CustomizedXpathVisitor extends XpathBaseVisitor<LinkedList>{
    private static final Logger logger = Logger.getLogger(CustomizedXpathVisitor.class.getName());
    LinkedList<Node> frontierNodes = new LinkedList<Node>();

    @Override
    public LinkedList<Node> visitDoc(XpathParser.DocContext ctx) {
        // https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        // https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html
        logger.info("visit Doc node");
        String fileName = ctx.fileName().STRING().getText();
        File xmlFile = new File(fileName.substring(1, fileName.length() - 1));
        LinkedList<Node> res = new LinkedList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            res.add(doc);
            this.frontierNodes = res;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public LinkedList<Node> visitEqFilter(XpathParser.EqFilterContext ctx) {
        logger.info("visit EqFilter");
        LinkedList<Node> tmp = this.frontierNodes;
        LinkedList<Node> res = new LinkedList<>();

        for (Node node: tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);

            this.frontierNodes = evalNode;
            LinkedList<Node> l = visit(ctx.rp(0));

            this.frontierNodes = evalNode;
            LinkedList<Node> r = visit(ctx.rp(1));

            for (Node ln: l)
                for (Node rn: r)
                    if (ln.isEqualNode(rn) && !res.contains(node))
                        res.add(node);
        }

        //this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitIsFilter(XpathParser.IsFilterContext ctx) {
        logger.info("visit IsFilter");
        LinkedList<Node> tmp = this.frontierNodes;
        LinkedList<Node> res = new LinkedList<>();

        for (Node node: tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);

            this.frontierNodes = evalNode;
            LinkedList<Node> l = visit(ctx.rp(0));

            this.frontierNodes = evalNode;
            LinkedList<Node> r = visit(ctx.rp(1));

            for (Node ln: l)
                for (Node rn: r)
                    if (ln.isSameNode(rn) && !res.contains(node))
                        res.add(node);
        }

        //this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitRpFilter(XpathParser.RpFilterContext ctx) {
        logger.info("visit RpFilter");
        LinkedList<Node> res = new LinkedList<>();

        for (Node node: this.frontierNodes) {
            if (visit(ctx.rp()).size() != 0)
                res.add(node);
        }

        //this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitBraceFilter(XpathParser.BraceFilterContext ctx) {
        logger.info("visit BraceFilter");
        return visit(ctx.f());
    }

    @Override
    public LinkedList<Node> visitOrFilter(XpathParser.OrFilterContext ctx) {
        logger.info("visit OrFilter");
        LinkedList<Node> res;

        HashSet<Node> ls = new HashSet<>(visit(ctx.f(0)));
        HashSet<Node> rs = new HashSet<>(visit(ctx.f(1)));

        ls.addAll(rs);
        res = new LinkedList<>(ls);

        return res;
    }

    @Override
    public LinkedList<Node> visitNotFilter(XpathParser.NotFilterContext ctx) {
        logger.info("visit NotFilter");
        LinkedList<Node> res;

        HashSet<Node> current = new HashSet<>(this.frontierNodes);
        HashSet<Node> diff = new HashSet<>(visit(ctx.f()));

        current.removeAll(diff);
        res = new LinkedList<>(current);

        return res;
    }

    @Override
    public LinkedList<Node> visitAndFilter(XpathParser.AndFilterContext ctx) {
        logger.info("visit AndFilter");
        LinkedList<Node> res;

        HashSet<Node> ls = new HashSet<>(visit(ctx.f(0)));
        HashSet<Node> rs = new HashSet<>(visit(ctx.f(1)));

        ls.retainAll(rs);
        res = new LinkedList<>(ls);

        return res;
    }

    @Override
    public LinkedList<Node> visitStringFilter(XpathParser.StringFilterContext ctx) {
        logger.info("visit StringFilter");
        LinkedList<Node> res = new LinkedList<>();
        String str = ctx.STRING().getText();
        str = str.substring(1, str.length() - 1);

        this.frontierNodes = visit(ctx.rp());
        for (Node node: this.frontierNodes) {
            if (node.getNodeName() == str && !res.contains(node))
                res.add(node);
        }

        //this.frontierNodes = res;
        return res;
    }

//    @Override
//    public LinkedList<Node> visitFileName(XpathParser.FileNameContext ctx) {
//
//    }
}
