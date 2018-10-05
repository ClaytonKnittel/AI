package miri;

public class ProperNoun extends Noun {
	
	private boolean isGirl;
	
	public ProperNoun(String word, boolean an, boolean isGirl) {
		super(word, 2, an);
		this.isGirl = isGirl;
	}
	
	public ProperNoun(String word, boolean isGirl) {
		this(word, false, isGirl);
	}
	
	public boolean isGirl() {
		return isGirl;
	}
}
