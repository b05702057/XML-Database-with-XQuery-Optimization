package edu.ucsd.cse232b.XqueryImpl;

import edu.ucsd.cse232b.Antlr4Xquery.XqueryLexer;
import edu.ucsd.cse232b.Antlr4Xquery.XqueryParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class XqueryEngine {
    public static void main(String[] args) {
        String XpathQuery = "testcase/XpathQuery/test7";
        String resFilename = "testcase/XpathResult/res7";
        LinkedList<Node> res;
        Document output;
        try {
            // https://stackoverflow.com/questions/50541321/antlrinputstream-and-antlrfilestream-are-deprecated-what-are-the-alternatives
            CharStream cs = CharStreams.fromFileName(XpathQuery);
            XqueryLexer lexer= new XqueryLexer(cs);
            XqueryParser parser = new XqueryParser((new CommonTokenStream((lexer))));
            ParseTree tree = parser.xq();

            CustomizedXqueryVisitor visitor = new CustomizedXqueryVisitor();
            res = visitor.visit(tree);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            output = db.newDocument();

            write2File(output, res, resFilename);
        } catch(ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void write2File(Document doc, LinkedList<Node> res, String outputFilename) {
        // https://www.titanwolf.org/Network/q/4e45060a-2293-47f3-aa12-3d67d3d96f6f/y
        Node root = doc.createElement("result");
        for (Node node: res) {
            Node cNode = doc.importNode(node, true);
            root.appendChild(cNode);
        }
        Node cRoot = doc.importNode(root, true);
        doc.appendChild(cRoot);

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setParameter(OutputKeys.INDENT, "yes");
            t.setParameter("{http://xml.apache.org/xslt}indent-amount", "4");
            t.transform(new DOMSource(doc), new StreamResult(new File(outputFilename)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}