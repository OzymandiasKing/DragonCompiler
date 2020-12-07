package inter;

/**
 * Deal with syntax-tree construction.
 */
public class Stmt extends Node{
    /**
     * This constructor does nothing, since the work is
     * done in the subclass.
     */
    public Stmt() {}
    public static Stmt Null = new Stmt();

    /**
     * Called with labels begin and after
     * @param b labels begin
     * @param a labels after
     */
    public void gen(int b, int a) {}
    //save labels after
    int after = 0;
    /**
     * Use for break stmts
     */
    public static Stmt Enclosing = Stmt.Null;
}
