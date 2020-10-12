package com.dragon.inter;

import com.dragon.lexer.Token;
import com.dragon.symbols.Type;

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
