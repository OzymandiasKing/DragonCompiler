package com.dragon.inter;

import com.dragon.symbols.Type;

public class Set extends Stmt{
    public Id id;
    public Expr expr;
    public Set(Id id, Expr expr) {
        this.id = id;
        this.expr = expr;
        if (check(this.id.type, this.expr.type) == null) {
            error("type error!!!");
        }
    }

    public Type check(Type type1, Type type2) {
        if (Type.numeric(type1) && Type.numeric(type2)) {
            return type2;
        } else if (type1 == Type.Bool && type2 == Type.Bool) {
            return type2;
        } else {
            return null;
        }
    }

    @Override
    public void gen(int b, int a) {
        emit(id.toString() + " = " + expr.gen().toString());
    }
}
