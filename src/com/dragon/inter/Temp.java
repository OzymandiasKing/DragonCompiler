package com.dragon.inter;

import com.dragon.lexer.Word;
import com.dragon.symbols.Type;

public class Temp extends Expr{
	static int count = 0;
	int number = 0;
	public Temp(Type type) {
		super(Word.temp, type);
		number = ++ count;
	}
	
	@Override
	public String toString() {
		return "t" + number;
	}
	
}
