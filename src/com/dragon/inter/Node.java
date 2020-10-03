package com.dragon.inter;

import com.dragon.lexer.Lexer;

public class Node {
	int lexLine = 0;
	Node() {
		lexLine = Lexer.line;
	}
	
	void error(String string) {
		throw new Error("near line " + lexLine + ": " + string);
	}
	
	static int labels = 0;
	public int newLabel() {
		return ++labels;
	}
	
	public void emitLabel(int i) {
		System.out.print("L" + i + ":");
	}
	
	public void emit(String string) {
		System.out.print("\t" + string);
	}
	
}
