package neuralNetwork;

import java.awt.Color;
import java.awt.Graphics;

public class Layer {
	
	/**
	 * Neurons is the array of biases associated with each neuron in this Layer.
	 * <p>
	 * Weights is the 2D array of weights associated with each neuron. Its dimensions
	 * are number of neurons x number of neurons connecting to each (from previous Layer).
	 * <p>
	 * activations is the array of (weighted) active values of each neuron in this Layer. They are recalculated
	 * every time the weights and biases are recalculated.
	 * <p>
	 * If weights is left null, this is the input layer.
	 */
	private float[] b;
	private float[][] w;
	private float[][] v;
	private float[] z;
	private float[] a;
	
	private float[] db;
	private float[][] dw;
	
	// negative color and positive color
	private static final int[] cn = new int[] {248, 80, 7};
	private static final int[] cp = new int[] {7, 248, 241};
	
	public static boolean momentum = false;
	
	public static float friction = .4f;
	
	
	/**
	 * 
	 * @param size how many neurons are in this Layer
	 * @param sizePrevious how many neurons are in the previous Layer
	 */
	public Layer(int size, int sizePrevious) {
		a = new float[size];
		z = new float[size];
		if (sizePrevious > 0) {
			b = new float[size];
			w = new float[size][sizePrevious];
			if (momentum)
				v = new float[size][sizePrevious];
			db = new float[size];
			dw = new float[size][sizePrevious];
			randomize();
		}
	}
	
	/**
	 * 
	 * @param weightsAndBiases an array of {bias, w1, w2, ...}
	 */
	public Layer(float[][] weightsAndBiases) {
		this(weightsAndBiases.length, 0);
		b = new float[weightsAndBiases.length];
		w = new float[b.length][weightsAndBiases[0].length - 1];
		if (momentum)
			v = new float[b.length][weightsAndBiases[0].length - 1];
		db = new float[b.length];
		dw = new float[w.length][w[0].length];
		for (int x = 0; x < b.length; x++) {
			b[x] = weightsAndBiases[x][0];
			for (int y = 1; y < weightsAndBiases[0].length; y++)
				w[x][y - 1] = weightsAndBiases[x][y];
		}
	}
	
	public void randomize() {
		for (int x = 0; x < b.length; x++) {
			b[x] = random();
			for (int y = 0; y < w[0].length; y++)
				w[x][y] = random() / w[0].length;
		}
	}
	
	private float random() {
		return Network.sigmoidInverse((float) Math.random()) / 2;
	}
	
	/**
	 * 
	 * @param chanceMutate chance that a single neuron mutates
	 * @param mutateVolatility max amount a single neuron can change by in mutation
	 */
	public void slightMutation(float chanceMutate, float mutateVolatility) {
		if (w == null)
			return;
		for (int j = 0; j < w.length; j++) {
			if (Math.random() > chanceMutate)
				b[j] += (float) ((2 * Math.random() - 1) * mutateVolatility);
			for (int i = 0; i < w[0].length; i++) {
				if (Math.random() > chanceMutate)
					w[j][i] += (float) ((2 * Math.random() - 1) * mutateVolatility);
			}
		}
	}
	
	public void activate(int j, float val) {
		z[j] = val;
		a[j] = Network.sFunction(val);
	}
	
	public int size() {
		return a.length;
	}
	
	public float activation(int j) {
		return a[j];
	}
	
	public float weightedActivation(int j) {
		return z[j];
	}
	
	public float bias(int j) {
		return b[j];
	}
	
	public float[] activations() {
		return a;
	}
	
	public float[] weightedActivations() {
		return z;
	}
	
	/**
	 * 
	 * @return all weights associated with the jth neuron of this Layer in a float array
	 */
	public float[] weights(int j) {
		return w[j];
	}
	
	public float weight(int j, int i) {
		return w[j][i];
	}
	
	public float sumWeightsSquared() {
		float sum = 0;
		for (int i = 0; i < w[0].length; i++) {
			for (int j = 0; j < w.length; j++)
				sum += w[j][i] * w[j][i];
		}
		return sum;
	}
	
	
	/**
	 * Takes in the nudges that should be applied to its activations and computes
	 * the gradient for this Layer, then returns the nudges to be applied to the
	 * activations of the layer before it.
	 * @param nudges the values by which each activation is desired to change
	 * @param prevActivations the unweighted values of activations of the layer l + 1
	 * @return an array of nudges to be applied to all activations in layer l - 1
	 */
	public float[] gradient(float[] nudges, float[] prevActivations) {
		for (int j = 0; j < size(); j++) {
			db[j] += nudges[j];
			for (int i = 0; i < w[0].length; i++) {
				dw[j][i] += prevActivations[i] * nudges[j];
			}
		}
		float[] ret = new float[w[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < size(); j++)
				ret[i] += w[j][i] * nudges[j];
			ret[i] *= Network.dsFunction(prevActivations[i]);
		}
		return ret;
	}
	
	public void applyChanges(float div, boolean regularization) {
		if (b == null)
			return;
		for (int j = 0; j < b.length; j++) {
			b[j] -= db[j] * Network.NU / div;
			for (int i = 0; i < w[0].length; i++) {
				if (momentum) {
					v[j][i] = friction * v[j][i] - dw[j][i] * Network.NU / div;
					if (regularization)
						v[j][i] -= Network.NU * Network.LAMBDA * w[j][i];
					w[j][i] += v[j][i];
				} else {
					w[j][i] -= dw[j][i] * Network.NU / div;
					if (regularization)
						w[j][i] -= Network.NU * Network.LAMBDA * w[j][i];
				}
			}
		}
		clearDeltas();
	}
	
	private void clearDeltas() {
		for (int j = 0; j < b.length; j++) {
			db[j] = 0;
			for (int i = 0; i < w[0].length; i++)
				dw[j][i] = 0;
		}
	}
	
	
	public void drawLines(Graphics g, int x, int dx, int prevLen) {
		if (w != null) {
			for (int i = 0; i < w.length; i++) {
				for (int j = 0; j < w[0].length; j++) {
					g.setColor(scaledColor(w[i][j]));
					g.drawLine(x - dx, getY(j, w[0].length), x, getY(i, w.length));
				}
			}
		}
	}
	
	public void drawCircles(Graphics g, int x) {
		int r = (int) (Math.min(.35f * Display.height / a.length, 40));
		for (int y = 0; y < a.length; y++) {
			g.setColor(color(a[y]));
			g.fillOval(x - r, getY(y, a.length) - r, 2 * r, 2 * r);
			if (b != null)
				g.setColor(scaledColor(b[y]));
			else
				g.setColor(new Color(0, 0, 0));
			g.drawOval(x - r, getY(y, a.length) - r, 2 * r, 2 * r);
			g.drawOval(x - r + 1, getY(y, a.length) - r + 1, 2 * r - 2, 2 * r - 2);
		}
	}
	
	private int getY(int j, int len) {
		return (int) ((j + .5f) * Display.height / (len + 1.0f) + Display.MENU_BAR_HEIGHT);
	}
	
	private Color scaledColor(float f) {
		return color(Network.sigmoid(f));
	}
	
	private Color color(float f) {
		if (Network.sFunction() == Network.Smooth.SHARP_RECTIFIER) {
			if (f > 0)
				f = Network.sigmoid(f);
		}
		int r = (int) (cn[0] * (1 - f) + cp[0] * f);
		int g = (int) (cn[1] * (1 - f) + cp[1] * f);
		int b = (int) (cn[2] * (1 - f) + cp[2] * f);
		return new Color(r, g, b);
	}
}
