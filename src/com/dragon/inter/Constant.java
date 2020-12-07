package inter;

import lexer.Num;
import lexer.Token;
import lexer.Word;
import symbols.Type;

public class Constant extends Expr{
    /**
     * Constructs a leaf in the syntax tree with label token and type type
     */
    public Constant(Token token, Type type) {
        super(token, type);
    }

    /**
     * This overload constructor Constant create a constant object from Integer
     */
    public Constant(int i) {
        super(new Num(i), Type.Int);
    }

    public static final Constant
            True = new Constant(Word.True, Type.Bool),
            False = new Constant(Word.False, Type.Bool);

    /**
     * If this constant is the static object True and t is not the special label 0, then a jump to t is generated.
     * Otherwise, if this is the object False and f is nonzero, then a jump to f is generated.
     * @param t labels t
     * @param f labels f
     */
    public void jumping(int t, int f) {
        if (this == True && t != 0) {
            emit("goto L " + t);
        } else if (this == False && f != 0) {
            emit("goto L " + f);
        }
    }
}
