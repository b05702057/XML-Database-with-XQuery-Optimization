package edu.ucsd.cse232b.XqueryImpl;

import edu.ucsd.cse232b.Antlr4Xquery.XqueryParser;
import edu.ucsd.cse232b.Antlr4Xquery.XqueryBaseVisitor;
import edu.ucsd.cse232b.XqueryImpl.CustomizedXqueryVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

public class CustomizedXqueryVisitor extends XqueryBaseVisitor<LinkedList> {
    private static final Logger logger = Logger.getLogger(CustomizedXqueryVisitor.class.getName());
    LinkedList<Node> frontierNodes = new LinkedList<>(); // the nodes under the current path
    HashMap<String,Object> context = new HashMap<>();

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

    public LinkedList<Node> visitDoubleSlash(XqueryParser.RpContext ctx) {
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

    public boolean visitSomeVarXq(XqueryParser.ParSatisfyCondContext ctx, int curIndex) {
        LinkedList<Node> res;
        // Since we only use the context at the lowest level, we don't need to remove var from the context during DFS.
        if (ctx.var().size() == curIndex) { // no more variables
            res = visit(ctx.cond());
            return res.size() > 0; // if the condition is matched or not
        }
        String var = ctx.var(curIndex).getText() ;
        XqueryParser.XqContext xq = ctx.xq(curIndex);
        res = visit(xq);
        for (Node node : res) {
            this.context.put(var, node);
            if (visitSomeVarXq(ctx, curIndex + 1)){ // DFS
                return true;
            }
        }
        return false;
    }

    @Override
    public LinkedList<Node> visitSingleAP(XqueryParser.SingleAPContext ctx) { // single slash
        logger.info("visit single AP node");
        this.frontierNodes = visit(ctx.doc()); // Only the document root node is in the list.
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitDoubleAP(XqueryParser.DoubleAPContext ctx) { // double slash (need to traverse the whole tree)
        logger.info("visit double AP node");
        this.frontierNodes = visit(ctx.doc()); // Only the document root node is in the list.
        return visitDoubleSlash(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitDoc(XqueryParser.DocContext ctx) {
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
    public LinkedList<Node> visitBraceRP(XqueryParser.BraceRPContext ctx) {
        return visit(ctx.rp());
    }

    @Override
    public LinkedList<Node> visitDoubleSlashRP(XqueryParser.DoubleSlashRPContext ctx) {
        logger.info("visit double RP node");
        this.frontierNodes = visit(ctx.rp(0)); // nodes that satisfy the first RP
        return visitDoubleSlash(ctx.rp(1));
    }

    @Override
    public LinkedList<Node> visitTextRP(XqueryParser.TextRPContext ctx) {
        logger.info("visit text RP node");
        NodeList children;
        Node child;
        LinkedList<Node> res = new LinkedList<>();

        for (Node node : this.frontierNodes) {
            children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                child = children.item(i);
                if (child.getNodeType() == Node.TEXT_NODE) {
                    res.add(child);
                }
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitAttRP(XqueryParser.AttRPContext ctx) {
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
    public LinkedList<Node> visitParentRP(XqueryParser.ParentRPContext ctx) {
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
    public LinkedList<Node> visitSelfRP(XqueryParser.SelfRPContext ctx) {
        logger.info("visit self RP node");
        return this.frontierNodes;
    }

    @Override
    public LinkedList<Node> visitFilterRP(XqueryParser.FilterRPContext ctx) {
        logger.info("visit filter RP node");
        this.frontierNodes = visit(ctx.rp());
        return visit(ctx.f());
    }

    @Override
    public LinkedList<Node> visitCommaRP(XqueryParser.CommaRPContext ctx) {
        logger.info("visit comma RP node");
        LinkedList<Node> res = visit(ctx.rp(0));
        LinkedList<Node> res2 = visit(ctx.rp(1));

        for (Node node : res2) {
            // res and res2.xml can have overlapping nodes
            if (!res.contains(node)) {
                res.add(node);
            }
        }
        return res;
    }

    @Override
    public LinkedList<Node> visitChildrenRP(XqueryParser.ChildrenRPContext ctx) {
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
    public LinkedList<Node> visitTagRP(XqueryParser.TagRPContext ctx) {
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
    public LinkedList<Node> visitSingleSlashRP(XqueryParser.SingleSlashRPContext ctx) {
        logger.info("visit single RP node");
        this.frontierNodes = visit(ctx.rp(0));
        return visit(ctx.rp(1));
    }

    @Override
    public LinkedList<Node> visitEqFilter(XqueryParser.EqFilterContext ctx) {
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
    public LinkedList<Node> visitNotFilter(XqueryParser.NotFilterContext ctx) {
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
    public LinkedList<Node> visitAndFilter(XqueryParser.AndFilterContext ctx) {
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
    public LinkedList<Node> visitIsFilter(XqueryParser.IsFilterContext ctx) {
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
    public LinkedList<Node> visitRpFilter(XqueryParser.RpFilterContext ctx) {
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
    public LinkedList<Node> visitStringFilter(XqueryParser.StringFilterContext ctx) {
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
                if (ln.getNodeValue().equals(str) && !res.contains(node)) {
                    logger.info("add a node");
                    res.add(node);
                }
            }
        }

        this.frontierNodes = res;
        return res;
    }

    @Override
    public LinkedList<Node> visitBraceFilter(XqueryParser.BraceFilterContext ctx) {
        logger.info("visit BraceFilter");
        return visit(ctx.f());
    }

    @Override
    public LinkedList<Node> visitOrFilter(XqueryParser.OrFilterContext ctx) {
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
    public LinkedList<Node> visitTagName(XqueryParser.TagNameContext ctx) {
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
    public LinkedList<Node> visitAttName(XqueryParser.AttNameContext ctx) {
        logger.info("visit AttName");
        LinkedList<Node> res = new LinkedList<>(); // result nodes
        for (Node node: this.frontierNodes){
            if (node.getNodeName().equals(ctx.getText())) {
                res.add(node);
            }
        }
        return res;
    }

    @Override public LinkedList<Node> visitFLWR(XqueryParser.FLWRContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitSingleSlashXQ(XqueryParser.SingleSlashXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitTagXQ(XqueryParser.TagXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitApXQ(XqueryParser.ApXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitLetXQ(XqueryParser.LetXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitStringXQ(XqueryParser.StringXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitCommaXQ(XqueryParser.CommaXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitVarXQ(XqueryParser.VarXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitBraceXQ(XqueryParser.BraceXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitDoubleSlashXQ(XqueryParser.DoubleSlashXQContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitForClause(XqueryParser.ForClauseContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitLetClause(XqueryParser.LetClauseContext ctx) {
        int varNum = ctx.var().size();
        for (int i = 0; i < varNum; i++) {
            this.context.put(ctx.var(i).getText(), ctx.xq(i));
        }
        return visit(ctx.xq(varNum));
    }

    @Override public LinkedList<Node> visitWhereClause(XqueryParser.WhereClauseContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitReturnClause(XqueryParser.ReturnClauseContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitParSatisfyCond(XqueryParser.ParSatisfyCondContext ctx) {
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> tmp = this.frontierNodes;
        for (Node node : tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);

            this.frontierNodes = evalNode;
            if (visitSomeVarXq(ctx, 0)) {
                res.add(node);
            }
        }
        return res;
    }

    @Override public LinkedList<Node> visitOrCond(XqueryParser.OrCondContext ctx) {
        logger.info("visit OrCond");
        LinkedList<Node> res;
        LinkedList<Node> tmp = this.frontierNodes;

        HashSet<Node> ls = new HashSet<>(visit(ctx.cond(0)));

        this.frontierNodes = tmp;
        HashSet<Node> rs = new HashSet<>(visit(ctx.cond(1)));

        ls.addAll(rs);
        res = new LinkedList<>(ls);
        return res;
    }

    @Override public LinkedList<Node> visitBraceCond(XqueryParser.BraceCondContext ctx) {
        logger.info("visit BraceCond");
        return visit(ctx.cond());
    }

    @Override public LinkedList<Node> visitEmptyCond(XqueryParser.EmptyCondContext ctx) {
        logger.info("visit EmptyCond");
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> tmp = this.frontierNodes;
        LinkedList<Node> condRes;

        for (Node node : tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);

            this.frontierNodes = evalNode;
            condRes = visit(ctx.xq());
            if (condRes.size() == 0) {
                res.add(node);
            }
        }
        return res;
    }

    @Override public LinkedList<Node> visitAndCond(XqueryParser.AndCondContext ctx) {
        logger.info("visit AndCond");
        LinkedList<Node> res;
        LinkedList<Node> tmp = this.frontierNodes;

        HashSet<Node> ls = new HashSet<>(visit(ctx.cond(0)));

        this.frontierNodes = tmp;
        HashSet<Node> rs = new HashSet<>(visit(ctx.cond(1)));

        ls.retainAll(rs);
        res = new LinkedList<>(ls);
        return res;
    }

    @Override public LinkedList<Node> visitIsCond(XqueryParser.IsCondContext ctx) {
        logger.info("visit IsCond");
        LinkedList<Node> tmp = this.frontierNodes;
        LinkedList<Node> res = new LinkedList<>();
        boolean shouldBreak = false;

        for (Node node: tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);

            this.frontierNodes = evalNode;
            LinkedList<Node> l = visit(ctx.xq(0));

            this.frontierNodes = evalNode;
            LinkedList<Node> r = visit(ctx.xq(1));

            for (Node ln: l) {
                if (shouldBreak) {
                    break;
                }
                for (Node rn: r) {
                    if (ln.isSameNode(rn)) {
                        res.add(node);
                        shouldBreak = true;
                        break;
                    }
                }
            }
        }
        // add this line back if necessary
//        this.frontierNodes = res;
        return res;
    }

    @Override public LinkedList<Node> visitEqCond(XqueryParser.EqCondContext ctx) {
        logger.info("visit EqCond");
        LinkedList<Node> tmp = this.frontierNodes;
        LinkedList<Node> res = new LinkedList<>();
        boolean shouldBreak = false;

        for (Node node: tmp) {
            LinkedList<Node> evalNode = new LinkedList<>();
            evalNode.add(node);

            this.frontierNodes = evalNode;
            LinkedList<Node> l = visit(ctx.xq(0));

            this.frontierNodes = evalNode;
            LinkedList<Node> r = visit(ctx.xq(1));

            for (Node ln: l) {
                if (shouldBreak) {
                    break;
                }
                for (Node rn: r) {
                    if (ln.isEqualNode(rn)) {
                        res.add(node);
                        shouldBreak = true;
                        break;
                    }
                }
            }
        }
        // add this line back if necessary
//        this.frontierNodes = res;
        return res;
    }

    @Override public LinkedList<Node> visitNotCond(XqueryParser.NotCondContext ctx) {
        logger.info("visit NotCond");
        LinkedList<Node> res;

        HashSet<Node> current = new HashSet<>(this.frontierNodes);
        HashSet<Node> diff = new HashSet<>(visit(ctx.cond()));

        current.removeAll(diff);
        res = new LinkedList<>(current);

        // add this line back if something goes wrong
//        this.frontierNodes = res;
        return res;
    }

    @Override public LinkedList<Node> visitVar(XqueryParser.VarContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitOpenTag(XqueryParser.OpenTagContext ctx) { return visitChildren(ctx); }

    @Override public LinkedList<Node> visitCloseTag(XqueryParser.CloseTagContext ctx) { return visitChildren(ctx); }
}
