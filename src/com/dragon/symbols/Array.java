package com.dragon.symbols;

import com.dragon.lexer.Tag;

public class Array extends Type{
	public Type of;
	public int size = 1;
	public Array(int size, Type of) {
		super("[]", Tag.INDEX, size * of.width);
		this.size = size;
		this.of = of;
	}
	
	@Override
	public String toString() {
		return "[" + size + "]" + of.toString();
	}

}
