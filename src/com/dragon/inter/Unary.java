package inter;

import lexer.Token;
import symbols.Type;

public class Unary extends Op{
	public Expr expr;
	public Unary(Token token, Expr expr) {
		super(token, null);
		this.expr = expr;
		type = Type.max(Type.Int, expr.type);
		if (type == null) {
			error("type error!!!");
		}
	}
	public Expr gen() {
		return new Unary(opToken, expr.reduce());
	}
	
	@Override
	public String toString() {
		return opToken.toString() + " " + expr.toString();
	}

}
