package edu.ucsd.cse232b.XpathImpl;

//import com.apple.laf.AquaButtonBorder;
import edu.ucsd.cse232b.Antlr4Xpath.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graalvm.compiler.graph.NodeMap;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
* (1) variable names: frontierNodes, evalNode
* (2) Do we need evalNode?
* (3) The definition of frontier nodes should not change.
 * => It should always be the nodes that satisfy current conditions, and we don't need to modify the value for a specific traversal.
* (4) redundant assignment or return => "this.frontierNodes = res" and "return res"
* (5) Should we use the KMP algorithm? Is the O(N * M) time complexity tolerable?
* (6) visitDoc and visitFileName?
* (7) text nodes and attribute nodes, child nodes?
*/

public class CustomizedXpathVisitor extends XpathBaseVisitor<LinkedList>{
    private static final Logger logger = Logger.getLogger(CustomizedXpathVisitor.class.getName());
    LinkedList<Node> frontierNodes = new LinkedList<Node>(); // the nodes under the current path

    // get all children and sub children for the double slash condition
    public LinkedList<Node> getAllChildren(Node node) {
        LinkedList<Node> res = new LinkedList<>(); // result

        NodeList childrenNodes = node.getChildNodes();
        Node curChild;
        for (int i = 0; i < childrenNodes.getLength(); i++) {
            curChild = childrenNodes.item(i);
            res.add(curChild); // add a child
            res.addAll(getAllChildren(curChild)); // add sub children
        }
        return res;
    }

    // visit "//RP"
    public LinkedList<Node> visitDoubleSlash(XpathParser.RpContext ctx) {
        LinkedList<Node> tmp = this.frontierNodes;
        for (Node node : tmp){
            this.frontierNodes.addAll(getAllChildren(node)); // all children nodes
        }
        return visit(ctx);
    }

    @Override
    public LinkedList<Node> visitSingleAP(XpathParser.SingleAPContext ctx) { // single slash
        this.frontierNodes = visit(ctx.doc()); // Only the document root node is in the list.
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitDoubleAP(XpathParser.DoubleAPContext ctx) { // double slash (need to traverse the whole tree)
        this.frontierNodes = visit(ctx.doc()); // Only the document root node is in the list.
        return visitDoubleSlash(ctx.rp());
    }

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
    public LinkedList<Node> visitDoubleSlashRP(XpathParser.DoubleSlashRPContext ctx) {
        this.frontierNodes = visit(ctx.rp(0)); // nodes that satisfy the first RP
        return visitDoubleSlash(ctx.rp(1));
    }

    @Override
    public LinkedList<Node> visitTextRP(XpathParser.TextRPContext ctx) {
        NodeList children;
        Node child;
        LinkedList<Node> res = new LinkedList<>();
        for (Node node : this.frontierNodes) {
            children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) { // iterate the children to find the text nodes
                child = children.item(i);
                if (child.getTextContent() == ctx.getText()) {
                    res.add(child);
                }
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitAttRP(XpathParser.AttRPContext ctx) {
        LinkedList<Node> res = new LinkedList<>();
        String attName;
        NamedNodeMap attributes;
        for (Node node : this.frontierNodes) {
            attributes = node.getAttributes(); // get all attributes of a node
            attName = ctx.attName().ID().toString();
            res.add(attributes.getNamedItem(attName));
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitParentRP(XpathParser.ParentRPContext ctx) {
        LinkedList<Node> res = new LinkedList<>();
        for (Node node : this.frontierNodes) {
            res.add(node.getParentNode());
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitSelfRP(XpathParser.SelfRPContext ctx) {
        return this.frontierNodes;
    }

    @Override
    public LinkedList<Node> visitFilterRP(XpathParser.FilterRPContext ctx) {
        this.frontierNodes = visit(ctx.rp());
        return visit(ctx.f());
    }

    @Override
    public LinkedList<Node> visitCommaRP(XpathParser.CommaRPContext ctx) {
        LinkedList<Node> res = visit(ctx.rp(0));
        res.addAll(visit(ctx.rp(1)));
        return res;
    }

    @Override
    public LinkedList<Node> visitChildrenRP(XpathParser.ChildrenRPContext ctx) {
        LinkedList<Node> res = new LinkedList<>();
        NodeList children;
        for (Node node : this.frontierNodes) {
            children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                res.add(children.item(i));
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitTagRP(XpathParser.TagRPContext ctx) {
        NodeList children;
        Node child;
        LinkedList<Node> res = new LinkedList<>();
        for (Node node : this.frontierNodes) {
            children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) { // iterate the children to find the text nodes
                child = children.item(i);
                String tagName = ctx.tagName().ID().toString();
                if (child.getNodeName() == tagName)  {
                    res.add(child);
                }
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitSingleSlashRP(XpathParser.SingleSlashRPContext ctx) {
        this.frontierNodes = visit(ctx.rp(0));
        return visit(ctx.rp(1));
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
            LinkedList<Node> l = visit(ctx.rp(0)); // left nodes

            this.frontierNodes = evalNode;
            LinkedList<Node> r = visit(ctx.rp(1)); // right nodes

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

    @Override
    public LinkedList<Node> visitTagName(XpathParser.TagNameContext ctx) {
        logger.info("visit TagName");
        LinkedList<Node> res = new LinkedList<>(); // result nodes

        for (Node node: this.frontierNodes){
            // https://docs.oracle.com/javase/7/docs/api/org/w3c/dom/Node.html
            // Only element nodes have tag names.
            if (node.getNodeType() == node.ELEMENT_NODE && node.getNodeName() == ctx.getText())
                res.add(node);
        }
        this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitAttName(XpathParser.AttNameContext ctx) {
        logger.info("visit AttName");
        LinkedList<Node> res = new LinkedList<>(); // result nodes

        for (Node node : this.frontierNodes) {
            Node attNode = node.getAttributes().getNamedItem(ctx.getText());
            if (attNode != null)
                res.add(attNode);
        }
        this.frontierNodes = res;
        return res;
    }

//    @Override
//    public LinkedList<Node> visitFileName(XpathParser.FileNameContext ctx) {
//
//    }
}
