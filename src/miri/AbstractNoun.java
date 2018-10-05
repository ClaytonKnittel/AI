package miri;

public class AbstractNoun extends Noun {

	public AbstractNoun(String word, boolean an, String plural) {
		super(word, 2, an, plural);
	}
	
	public AbstractNoun(String word, boolean an) {
		this(word, an, null);
	}
	
	public AbstractNoun(String word, String plural) {
		this(word, false, plural);
	}
	
	public AbstractNoun(String word) {
		this(word, false);
	}

}
