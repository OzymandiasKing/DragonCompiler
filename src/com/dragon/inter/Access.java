package inter;

import lexer.Tag;
import lexer.Word;
import symbols.Type;

/**
 * The source language allows boolean values to be assigned to identifiers
 * and array elements, so a boolean expression can be an array access.
 */
public class Access extends Op{
    public Id array;
    public Expr index;

    /**
     * Constructor is called with a flattened array "array",
     * an index "index", and the type "type" of an element
     * in the flattened array
     * @param array a flattened array
     * @param index an index
     * @param type type of an element in the flattened array
     */
    public Access(Id array, Expr index, Type type) {   //type is the element type after
        super(new Word("[]", Tag.INDEX), type);      //flattening the array
        this.array = array;
        this.index = index;
    }

    /**
     * Generate "normal" code
     */
    @Override
    public Expr gen() {
        return new Access(array, index.reduce(), type);
    }

    /**
     * Jumping access to a temporary.
     * @param t labels t
     * @param f labels f
     */
    @Override
    public void jumping(int t, int f) {
        emitJumps(reduce().toString(), t, f);
    }

    @Override
    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }
}
