package miri;

public class Rememberer {

	private String[] memory;
	
	public Rememberer() {
		memory = new String[0];
	}
	
	public void add(String s) {
		add(memory, s);
	}
	
	public static String[] add(String[] mem, String s) {
		String[] plus = new String[mem.length + 1];
		for (int x = 0; x < mem.length; x++) {
			plus[x] = mem[x];
		}
		plus[mem.length] = s;
		return plus;
	}
}
