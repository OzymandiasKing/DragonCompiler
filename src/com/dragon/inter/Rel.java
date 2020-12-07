package inter;

import lexer.Token;
import symbols.Array;
import symbols.Type;

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

    /**
     * Method jumping(t, f) begins from generating code for subexpressions
     * expr1 and expr2(in Rel.java lines11-12), it then calls method
     * emitJumps(test, t, f)(in Expr.java line26). If neither t nor f is
     * the special label 0, then emitJumps execute the following
     *          emit("if " + test + " goto L" + t);
     * 			emit("goto L" + f);
     * At most one instruction is generated if either t or f is the special label 0:
     *          emit("if " + test + "goto L" + t);
     *          emit("iffalse " + test  + "goto L" + f);
     *          else;//nothing both t and f fall through
     * @param t labels t
     * @param f labels f
     */
    @Override
    public void jumping(int t, int f) {
        Expr a = expr1.reduce();
        Expr b = expr2.reduce();

        String test = a.toString() + " " + opToken.toString() + " " + b.toString();
        emitJumps(test, t, f);
    }
}
