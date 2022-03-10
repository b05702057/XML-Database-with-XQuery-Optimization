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
import java.util.logging.LogManager;

public class XqueryEngine {
    public static void main(String[] args) {
        LogManager.getLogManager().reset(); // comment this for debugging
//        String XpathQuery = "testcase/XjoinQuery/test18"; //args[0];
//        String resFilename = "testcase/XqueryResult/res-join18.xml"; //"result.xml";
        String XpathQuery = "testcase/XjoinResult/test18"; //args[0];
        String resFilename = "testcase/XqueryResult/res-join18R.xml"; //"result.xml";
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
            output = visitor.doc;

            write2File(output, res, resFilename);
        } catch(IOException e) {
            e.printStackTrace(); // print the stack frame status
        }
    }

    public static void write2File(Document doc, LinkedList<Node> res, String outputFilename) {
        // https://www.titanwolf.org/Network/q/4e45060a-2293-47f3-aa12-3d67d3d96f6f/y
        Node root = doc.createElement("result");
        for (Node node: res) {
            // Do we have to create a node here?
            Node cNode = doc.importNode(node, true); // return the deep copied node (including its children)
            root.appendChild(cNode);
        }
        // Do we have to create a node here?
        Node cRoot = doc.importNode(root, true);
        doc.appendChild(cRoot);

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            t.transform(new DOMSource(doc), new StreamResult(new File(outputFilename)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}