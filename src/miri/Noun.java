package miri;

public class Noun extends Word {

	/**
	 * @author Clayton Knittel
	 * perspective = point of view of the noun (usually third person singular)
	 * an = true if you need to use an before it rather than a
	 */
	private int perspective;
	private String plural;
	
	public Noun(String word, int perspective, boolean an, String plural) {
		super(word, P.NOUN, an);
		this.perspective = perspective;
		if (plural != null)
			this.plural = plural;
	}
	
	public Noun(String word, int perspective, boolean an) {
		this(word, perspective, an, null);
	}
	
	public Noun(String word, String plural) {
		this(word, 2, false, plural);
	}
	
	public Noun(String word, int perspective) {
		this(word, perspective, false);
	}
	
	public Noun(String word, boolean an) {
		this(word, 2, an);
	}
	
	public Noun(String word) { // assume words are in third person singular if no pos specified
		this(word, 2);
	}
	
	public int getPerspective() {
		return perspective;
	}
	
	public String get(Word[] sentence, int index) {
		return Pronoun.getPronoun(index, sentence);
	}
	
	public Word pluralize() {
		if (plural == null) {
			if (get().charAt(get().length() - 1) == 'y')
				return new Noun(get().substring(0, get().length() - 1) + "ies");
			return new Noun(get() + "s", 5);
		}
		return new Noun(plural, 5);
	}
}
