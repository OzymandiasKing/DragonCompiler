package com.dragon.inter;

import com.dragon.lexer.Type;
import com.dragon.lexer.Word;

public class Id extends Expr {
	public int offset;
	public Id(Word word, Type type, int offset) {
		super(word, type);
		this.offset = offset;
	}
}
