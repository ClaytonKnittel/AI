package network.graphics;

import java.awt.Color;
import java.awt.Graphics;

import network.Layer;
import network.Network;
import network.Neuron;
import network.NeuronAndWeight;

public class Drawer {
	
	static boolean text = true;
	
	public static void draw(Network n, Graphics g, int x, int y, int width, int height) {
		for (int i = 0; i < n.numLayers(); i++)
			draw(n.get(i), g, x + (i + 1) * width / (n.numLayers() + 1f), y, height, x + (i + 2) * width / (n.numLayers() + 1f));
	}
	
	static void draw(Layer l, Graphics g, float x, float y, float height, float x2) {
		for (int j = 0; j < l.length(); j++) {
			draw(l.get(j), g, x, y + (j + 1) * height / (l.length() + 1f), height / (l.length() + 1f) * Layer.percentageFilled / 2, x2, y, height);
		}
	}
	
	static void draw(Neuron n, Graphics g, float x, float y, float radius, float x2, float y2Offset, float height) {
		for (NeuronAndWeight nw : n.neuronsTo())
			draw(nw, g, x, y, x2, y2Offset, height);
		g.setColor(Network.blendColor(n.activation()));
		g.fillOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
		g.setColor(Network.blendColor((n.bias() + 1f) / 2f));
		g.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
		g.drawOval((int) (x - radius) + 1, (int) (y - radius) + 1, (int) (2 * radius) - 2, (int) (2 * radius) - 2);
		if (text) {
			g.setColor(Color.BLACK);
			g.drawString("" + n.activation(), (int) x - 30, (int) y);
		}
	}
	
	static void draw(NeuronAndWeight n, Graphics g, float x1, float y, float x2, float y2Offset, float height) {
		g.setColor(Network.blendColor((n.weight() + 1f) / 2f));
		g.drawLine((int) x1, (int) y, (int) x2, (int) (y2Offset + height * (n.neuronTo().pos() + 1) / (n.neuronTo().lengthLayer() + 1)));
	}
	
}
