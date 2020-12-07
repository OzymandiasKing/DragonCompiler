package com.dragon.inter;

import com.dragon.symbols.Array;
import com.dragon.symbols.Type;

public class SetElem extends Stmt{
    public Id array;
    public Expr index, expr;
    public SetElem(Access x, Expr y) {
        array = x.array;
        index = x.index;
        expr = y;
        if (check(x.type, y.type) == null) {
            error("type error!!!");
        }
    }

    public Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) {
            return null;
        } else if (type1 == type2) {
            return type2;
        } else if (Type.numeric(type1) && Type.numeric(type2)) {
            return type2;
        } else {
            return null;
        }
    }

    @Override
    public void gen(int b, int a) {
        String s1 = index.reduce().toString();
        String s2 = expr.reduce().toString();
        emit(array.toString() + "[ " + s1 + " ]" + s2);
    }
}
