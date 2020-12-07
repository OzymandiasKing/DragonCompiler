package symbols;

import java.util.Hashtable;

import inter.Id;
import lexer.Token;

public class Env {
	private Hashtable<Object, Object> tHashtable;
	protected Env pEnv;
	public Env(Env pEnv) {
		tHashtable = new Hashtable<>();
		this.pEnv = pEnv;
	}
	
	public void put(Token token, Id i) {
		tHashtable.put(token, i);
	}
	
	public Id get(Token token) {
		for (Env env = this; env != null; env = env.pEnv) {
			Id foundId = (Id) env.tHashtable.get(token);
			if (foundId != null) {
				return foundId;
			}
		}
		return null;
	}
}
