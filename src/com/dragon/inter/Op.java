package inter;

import lexer.Token;
import symbols.Type;

public class Op extends Expr{
	public Op(Token token, Type type) {
		super(token, type);
	}
	public Expr reduce() {
		Expr xExpr = gen();
		Temp temp = new Temp(type);
		emit(temp.toString() + " = " + xExpr.toString());
		return temp;
	}
}
