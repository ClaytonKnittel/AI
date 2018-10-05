package miri;

public class Sentence {

	private P[] sentence;
	
	public Sentence(P[] sentence) {
		this.sentence = sentence;
	}
	
	public String createSentence() {
		Word[] ret = new Word[sentence.length];
		for (int x = 0; x < sentence.length; x++) {
			ret[x] = getWord(sentence[x]);
		}
		return stringify(ret);
	}
	
	private String stringify(Word[] s) {
		String ret = "";
		for (int x = 0; x < s.length; x++) {
			if (s[x].getPOS().equals(P.VERB) && x > 0) {
				if (sentence[x - 1].equals(P.NOUN)) {
					ret += ((Verb) s[x]).get(((Noun) s[x - 1]).getPerspective());
				}
				else
					ret += ((Verb) s[x]).get();
			}
			else if (s[x].getPOS().equals(P.ARTICLE)) {
				ret += s[x].get();
				if (s[x].is("a")) {
					ret += (((Noun) s[x + 1]).an() ? "n" : "");
				}
			}
			else if (s[x].getPOS().equals(P.NOUN)) {
				ret += Pronoun.getPronoun(x, s);
			}
			else
				ret += s[x].get();
			ret += (x == sentence.length - 1 ? "" : " ");
		}
		return ret + ".";
	}
	
	private Word getWord(P pos) {
		if (pos.equals(P.NOUN)) {
			if (Math.random() < .5)
				return ((Noun) Dictionary.getRandomWord(pos)).pluralize();
		}
		return Dictionary.getRandomWord(pos);
	}
	
	public static int[] findAll(Word[] sentence, String word) {
		int[] ret = new int[0];
		for (int x = 0; x < sentence.length; x++) {
			if (sentence[x].get().equalsIgnoreCase(word))
				ret = add(ret, x);
		}
		return ret;
	}
	
	public static int[] add(int[] x, int y) {
		int[] ret = new int[x.length + 1];
		for (int z = 0; z < x.length; z++)
			ret[z] = x[z];
		ret [x.length] = y;
		return ret;
	}
	
	public static int find(Word[] sentence, Word word) {
		for (int x = 0; x < sentence.length; x++) {
			if (sentence[x].get().equalsIgnoreCase(word.get()))
				return x;
		}
		return -1;
	}
	
	public static String[] enumerate(Word[] sentence) {
		String[] ret = new String[sentence.length];
		for (int x = 0; x < ret.length; x++)
			ret[x] = sentence[x].get();
		return ret;
	}
}
