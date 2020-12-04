package com.dragon.inter;

import com.dragon.symbols.Type;

public class Do extends Stmt{
    Expr expr;
    Stmt stmt;
    public Do(Expr expr, Stmt stmt){
        this.expr = expr;
        this.stmt = stmt;
        if (this.expr.type != Type.Bool) {
            this.expr.error("boolean is required in do!!!");
        }
    }

    @Override
    public void gen(int b, int a) {
        after = a;
        int label = newLabel();
        stmt.gen(label, b);
        emitLabel(label);
        expr.jumping(b, 0);
    }
}
