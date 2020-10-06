package com.dragon.inter;

import com.dragon.lexer.Word;
import com.dragon.symbols.Type;

public class Id extends Expr {
	public int offset;
	public Id(Word word, Type type, int offset) {
		super(word, type);
		this.offset = offset;
	}
}
