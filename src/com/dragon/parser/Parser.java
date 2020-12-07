package com.dragon.parser;

import com.dragon.inter.*;
import com.dragon.lexer.*;
import com.dragon.symbols.Array;
import com.dragon.symbols.Env;
import com.dragon.symbols.Type;

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

    public void program() throws IOException{
        Stmt stmt = block();
        int begin = stmt.newLabel();
        int after = stmt.newLabel();
        stmt.emitLabel(begin);
        stmt.gen(begin, after);
        stmt.emitLabel(after);
    }

    Stmt block() throws IOException {
        math('{');
        Env saveEnv = top;
        top = new Env(top);
        declarations();
        Stmt stmtS = stmtS();
        math('}');
        top = saveEnv;
        return stmtS;
    }

    void declarations() throws IOException {
        while (look.tag == Tag.BASIC) {
            Type type = type();
            Token token = look;
            math(Tag.ID);
            math(';');
            Id id = new Id((Word) token, type , used);
            top.put(token, id);
            used = used + type.width;
        }
    }

    Type type() throws IOException {
        Type type = (Type) look;
        math(Tag.BASIC);
        if (look.tag != '[') {
            return type;
        } else {
            return dims(type);
        }
    }

    Type dims(Type type) throws IOException {
        math('[');
        Token token = look;
        math(Tag.NUM);
        math(']');
        if (look.tag == '[') {
            type = dims(type);
        }
        return new Array(((Num)token).value, type);
    }

    Stmt stmtS() throws IOException {
        if (look.tag == '}'){
            return Stmt.Null;
        } else {
            return new Seq(stmt(), stmtS());
        }
    }

    Stmt stmt() throws IOException {
        Expr expr;
        Stmt stmt, stmt1, stmt2;
        Stmt savedStmt;
        switch (look.tag) {
            case ';': move(); return Stmt.Null;
            case Tag.IF:
                math(Tag.IF); math('('); expr = bool(); math(')');
                stmt1 = stmt();
                if (look.tag != Tag.ELSE) {
                    return new If(expr, stmt1);
                }
                math(Tag.ELSE);
                stmt2 = stmt();
                return new Else(expr, stmt1, stmt2);
            case Tag.WHILE:
                While whileNode = new While();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = whileNode;
                math(Tag.WHILE); math('('); expr = bool(); math(')');
                stmt1 = stmt();
                whileNode.init(expr, stmt1);
                Stmt.Enclosing = savedStmt;
                return whileNode;
            case Tag.DO:
                Do DoNode = new Do();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = DoNode;
                math(Tag.DO);
                stmt1 = stmt();
                math(Tag.WHILE);math('('); expr = bool(); math(')'); math(';');
                DoNode.init(stmt1, expr);
                Stmt.Enclosing = savedStmt;
                return DoNode;
            case Tag.BREAK:
                math(Tag.BREAK); math(';');
                return new Break();
            case '{':
                return block();
            default:
                return assign();
        }
    }

    Stmt assign() throws IOException {
        Stmt stmt;
        Token token = look;
        math(Tag.ID);
        Id id = top.get(token);
        if (id == null) {
            error(token.toString() + " undeclared");
        }
        if (look.tag == '=') {
            move();
            stmt = new Set(id, bool());
        } else {
            Access x = offset(id);
            math('=');
            stmt = new SetElem(x, bool());
        }
        math(';');
        return stmt;
    }

    Expr bool() throws IOException {
        Expr x = join();
        while (look.tag == Tag.OR) {
            Token token = look;
            move();
            x = new Or(token, x, join());
        }
        return x;
    }

    Expr join() throws IOException {
        Expr x = equality();
        while (look.tag == Tag.AND) {
            Token token = look;
            move();
            x = new And(token, x, equality());
        }
        return x;
    }

    Expr equality() throws IOException {
        Expr x = rel();
        while (look.tag == Tag.EQ || look.tag == Tag.NE) {
            Token token = look;
            move();
            x = new Rel(token, x, rel());
        }
        return x;
    }

    Expr rel() throws IOException {
        Expr x = expr();
        switch (look.tag) {
            case '<':
            case Tag.LE:
            case Tag.GE:
            case '>':
                Token token = look;
                move();
                return new Rel(token, x, expr());
            default:
                return x;
        }
    }

    Expr expr() throws IOException {
        Expr x = unary();
        while (look.tag == '*' || look.tag == '/') {
            Token token = look;
            move();
            x = new Arith(token, x, unary());
        }
        return x;
    }

    Expr unary() throws IOException {
        if (look.tag == '-') {
            move();
            return new Unary(Word.minus, unary());
        } else if (look.tag == '!') {
            Token token = look;
            move();
            return new Not(token, unary());
        } else {
            return factor();
        }
    }

    Expr factor() throws IOException{
        Expr x = null;
        switch (look.tag) {
            case '(':
                move(); x = bool(); math(')');
                return x;
            case Tag.NUM:
                x = new Constant(look, Type.Int);
                move();
                return x;
            case Tag.REAL:
                x = new Constant(look, Type.Float);
                move();
                return x;
            case Tag.TRUE:
                x = Constant.True;
                move();
                return x;
            case Tag.FALSE:
                x = Constant.False;
                move();
                return x;
            default:
                error("syntax error");
                return x;
            case Tag.ID:
                String s = look.toString();
                Id id = top.get(look);
                if (id == null) {
                    error(look.toString() + " undeclared");
                }
                move();
                if (look.tag != '[') {
                    return id;
                } else {
                    return offset(id);
                }
        }
    }

    Access offset(Id a) throws IOException {
        Expr i;
        Expr w;
        Expr t1, t2;
        Expr loc;
        Type type = a.type;
        math('['); i = bool(); math(']');
        type = ((Array)type).of;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'), i, w);
        loc = t1;
        while (look.tag == '[') {
            math('['); i = bool(); math(']');
            type = ((Array)type).of;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'), i, w);
            t2 = new Arith(new Token('+'), loc, t1);
            loc = t2;
        }
        return new Access(a, loc, type);
    }
}

