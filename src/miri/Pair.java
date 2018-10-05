package miri;

public class Pair {

	private String word;
	private int value;
	
	public Pair(String word, int value) {
		this.word = word;
		this.value = value;
	}
	
	public boolean is(String s) {
		return s.equalsIgnoreCase(word);
	}
	
	public int getValue() {
		return value;
	}
}