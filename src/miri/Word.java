package miri;

public class Word {

	private String word;
	private P pos;
	private boolean an;
	
	public Word(String word, P pos, boolean an) {
		this.word = word;
		this.pos = pos;
		this.an = an;
	}
	
	public Word(String word, P pos) {
		this(word, pos, false);
	}
	
	public String get() {
		return word;
	}
	
	public P getPOS() {
		return pos;
	}
	
	public void setPOS(P p) {
		pos = p;
	}
	
	public boolean an() {
		return an;
	}
	
	public boolean is(String s) {
		return s.equalsIgnoreCase(get());
	}
}