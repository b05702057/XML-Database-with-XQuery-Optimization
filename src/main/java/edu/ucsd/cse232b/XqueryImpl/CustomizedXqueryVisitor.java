package edu.ucsd.cse232b.XqueryImpl;

import edu.ucsd.cse232b.Antlr4Xquery.XqueryParser;
import edu.ucsd.cse232b.Antlr4Xquery.XqueryBaseVisitor;
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
import java.util.*;
import java.util.logging.Logger;

public class CustomizedXqueryVisitor extends XqueryBaseVisitor<LinkedList> {
    private static final Logger logger = Logger.getLogger(CustomizedXqueryVisitor.class.getName());
    LinkedList<Node> frontierNodes = new LinkedList<>(); // the nodes under the current path
    public Document doc = null;
    private HashMap<String, LinkedList<Node>> contextMap = new HashMap<>();

    public CustomizedXqueryVisitor(){
        try {
            DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder docB = docBF.newDocumentBuilder();
            doc = docB.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

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
        logger.info("visitDoubleSlash1: " + contextMap);
        for (Node node : tmp) {
            // Edge Case => The query is doc("file")//A//A, and the DOM tree consists only A nodes.
            if (!this.frontierNodes.contains(node)) {
                this.frontierNodes.add(node);
            }
        }
        logger.info("visitDoubleSlash2: " + contextMap);
        return visit(ctx);
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
        NamedNodeMap attributes;
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

    @Override
    public LinkedList<Node> visitBraceXQ(XqueryParser.BraceXQContext ctx) {
        logger.info("visit BraceXQ");
        return visit(ctx.xq());
    }

    @Override
    public LinkedList<Node> visitCommaXQ(XqueryParser.CommaXQContext ctx) {
        logger.info("visit CommaXQ");
        LinkedList<Node> res = visit(ctx.xq(0));
        res.addAll(visit(ctx.xq(1)));

        return res;
    }

    @Override
    public LinkedList<Node> visitStringXQ(XqueryParser.StringXQContext ctx) {
        logger.info("visit StringXQ: " + ctx.STRING().getText());
        String str = ctx.STRING().getText();
        str = str.substring(1, str.length() - 1); // remove the left parenthesis and the right parenthesis

        LinkedList<Node> res = new LinkedList<Node>();
        res.add(this.doc.createTextNode(str));

        return res;
    }

    @Override
    public LinkedList<Node> visitApXQ(XqueryParser.ApXQContext ctx) {
        logger.info("visit ApXQ");
        return visit(ctx.ap());
    }

    @Override
    public LinkedList<Node> visitSingleSlashXQ(XqueryParser.SingleSlashXQContext ctx) {
        logger.info("visit singleSlashRP XQ");
        LinkedList<Node> res = new LinkedList<>();

        // Why do we copy the nodes here?
        this.frontierNodes = copyNodes(visit(ctx.xq()));
        res.addAll(visit(ctx.rp()));

        return res;
    }


    @Override public LinkedList<Node> visitLetClause(XqueryParser.LetClauseContext ctx) {
        LinkedList<Node> empty = new LinkedList<>();
        int varNum = ctx.var().size();
        for (int i = 0; i < varNum; i++) {
            this.contextMap.put(ctx.var(i).getText(), visit(ctx.xq(i)));
        }
        return empty;
    }

    @Override
    public LinkedList<Node> visitDoubleSlashXQ(XqueryParser.DoubleSlashXQContext ctx) {
        logger.info("visit doubleSlashRp XQ");

        // Why do we copy the nodes here?
        this.frontierNodes = copyNodes(visit(ctx.xq()));
        return visitDoubleSlash(ctx.rp());
    }

    private LinkedList<Node> copyNodes(LinkedList<Node> from) {
        LinkedList<Node> to = new LinkedList<>();
        for (Node node: from) to.add(node);

        return to;
    }

    @Override
    public LinkedList<Node> visitTagXQ(XqueryParser.TagXQContext ctx) {
        logger.info("visit TagXQ");
        String tag = ctx.openTag().tagName().getText();
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> nodeList = visit(ctx.xq());

        logger.info("tagXQ " + tag + ": " + nodeList);
        Node node = makeElem(tag, nodeList);
        res.add(node);

        return res;
    }

    public LinkedList<Node> visitSomeVarXq(XqueryParser.ParSatisfyCondContext ctx, int curIndex, HashMap<String, LinkedList<Node>> curMap) {
        LinkedList<Node> res;
        LinkedList<Node> finalRes;
        LinkedList<Node> empty = new LinkedList<>();

        if (ctx.var().size() == curIndex) { // no more variables
            return visit(ctx.cond());
        }
        String var = ctx.var(curIndex).getText() ;
        XqueryParser.XqContext xq = ctx.xq(curIndex);
        res = visit(xq);
        for (Node node : res) {
            HashMap<String, LinkedList<Node>> next = new HashMap<>(curMap);
            LinkedList<Node> curNodeList = new LinkedList<>();
            curNodeList.add(node);
            next.put(var, curNodeList);

            // We don't really use this map in the process, but we still want to keep it updated.
            contextMap = next;
            finalRes = visitSomeVarXq(ctx, curIndex + 1, next);
            contextMap = curMap;
            if (finalRes.size() > 0) return finalRes;
        }
        return empty;
    }

    @Override public LinkedList<Node> visitParSatisfyCond(XqueryParser.ParSatisfyCondContext ctx) {
        return visitSomeVarXq(ctx, 0, contextMap);
    }

    @Override public LinkedList<Node> visitOrCond(XqueryParser.OrCondContext ctx) {
        logger.info("visit OrCond");
        LinkedList<Node> res1;
        LinkedList<Node> res2;

        res1 = visit(ctx.cond(0));
        res2 = visit(ctx.cond(1));

        if (res1.size() > 0) return res1;
        if (res2.size() > 0) return res2;
        return res1; // return empty
    }

    @Override public LinkedList<Node> visitBraceCond(XqueryParser.BraceCondContext ctx) {
        logger.info("visit BraceCond");
        return visit(ctx.cond());
    }

    @Override public LinkedList<Node> visitEmptyCond(XqueryParser.EmptyCondContext ctx) {
        logger.info("visit EmptyCond");
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> empty = new LinkedList<>();
        Node oneNode = doc.createTextNode("random");
        LinkedList<Node> oneNodeList = new LinkedList<>();
        oneNodeList.add(oneNode);

        res = visit(ctx.xq());
        if (res.size() == 0) return oneNodeList;
        return  empty;
    }

    @Override public LinkedList<Node> visitAndCond(XqueryParser.AndCondContext ctx) {
        logger.info("visit AndCond");
        LinkedList<Node> res1;
        LinkedList<Node> res2;

        LinkedList<Node> empty = new LinkedList<>();
        Node oneNode = doc.createTextNode("random");
        LinkedList<Node> oneNodeList = new LinkedList<>();
        oneNodeList.add(oneNode);

        res1 = visit(ctx.cond(0));
        res2 = visit(ctx.cond(1));

        if (res1.size() > 0 && res2.size() > 0) return oneNodeList;
        return empty;
    }

    @Override public LinkedList<Node> visitIsCond(XqueryParser.IsCondContext ctx) {
        logger.info("visit IsCond");
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> l = visit(ctx.xq(0));
        LinkedList<Node> r = visit(ctx.xq(1));

        for (Node ln: l) {
            for (Node rn: r) {
                if (ln.isSameNode(rn)) {
                    res.add(ln); // only store one node
                    return res;
                }
            }
        }
        return res; // empty
    }

    @Override public LinkedList<Node> visitEqCond(XqueryParser.EqCondContext ctx) {
        logger.info("visit EqCond");
        LinkedList<Node> res = new LinkedList<>();
        LinkedList<Node> l = visit(ctx.xq(0));
        LinkedList<Node> r = visit(ctx.xq(1));

        for (Node ln: l) {
            for (Node rn: r) {
                if (ln.isEqualNode(rn)) {
                    res.add(ln);
                    return res;
                }
            }
        }
        return res; // empty
    }

    @Override public LinkedList<Node> visitNotCond(XqueryParser.NotCondContext ctx) {
        logger.info("visit NotCond");
        LinkedList<Node> res;
        LinkedList<Node> empty = new LinkedList<>();
        Node oneNode = doc.createTextNode("random");
        LinkedList<Node> oneNodeList = new LinkedList<>();
        oneNodeList.add(oneNode);

        res = visit(ctx.cond());
        if (res.size() > 0) return empty;
        return oneNodeList;
    }

    private Node makeElem(String tag, LinkedList<Node> nodeList) {
        Node newNode = doc.createElement(tag);
        for (Node node: nodeList) {
            if (node != null) newNode.appendChild(doc.importNode(node, true));
        }

        return newNode;
    }

    @Override
    public LinkedList<Node> visitVarXQ(XqueryParser.VarXQContext ctx) {
        logger.info("visit VarXQ: " + ctx.var().getText() );
        return this.contextMap.get(ctx.var().getText());
    }

    @Override
    public LinkedList<Node> visitFLWR(XqueryParser.FLWRContext ctx) {
        logger.info("visit FLWR");
        LinkedList<Node> res = new LinkedList<>();

        boolean milestone3 = true;
        if (milestone3) {
            HashMap<String, Integer> varGroup = new HashMap<>(); // store the group of each variable
            HashMap<String, Integer> varIndex = new HashMap<>(); // store the index of each variable
            Integer varNum = ctx.forClause().var().size(); // number of variables
            for (int i = 0; i < varNum; i++) {
                String var = ctx.forClause().var(i).getText();
                String path = ctx.forClause().xq(i).getText();

                // A variable starts with "$".
                if (path.substring(0, 1).equals("$")) { // variable
                    // A child is in the same group as its parent.
                    String parentVar = path.split("/")[0];
                    varGroup.put(var, varGroup.get(parentVar));
                }
                else { // document
                    varGroup.put(var, i);
                }
                varIndex.put(var, i);
            }

            HashMap<Integer, LinkedList<String>> groupVar = new HashMap<>(); // store the variables of a given group
            for (Map.Entry<String, Integer> set : varGroup.entrySet()) {
                String key = set.getKey(); // variable
                Integer val = set.getValue(); // group
                if (!groupVar.containsKey(val)) { // new group
                    LinkedList<String> newList = new LinkedList<>();
                    groupVar.put(set.getValue(), newList); // create a new group
                }
                groupVar.get(val).add(key); // add a variable to the group
            }

            HashMap<Integer, LinkedList<String[]>> groupConst = new HashMap<>(); // store the constant conditions of a given group
            HashMap<String, LinkedList<String[]>> groupsCond = new HashMap<>(); // store the conditions related to 2 groups
            String[] conditions = ctx.whereClause().getText().substring(5).split("and"); // the conditions of a where clause
            String[] eqCondition; // store the split condition
            for (int i = 0; i < conditions.length; i++) {
                String curCond = conditions[i];
                // We have to split with "eq$" or "eq\"" because "eq" can be a variable name.
                if (curCond.contains("eq$")) {
                    eqCondition = curCond.split("eq\\$");
                    eqCondition[1] = "$" + eqCondition[1];
                }
                else {
                    eqCondition = curCond.split("eq\"");
                    eqCondition[1] = "\"" + eqCondition[1];
                }

                // constant condition => push the condition down
                if (curCond.contains("\"")) {
                    boolean isFirstConstant = eqCondition[0].substring(0, 1).equals("\"");
                    boolean isSecondConstant = eqCondition[1].substring(0, 1).equals("\"");
                    // (1) both constants => push to every group
                    if (isFirstConstant && isSecondConstant) {
                        for (Integer key : groupVar.keySet()) {
                            if (!groupConst.containsKey(key)){
                                LinkedList<String[]> newList = new LinkedList<>();
                                groupConst.put(key, newList);
                            }
                            groupConst.get(key).add(eqCondition);
                        }
                    }
                    // one constant
                    else {
                        int group;
                        // (2) first constant
                        if (isFirstConstant) {
                            group = varGroup.get(eqCondition[1]);
                        }
                        // (3) second constant
                        else {
                            group = varGroup.get(eqCondition[0]);
                        }

                        if (!groupConst.containsKey(group)){
                            LinkedList<String[]> newList = new LinkedList<>();
                            groupConst.put(group, newList);
                        }
                        groupConst.get(group).add(eqCondition);
                    }
                }
                // no constants => variable conditions
                else {
                    int group1 = varGroup.get(eqCondition[0]);
                    int group2 = varGroup.get(eqCondition[1]);

                    if (group1 == group2) { // similar to constant conditions
                        if (!groupConst.containsKey(group1)) {
                            LinkedList<String[]> newList = new LinkedList<>();
                            groupConst.put(group1, newList);
                        }
                        groupConst.get(group1).add(eqCondition);
                    }
                    else {
                        String key = group1 + "," + group2 ;
                        if (!groupsCond.containsKey(key)){
                            LinkedList<String[]> val = new LinkedList<>();
                            groupsCond.put(key, val);
                        }
                        groupsCond.get(key).add(eqCondition);
                    }
                }
            }

            HashMap<Integer, String> finalForGroup = new HashMap<>();
            // prepare the for blocks
            for (Map.Entry<Integer, LinkedList<String>> set : groupVar.entrySet()) {
                int curGroup = set.getKey();
                String blockString = "for " + ctx.forClause().var(curGroup).getText() + " in " + ctx.forClause().xq(curGroup).getText() + ", ";
                for (String curVar : set.getValue()) {
                    int curIndex =  varIndex.get(curVar);
                    if (curGroup != curIndex) { // not the first index of the group
                        blockString += curVar + " in " + ctx.forClause().xq(curIndex).getText() + ", ";
                    }
                }
                finalForGroup.put(curGroup, blockString);
            }

            // add the pushed down where clause, ex. constant conditions
            for (Map.Entry<Integer, LinkedList<String[]>> set : groupConst.entrySet()) {
                int curGroup = set.getKey();
                String blockString = finalForGroup.get(curGroup);
                blockString += "     where ";
                for (String[] eqCond : set.getValue()) {
                    blockString += eqCond[0] + " eq " + eqCond[1] + " and ";
                }
                blockString = blockString.substring(0, blockString.length() - 5); // strip the and part
                finalForGroup.put(curGroup, blockString);
            }

            // add return clause
            for (Map.Entry<Integer, LinkedList<String>> set : groupVar.entrySet()) {
                int curGroup = set.getKey();
                String blockString = finalForGroup.get(curGroup);
                blockString += "     return <tuple> ";
                for (String curVar : set.getValue()){
                    blockString += String.format("<%s>{%s}</%s>, ", curVar.substring(1), curVar, curVar.substring(1));
                }
                blockString += "</tuple>, ";
                finalForGroup.put(curGroup, blockString);
            }

            // join the results
            HashMap<HashSet<Integer>, String> finalResult = new HashMap<>(); // store the joined groups
            for (Integer group : finalForGroup.keySet()) { // store original results initially
                HashSet<Integer> newList = new HashSet<>();
                newList.add(group);
                finalResult.put(newList, finalForGroup.get(group));
            }

            for (String key : groupsCond.keySet()) {
                String finalString = "join (";
                int group1 = Integer.valueOf(key.split(",")[0]);
                int group2 = Integer.valueOf(key.split(",")[1]);
                String groupString1 = "";
                String groupString2 = "";
                HashSet<Integer> newList = new HashSet<>();

                // get the result of the first group
                for (HashSet<Integer> listKey : finalResult.keySet()) {
                    if (listKey.contains(group1)) {
                        groupString1 = finalResult.get(listKey);
                        finalResult.remove(listKey);
                        newList.addAll(listKey);
                        break;
                    }
                }

                // get the result of the second group
                for (HashSet<Integer> listKey : finalResult.keySet()) {
                    if (listKey.contains(group2)) {
                        groupString2 = finalResult.get(listKey);
                        finalResult.remove(listKey);
                        newList.addAll(listKey);
                        break;
                    }
                }

                finalString += groupString1 + "\n" + groupString2 + "\n";
                String list1 = "[";
                String list2 = "[";
                for (String[] eqCond : groupsCond.get(key)) {
                    list1 += eqCond[0].substring(1) + ",";
                    list2 += eqCond[1].substring(1) + ",";
                }
                list1 = list1.substring(0, list1.length() - 1) + "], ";
                list2 = list2.substring(0, list2.length() - 1) + "]";
                finalString += list1 + list2;
                finalString += "),";
                finalResult.put(newList, finalString);
            }

            // join all unrelated groups
            List<HashSet<Integer>> keyList = new ArrayList<>();
            keyList.addAll(finalResult.keySet());
            for (int i = keyList.size() - 1; i > 0; i--) {
                HashSet<Integer> key1 = keyList.remove(i) ;
                HashSet<Integer> key2 = keyList.remove(i - 1);
                String groupString1 = finalResult.remove(key1);
                String groupString2 = finalResult.remove(key2);

                String finalString = "join (" + groupString1 + "\n" + groupString2 + "\n" + "[], []),";

                key1.addAll(key2);
                keyList.add(key1);
                finalResult.put(key1, finalString);
            }

            // get the final query
            String finalQuery = "for $tuple in ";
            String finalString = finalResult.values().toString();
            finalString = finalString.substring(1, finalString.length() - 2);
            finalQuery += finalString + "\n";

            String returnString = ctx.returnClause().getText();
            returnString = returnString.replace("$", "$tuple/");
            finalQuery += returnString;

//            System.out.println(varGroup);
//            System.out.println(groupVar);
//            System.out.println(varIndex);
//            System.out.println(finalForGroup);
//            System.out.println(groupConst);
//            System.out.println(groupsCond);
//            System.out.println(finalResult);
            System.out.println(finalQuery);
            return res;
        }

        HashMap<String, LinkedList<Node>> currContext = new HashMap<>(contextMap);
        Stack<HashMap<String, LinkedList<Node>>> ctxStack = new Stack<>();

        ctxStack.push(currContext);
        traverse(ctx, ctxStack, 0, ctx.forClause().var().size(), res);
        contextMap = ctxStack.pop();

        return res;
    }

    private void traverse(XqueryParser.FLWRContext ctx, Stack<HashMap<String, LinkedList<Node>>> ctxStack,
                                      int layer, int maxLayer, LinkedList<Node> res) {
        if (layer == maxLayer) {
            logger.info("final layer, output the result with let, where, and return: context= "+ contextMap);
            if (ctx.letClause() != null) visit(ctx.letClause());
            if (ctx.whereClause() != null)
                if (visit(ctx.whereClause()).size() == 0) {
                    logger.info("no return value");
                    return;
                }
            res.addAll(visit(ctx.returnClause()));
            return;
        }

        String key = ctx.forClause().var(layer).getText();
        System.out.println("before " + key + " is made with " + ctx.forClause().xq(layer).getText() + ": " + contextMap);
        LinkedList<Node> nodeList = visit(ctx.forClause().xq(layer));
        System.out.println("end of evaluation: " + nodeList + ". At this moment, contextMap=" + contextMap);

        for (Node node: nodeList) {
            HashMap<String, LinkedList<Node>> next = new HashMap<>(contextMap);
            LinkedList<Node> val = new LinkedList<>();
            val.add(node);
            next.put(key, val);

            ctxStack.push(next);
            contextMap = ctxStack.peek();
            traverse(ctx, ctxStack, layer + 1, maxLayer, res);
            ctxStack.pop();
            contextMap = ctxStack.peek();
        }
    }

    @Override
    public LinkedList<Node> visitWhereClause(XqueryParser.WhereClauseContext ctx) {
        logger.info("visit where clause");
        return visit(ctx.cond());
    }

    @Override
    public LinkedList<Node> visitReturnClause(XqueryParser.ReturnClauseContext ctx) {
        logger.info("visit return clause");
        return visit(ctx.xq());
    }
}


