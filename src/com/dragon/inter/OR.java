package com.dragon.inter;

import com.dragon.lexer.Token;


public class OR extends Logical{
    public OR(Token token, Expr expr1, Expr expr2) {
        super(token, expr1, expr2);
    }

    /**
     * Method jumping generates jumping code for a boolean expression B = B1 || B2.
     * For the moment, suppose that neither the true t nor the false exit f of B is
     * the special label 0.
     * @param t label t
     * @param f label f
     */
    @Override
    public void jumping(int t, int f) {
        int label = t != 0 ? t : newLabel();
        expr1.jumping(label, 0);
        expr2.jumping(t, f);
        if (t == 0) {
            emitLabel(label);
        }
    }
}
