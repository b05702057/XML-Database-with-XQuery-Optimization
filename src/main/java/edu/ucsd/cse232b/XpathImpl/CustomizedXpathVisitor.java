package edu.ucsd.cse232b.XpathImpl;

import edu.ucsd.cse232b.Antlr4Xpath.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.File;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CustomizedXpathVisitor extends XpathBaseVisitor<LinkedList>{
    private static final Logger logger = Logger.getLogger(CustomizedXpathVisitor.class.getName());
    LinkedList<Node> frontierNodes = new LinkedList<>(); // the nodes under the current path

    // get all children and sub children for the double slash condition
    public LinkedList<Node> getAllChildren(Node node) {
        LinkedList<Node> res = new LinkedList<>(); // result

        NodeList childrenNodes = node.getChildNodes();
        Node curChild; // current child
        for (int i = 0; i < childrenNodes.getLength(); i++) {
            curChild = childrenNodes.item(i);
            res.add(curChild); // add a child
            res.addAll(getAllChildren(curChild)); // add sub children
        }
        return res;
    }

    public LinkedList<Node> visitDoubleSlash(XpathParser.RpContext ctx) {
        LinkedList<Node> tmp = new LinkedList<>();
        for (Node node : this.frontierNodes) {
            tmp.addAll(getAllChildren(node)); // all children nodes
        }
        for (Node node : tmp) {
            // Edge Case => The query is doc("file")//A//A, and the DOM tree consists only A nodes.
            if (!this.frontierNodes.contains(node)) {
                this.frontierNodes.add(node);
            }
        }
        return visit(ctx);
    }

    @Override
    public LinkedList<Node> visitSingleAP(XpathParser.SingleAPContext ctx) { // single slash
        logger.info("visit single AP node");
        this.frontierNodes = visit(ctx.doc()); // Only the document root node is in the list.
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitDoubleAP(XpathParser.DoubleAPContext ctx) { // double slash (need to traverse the whole tree)
        logger.info("visit double AP node");
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
        dbf.setIgnoringElementContentWhitespace(true);  // eliminate white space in element content

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();  // as a node
            res.add(doc);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitDoubleSlashRP(XpathParser.DoubleSlashRPContext ctx) {
        logger.info("visit double RP node");
        this.frontierNodes = visit(ctx.rp(0)); // nodes that satisfy the first RP
        return visitDoubleSlash(ctx.rp(1));
    }

    @Override
    public LinkedList<Node> visitTextRP(XpathParser.TextRPContext ctx) {
        logger.info("visit text RP node");
        String curText;
        String text = ctx.getText();
        LinkedList<Node> res = new LinkedList<>();
        for (Node node : this.frontierNodes) {
            curText = node.getTextContent();
            if (curText.equals(text)) {
                res.add(node);
            }
        }
        return this.frontierNodes;
    }

    @Override
    public LinkedList<Node> visitAttRP(XpathParser.AttRPContext ctx) {
        logger.info("visit attribute RP node");
        LinkedList<Node> res = new LinkedList<>();
        String attName;
        NamedNodeMap attributes;
        Node attribute;
        for (Node node : this.frontierNodes) {
            attributes = node.getAttributes(); // get all attributes of a node
            for (int i = 0; i < attributes.getLength(); i++) {
                res.add(attributes.item(i));
            }
        }
        this.frontierNodes = res;
        return visit(ctx.attName());
    }

    @Override
    public LinkedList<Node> visitParentRP(XpathParser.ParentRPContext ctx) {
        logger.info("visit parent RP node");
        LinkedList<Node> res = new LinkedList<>();
        Node parentNode;
        for (Node node : this.frontierNodes) {
            parentNode = node.getParentNode();
            // Different nodes can have the same parent node.
            if (!res.contains(parentNode)) {
                res.add(parentNode);
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitSelfRP(XpathParser.SelfRPContext ctx) {
        logger.info("visit self RP node");
        return this.frontierNodes;
    }

    @Override
    public LinkedList<Node> visitFilterRP(XpathParser.FilterRPContext ctx) {
        logger.info("visit filter RP node");
        this.frontierNodes = visit(ctx.rp());
        return visit(ctx.f());
    }

    @Override
    public LinkedList<Node> visitCommaRP(XpathParser.CommaRPContext ctx) {
        logger.info("visit comma RP node");
        LinkedList<Node> res = visit(ctx.rp(0));
        LinkedList<Node> res2 = visit(ctx.rp(1));

        for (Node node : res2) {
            // res and res2 can have overlapping nodes
            if (!res.contains(node)) {
                res.add(node);
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitBraceRP(XpathParser.BraceRPContext ctx) {
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitChildrenRP(XpathParser.ChildrenRPContext ctx) {
        logger.info("visit children RP node");
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
        logger.info("visit tag RP node");
        LinkedList<Node> tmp = new LinkedList<>();
        NodeList children;
        Node child;

        for (Node node : this.frontierNodes) {
            children = node.getChildNodes();
            // iterate the children to find the nodes with the right tag
            for (int i = 0; i < children.getLength(); i++) {
                child = children.item(i);
                // Only element nodes have tag names.
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    tmp.add(child);
                }
            }
        }
        this.frontierNodes = tmp;
        return visit(ctx.tagName());
    }

    @Override
    public LinkedList<Node> visitSingleSlashRP(XpathParser.SingleSlashRPContext ctx) {
        logger.info("visit single RP node");
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

            // Why not break the loop after finding the first equal node?
            for (Node ln: l)
                for (Node rn: r)
                    if (ln.isEqualNode(rn) && !res.contains(node))
                        res.add(node);
        }

        this.frontierNodes = res;
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
            logger.info("test" +ctx.rp(0).getText());
            LinkedList<Node> l = visit(ctx.rp(0));

            this.frontierNodes = evalNode;
            LinkedList<Node> r = visit(ctx.rp(1));

            // Why not break the loop after finding the first equal node?
            for (Node ln: l)
                for (Node rn: r)
                    if (ln.isSameNode(rn) && !res.contains(node))
                        res.add(node);
        }

        this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitRpFilter(XpathParser.RpFilterContext ctx) {
        logger.info("visit RpFilter");
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> tmp = this.frontierNodes;

        for (Node node: tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);
            this.frontierNodes = evalNode;
            if (visit(ctx.rp()).size() != 0)
                res.add(node);
        }

        this.frontierNodes = res;
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
        LinkedList<Node> tmp = this.frontierNodes;

        HashSet<Node> ls = new HashSet<>(visit(ctx.f(0)));

        this.frontierNodes = tmp;
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

        this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitAndFilter(XpathParser.AndFilterContext ctx) {
        logger.info("visit AndFilter");
        LinkedList<Node> res;
        LinkedList<Node> tmp = this.frontierNodes;

        HashSet<Node> ls = new HashSet<>(visit(ctx.f(0)));

        this.frontierNodes = tmp;
        HashSet<Node> rs = new HashSet<>(visit(ctx.f(1)));

        ls.retainAll(rs);
        res = new LinkedList<>(ls);

        return res;
    }

    @Override
    public LinkedList<Node> visitStringFilter(XpathParser.StringFilterContext ctx) {
        logger.info("visit StringFilter");
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> tmp = this.frontierNodes;

        String str = ctx.STRING().getText();
        str = str.substring(1, str.length() - 1);

        for (Node node: tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);
            this.frontierNodes = evalNode;

            LinkedList<Node> l = visit(ctx.rp());
            for (Node ln: l) {
                if (ln.getTextContent().equals(str) && !res.contains(node)) {
                    logger.info("add a node");
                    res.add(node);
                }
            }
        }

        this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitTagName(XpathParser.TagNameContext ctx) {
        logger.info("visit TagName");
        LinkedList<Node> res = new LinkedList<>(); // result nodes

        // Frontier nodes are guaranteed to be element nodes.
        for (Node node: this.frontierNodes){
            if (node.getNodeName().equals(ctx.getText()))
                res.add(node);
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitAttName(XpathParser.AttNameContext ctx) {
        logger.info("visit AttName");
        LinkedList<Node> res = new LinkedList<>(); // result nodes

        for (Node node: this.frontierNodes){
            if (node.getNodeName().equals(ctx.getText())) {
                res.add(node);
            }
        }
        return res;
    }
}
