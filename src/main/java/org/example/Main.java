package org.example;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.example.json.JSONLexer;
import org.example.json.JSONParser;
import org.example.json.Listener;
import org.example.json.TreeUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String inputFile = "/Users/akshaydohroo/Documents/antlrPPT/src/main/java/org/example/json/input.json";
        CharStream input = inputFile != null ? CharStreams.fromFileName(inputFile) : CharStreams.fromStream(System.in);

        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        JSONParser.JsonContext tree = parser.json();

        Listener listener = new Listener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        List<String> ruleNamesList = Arrays.asList(parser.getRuleNames());
        String prettyTree = TreeUtils.toPrettyTree(tree, ruleNamesList);
        System.out.println(prettyTree);
        System.out.println(listener.getResult());
    }
}