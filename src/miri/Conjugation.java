package miri;

public class Conjugation {

	private String[] table; //fs, ss, ts, fp, sp, tp
	
	public static final Conjugation BE = new Conjugation("am", "are", "is", "are", "are", "are");
	public static final Conjugation HAVE = new Conjugation("have", "have", "has", "have", "have", "have");
	
	public Conjugation(String[] table) {
		this.table = table;
	}
	
	public Conjugation(String a, String b, String c, String d, String e, String f) {
		this(new String[] {a, b, c, d, e, f});
	}
	
	public boolean conjugates() {
		return table != null;
	}
	
	public String get(int pos) {
		return table[pos];
	}
}
