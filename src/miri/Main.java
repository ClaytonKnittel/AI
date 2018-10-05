package miri;

import java.util.Scanner;

public class Main implements Runnable {
	
	private Scanner s = new Scanner(System.in);
	public static Miri miri;

	public static void main(String args[]) {
		miri = new Miri(true);
		Main f = new Main();
		Thread t = new Thread(f);
		t.start();
	}
	
	public void run() {
		String word;
		do {
			word = s.nextLine();
			System.out.println(miri.say(word));
		} while (!word.equalsIgnoreCase("bye"));
	}
}