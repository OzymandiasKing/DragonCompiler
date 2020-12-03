package com.dragon.inter;

import com.dragon.lexer.Token;
import com.dragon.symbols.Array;
import com.dragon.symbols.Type;

/**
 * Class Rel implements operators <, <=, ==, !=, >, >=.
 */
public class Rel extends Logical{
    public Rel(Token token, Expr expr1, Expr expr2) {
        super(token, expr1, expr2);
    }

    /**
     * Function check(type1, type2) checks that the two operand have the same
     * type and they are not arrays
     * @param type1 operand a
     * @param type2 operand b
     * @return return whether the two operands have the same type
     */
    @Override
    public Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) {
            return null;
        } else if (type1 == type2) {
            return Type.Bool;
        } else {
            return null;
        }
    }

    @Override
    public void jumping(int t, int f) {
        Expr a = expr1.reduce();
        Expr b = expr2.reduce();

        String test = a.toString() + " " + opToken.toString() + " " + b.toString();
        emitJumps(test, t, f);
    }
}
