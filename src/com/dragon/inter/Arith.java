package inter;

import lexer.Token;
import symbols.Type;

public class Arith extends Op{
	public Expr expr1, expr2;
	public Arith(Token token, Expr expr1, Expr expr2) {
		super(token, null);
		this.expr1 = expr1;
		this.expr2 = expr2;
		type = Type.max(expr1.type, expr2.type);
		if (type == null) {
			error("type error!!!");
		}
	}
	
	public Expr gen() {
		return new Arith(opToken, expr1.reduce(), expr2.reduce());
	}
	
	@Override
	public String toString() {
		return expr1.toString() + " " + opToken.toString() + " " + expr2.toString();
	}

}
