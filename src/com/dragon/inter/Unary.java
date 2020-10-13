package com.dragon.inter;

import com.dragon.lexer.Token;
import com.dragon.symbols.Type;

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
