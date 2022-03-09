package edu.ucsd.cse232b.Xjoinlmpl;

import edu.ucsd.cse232b.Antlr4Xjoin.XjoinLexer;
import edu.ucsd.cse232b.Antlr4Xjoin.XjoinParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XjoinEngine {
    public static void main(String[] args) {
        String XjoinQuery = "testcase/XjoinQuery/test15";
        Path inputPath = Paths.get(XjoinQuery);
        Path outputPath = Paths.get("testcase/XjoinResult/test15");
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
    }
}