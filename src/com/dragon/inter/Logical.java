package inter;

import lexer.Token;
import symbols.Type;

/**
 * Class Logical provides some common functionality for classes OR, AND, and NOT.
 */
public class Logical extends Expr{
    //Fields expr1 and expr2 correspond to the operands of a logical operator.
    public Expr expr1, expr2;

    /**
     * Constructor Logical builds a syntax node with operator token and operand a and b.
     * In doing so it uses function check to ensure that a and b are both boolean.
     * @param token operator token
     * @param expr1 operand a
     * @param expr2 operand b
     */
    Logical(Token token, Expr expr1, Expr expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        type = check(this.expr1.type, this.expr2.type);
        if (type == null) {
            error("type error!!!");
        }
    }

    /**
     * Function check(type1, type2) can check whether type1 and type2 is boolean.
     * @param type1 operand a
     * @param type2 operand b
     * @return If type1 and type2 are both boolean return true, else return false.
     */
    public Type check(Type type1, Type type2) {
        if (type1 == Type.Bool && type2 == Type.Bool) {
            return Type.Bool;
        } else {
            return null;
        }
    }

    public Expr gen() {
        int f = newLabel();
        int a = newLabel();
        Temp temp = new Temp(type);
        this.jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitLabel(f);
        emit(temp.toString() + " = false");
        emitLabel(a);
        return temp;
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + opToken.toString() + " "  + expr2.toString();
    }
}
