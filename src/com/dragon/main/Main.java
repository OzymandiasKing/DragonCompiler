package com.dragon.main;

import com.dragon.lexer.Lexer;
import com.dragon.parser.Parser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer();
        Parser parser = new Parser(lexer);
        parser.program();
        System.out.write('\n');
    }
}
