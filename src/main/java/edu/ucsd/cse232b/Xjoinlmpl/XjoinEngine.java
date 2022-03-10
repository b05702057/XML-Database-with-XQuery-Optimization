package edu.ucsd.cse232b.Xjoinlmpl;

import edu.ucsd.cse232b.Antlr4Xjoin.XjoinLexer;
import edu.ucsd.cse232b.Antlr4Xjoin.XjoinParser;
import edu.ucsd.cse232b.Antlr4Xquery.XqueryLexer;
import edu.ucsd.cse232b.Antlr4Xquery.XqueryParser;
import edu.ucsd.cse232b.XqueryImpl.CustomizedXqueryVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.logging.LogManager;

public class XjoinEngine {
    public static void main(String[] args) {
        String XjoinQuery = "testcase/XjoinQuery/test18";
        String XjoinOutput = "testcase/XjoinResult/test18";
        Path inputPath = Paths.get(XjoinQuery);
        Path outputPath = Paths.get(XjoinOutput);
        String output;
        try {
            // https://stackoverflow.com/questions/50541321/antlrinputstream-and-antlrfilestream-are-deprecated-what-are-the-alternatives
            CharStream cs = CharStreams.fromFileName(XjoinQuery);
            XjoinLexer lexer= new XjoinLexer(cs);
            XjoinParser parser = new XjoinParser((new CommonTokenStream((lexer))));
            ParseTree tree = parser.xq();

            CustomizedXjoinVisitor visitor = new CustomizedXjoinVisitor();
            output = visitor.visit(tree);
            if (output.equals("original")) {
                output = Files.readString(inputPath, StandardCharsets.UTF_8);
            }

            try {
                Files.writeString(outputPath, output, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                System.out.println("error");
            }
        } catch(IOException e) {
            e.printStackTrace(); // print the stack frame status
        }


        // get the result from the query
        LogManager.getLogManager().reset(); // comment this for debugging
//        String XpathQuery = "testcase/XjoinQuery/test17"; //args[0];
//        String resFilename = "testcase/XqueryResult/res-join17.xml"; //"result.xml";
        String XpathQuery = XjoinOutput; //args[0];
        String resFilename = "testcase/XqueryResult/res-join18R.xml"; //"result.xml";
        LinkedList<Node> res;
        Document output2;
        try {
            // https://stackoverflow.com/questions/50541321/antlrinputstream-and-antlrfilestream-are-deprecated-what-are-the-alternatives
            CharStream cs = CharStreams.fromFileName(XpathQuery);
            XqueryLexer lexer= new XqueryLexer(cs);
            XqueryParser parser = new XqueryParser((new CommonTokenStream((lexer))));
            ParseTree tree = parser.xq();

            Instant start = Instant.now();
            CustomizedXqueryVisitor visitor = new CustomizedXqueryVisitor();
            res = visitor.visit(tree);
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println(timeElapsed); // milliseconds

            output2 = visitor.doc;

            write2File(output2, res, resFilename);
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