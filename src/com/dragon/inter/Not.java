package com.dragon.inter;

import com.dragon.lexer.Token;

public class Not extends Logical{
    public Not(Token token, Expr expr2) {
        super(token, expr2, expr2);
    }

    @Override
    public void jumping(int t, int f) {
        expr2.jumping(t, f);
    }

    @Override
    public String toString() {
        return opToken.toString() + " " + expr2.toString();
    }
}
