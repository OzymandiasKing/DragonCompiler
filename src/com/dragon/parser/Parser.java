package com.dragon.parser;

import com.dragon.lexer.Lexer;
import com.dragon.lexer.Token;
import com.dragon.symbols.Env;

import java.io.IOException;

public class Parser {
    private Lexer lexer;
    private Token look;
    Env top = null;
    int used = 0;
    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        move();
    }

    void move() throws IOException{
        look = lexer.scan();
    }

    void error(String s) {
        throw new Error("near line " + lexer.line + ": " + s);
    }

    void math(int t) throws IOException {
        if (look.tag == t) {
            move();
        } else {
            error("syntax error");
        }
    }
}
