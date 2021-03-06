package lexer;

import java.io.IOException;
import java.util.Hashtable;

import symbols.Type;



public class Lexer {

	public static int line = 1;
	char peek = ' ';
	Hashtable<String, Word> words = new Hashtable<>();
	void reserve(Word word) {
		words.put(word.lexeme, word);
	}
	
	public Lexer() {
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);
		reserve(Word.False);
		reserve(Type.Int);
		reserve(Type.Float);
		reserve(Type.Char);
		reserve(Type.Bool);
	}
	
	void readch() throws IOException {
		peek = (char) System.in.read();
	}
	
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c) {
			return false;
		}
		peek = ' ';
		return true;
	}

	/**
	 * This is the environment in the UNIX, but we are in the Windows10, so we must change
	 *			peek == ' ' || peek == '\t'
	 *			and
	 *			peek == '\n'
	 *			to
	 *			peek == ' ' || peek == '\t' || peek == '\n'
	 *			and
	 *			peek == '\r'
	 * @return
	 * @throws IOException
	 */
	public Token scan() throws IOException {
		for (; ; readch() ) {
			if (peek == ' ' || peek == '\t' || peek == '\n') {
				continue;
			} else if (peek == '\r') {
				line += 1;
			} else {
				break;
			}
		}
		switch (peek) {
		case '&': if (readch('&')) { return Word.and;} else { return new Token('&');}
		case '|': if (readch('|')) { return Word.or ;} else { return new Token('|');}
		case '=': if (readch('=')) { return Word.eq ;} else { return new Token('=');}
		case '!': if (readch('=')) { return Word.ne ;} else { return new Token('!');}
		case '<': if (readch('=')) { return Word.le ;} else { return new Token('<');}
		case '>': if (readch('=')) { return Word.ge ;} else { return new Token('>');}
		}
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			
			if (peek != '.') {
				return new Num(v);
			}
			float x = v;
			float d = 10;
			for (; ; ) {
				readch();
				if (! Character.isDigit(peek)) {
					break;
				}
				x = x * 10 + Character.digit(peek, 10) / d;
				d *= 10;
			}
			return new Real(x);
		}
		
		if (Character.isLetter(peek)) {
			StringBuffer buffer = new StringBuffer();
			do {
				buffer.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String string = buffer.toString();
			Word word = (Word) words.get(string);
			if (word != null) {
				return word;
			}
			word = new Word(string, Tag.ID);
			words.put(string, word);
			return word;
		}
		
		Token token = new Token(peek);
		peek = ' ';
		return token;
	}
}
	
