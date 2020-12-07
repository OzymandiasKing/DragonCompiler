package inter;

import symbols.Type;

public class Do extends Stmt{
    Expr expr;
    Stmt stmt;
    public Do() {
        expr = null;
        stmt = null;
    }
    public void init(Stmt stmt, Expr expr){
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
