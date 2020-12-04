package com.dragon.inter;

import com.dragon.symbols.Type;

public class While extends Stmt{
    Expr expr;
    Stmt stmt;
    public While(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (this.expr.type != Type.Bool) {
            this.expr.error("boolean required in while!!!");
        }
    }

    @Override
    public void gen(int b, int a) {
        int label = newLabel();
        after = a;
        expr.jumping(0, a);
        emitLabel(label);
        stmt.gen(label, b);
        emit("goto L" + b);
    }
}
