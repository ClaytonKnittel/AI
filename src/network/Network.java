package network;

import java.awt.Color;
import java.awt.Graphics;

import network.graphics.ColorMath;
import network.graphics.Drawer;
import network.utils.Drawable;

public class Network implements Drawable {
	
	private Layer[] layers;
	
	public static final Color positiveColor = new Color(30, 200, 220);
	public static final Color negativeColor = new Color(230, 10, 10);
	
	public Network(int[] sizes) {
		layers = new Layer[sizes.length];
		for (int i = 0; i < layers.length; i++)
			layers[i] = new Layer(sizes[i]);
		for (int i = 1; i < layers.length; i++)
			layers[i - 1].connect(layers[i]);
	}
	
	public static Color blendColor(float positive) {
		return ColorMath.blend(Network.positiveColor, Network.negativeColor, positive);
	}
	
	public Layer get(int pos) {
		return layers[pos];
	}
	
	public int numLayers() {
		return layers.length;
	}
	
	public void reset() {
		for (Layer l : layers)
			l.reset();
	}
	
	
	public void draw(Graphics g, int x, int y, int width, int height) {
		Drawer.draw(this, g, x, y, width, height);
	}
	
	
	public void input(float[] inputs) {
		reset();
		layers[0].input(inputs);
		for (Layer l : layers)
			l.finalize();
	}
	
	
	public String toString() {
		String ret = "";
		for (Layer l : layers)
			ret += l + "\n";
		return ret;
	}
	
}
