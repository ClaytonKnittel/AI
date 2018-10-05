package miri;

public class Miri {
	
	@SuppressWarnings("unused")
	private boolean girl;
	private Rememberer memory;
	int happiness;

	public Miri(boolean girl) {
		this.girl = girl;
		memory = new Rememberer();
		happiness = (int) (Math.random() * 11) - 5;
	}
	
	public String say(String s) {
		String[] sent = interpret(s);
		for (String x : sent)
			memory.add(x);
		return comment();
	}
	
	private String comment() {
		return Dictionary.getRandomSentence();
	}
	
	private String[] interpret(String s) {
		String[] partition = partition(s);
		for (String q : partition)
			happiness += Dictionary.getValue(q);
		return partition;
	}
	
	private String[] partition(String s) {
		String[] ret = new String[0];
		String word = "";
		for (int x = 0; x < s.length(); x++) {
			char c = s.charAt(x);
			if (c >= 97 && c <= 122)
				word += c;
			else if (c >= 65 && c <= 90)
				word += (c + 32);
			else {
				if (word != "") {
					ret = Rememberer.add(ret, word);
					word = "";
				}
			}
		}
		ret = Rememberer.add(ret, word);
		return ret;
	}
	
	@SuppressWarnings("unused")
	private boolean find(String s, String what) {
		for (int x = 0; x < s.length() - what.length(); x++) {
			if (s.substring(x, x + what.length()).equalsIgnoreCase(what))
				return true;
		}
		return false;
	}
}
