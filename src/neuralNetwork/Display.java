package neuralNetwork;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Display implements Runnable {
	
	private static BufferStrategy b;
	public static final int MENU_BAR_HEIGHT = 23;
	public static final int width = 600, height = 500;
	
	public static final String ADDER = "/Users/claytonknittel/documents/workspace/Artificial Intelligence/resources/adder.txt";
	public static final String TESTER = "/Users/claytonknittel/documents/workspace/Artificial Intelligence/resources/tester.txt";
	
	private static Network adder;

	public static void main(String args[]) {
		
		JFrame j = new JFrame();
		j.setLocation(400, 150);
		j.setSize(width, height + MENU_BAR_HEIGHT);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setResizable(false);
		j.setVisible(true);
		
		j.createBufferStrategy(2);
		b = j.getBufferStrategy();
		
		Display d = new Display();
		Thread t = new Thread(d);
		t.run();
	}
	
	public void run() {
		adder = new Network(new int[] {4, 4}, ADDER, Network.Cost.CROSS_ENTROPY, Network.UNREGULARIZED);
		float f[];
		for (int n = 0; n < 10000; n++) {
			render(adder);
			f = new float[] {(float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random()};
			adder.train(new float[][] {f}, new float[][] {f});
			if (n % 100 == 0)
				System.out.println(adder.cost(f));
		}
		adder.save();
		
//		//adder = new Network(ADDER, Network.CROSS_ENTROPY_COST, Network.REGULARIZED);
//		Tester tester = new Tester(adder, Tester.ADDITION);
//		try { Thread.sleep(200); } catch(Exception e) {}
//		
//		tester.network.input(new float[] {1, 0, 1, 0});
//		render(tester);
//		try { Thread.sleep(1000); } catch(Exception e) {}
//		
//		tester.trainBatches(10, 10);
//		tester.network.input(new float[] {1, 0, 1, 0});
//		render(tester);
//		//tester.learningTest(0, 10, 2, 20, .33f, 10, .0001f, true);
//		//tester.printFunction(100);
//		//tester.network.input(new float[] {1, 0, 1, 0});
//		
//		render(tester);
//		//System.exit(0);
	}
	
	public static void render(Network n) {
		Graphics g = b.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		
		n.draw(g);
		
		g.dispose();
		b.show();
	}
}
