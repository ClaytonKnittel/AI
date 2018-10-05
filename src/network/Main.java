package network;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JFrame;

import network.utils.Drawable;

public class Main extends JFrame implements Runnable {
	
	static LinkedList<Drawable> objects;
	
	static BufferStrategy b;
	
	static final int width = 800, height = 600;
	
	public static void main(String args[]) {
		Main m = new Main();
	}
	
	public Main() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setVisible(true);
		this.setResizable(false);
		this.createBufferStrategy(2);
		
		
		objects = new LinkedList<Drawable>();
		
		b = this.getBufferStrategy();
		
		Thread t = new Thread(new Main());
		t.start();
	}
	
	public void run() {
		Network n = new Network(new int[] {4, 4, 4});
		
		objects.add(n);
		
		int time = 0;
		float f;
		while (++time > 0) {
			f = (float) (Math.cos(time / 30f) + 1) / 2;
			n.input(new float[] {f, f, 0, 0});
			draw();
			try {
				Thread.sleep(30);
			} catch (Exception e) { e.printStackTrace(); }
		}
	}
	
	static void draw() {
		Graphics g = b.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		g.setColor(new Color(30, 30, 50));
		
		for (Drawable d : objects)
			d.draw(g, 0, 0, width, height);
		
		g.dispose();
		b.show();
	}
	
}
