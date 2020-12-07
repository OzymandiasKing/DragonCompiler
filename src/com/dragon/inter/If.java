package inter;

import symbols.Type;

public class If extends Stmt{
    Expr expr;
    Stmt stmt;
    public If(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (this.expr.type != Type.Bool) {
            this.expr.error("boolean required in if!!!");
        }
    }

    @Override
    public void gen(int b, int a) {
        int label = newLabel();
        expr.jumping(0, a);
        emitLabel(label);
        stmt.gen(label, a);
    }
}
