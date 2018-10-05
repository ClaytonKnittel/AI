package miri;

public class Verb extends Word {

	private Conjugation conjugation;
	
	
	public Verb(String word, boolean an, Conjugation c) {
		super(word, P.VERB, an);
		if (c == null)
			conjugation = new Conjugation(null);
		else
			conjugation = c;
	}
	
	public Verb(String word, boolean an) {
		this(word, an, null);
	}
	
	public Verb(String word) {
		this(word, false);
	}
	
	public Verb(String word, Conjugation c) {
		this(word, false, c);
	}
	
	public String get(int x) { // -1 is infinitive, 0-5 are conjugations
		if (x == -1)
			return "to " + super.get();
		if (conjugation.conjugates())
			return conjugation.get(x);
		return super.get() + (x == 2 ? "s" : "");
	}
}
