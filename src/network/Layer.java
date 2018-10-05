package network;

import java.lang.reflect.Array;

import network.utils.Functions;

public class Layer {
	
	protected Neuron[] neurons;
		
	public static final float percentageFilled = .8f;
	
	private Layer previous;
	
	// the vector operator used in backpropogation, explained in LaTeX paper
	private float[] v;
	
	Layer(int size) {
		instantiateNeurons(size);
		randomizeNeurons();
	}
	
	Layer(float[] biases) {
		instantiateNeurons(biases.length);
		for (int i = 0; i < neurons.length; i++)
			neurons[i] = new Neuron(neurons.length, i, biases[i]);
	}
	
	Layer(int size, Layer next) {
		this(size);
		connect(next);
	}
	
	Layer(float[] biases, float[][] weights, Layer next) {
		this(biases);
		connect(weights, next);
	}
	
	private void instantiateNeurons(int size) {
		neurons = new Neuron[size];
		v = new float[size];
	}
	
	void randomizeNeurons() {
		for (int i = 0; i < neurons.length; i++)
			neurons[i] = new Neuron(neurons.length, i);
	}
	
	public void connect(Layer next) {
		next.setPrev(this);
		for (int i = 0; i < neurons.length; i++)
			neurons[i].randomWeight(next);
	}
	
	public void connect(float[][] weights, Layer next) {
		next.setPrev(this);
		for (int i = 0; i < neurons.length; i++)
			neurons[i].setWeight(next, weights[i]);
	}
	
	private void setPrev(Layer l) {
		previous = l;
	}
	
	public int length() {
		return neurons.length;
	}
	
	public Neuron get(int pos) {
		return neurons[pos];
	}
	
	public void reset() {
		for (Neuron n : neurons)
			n.resetInput();
	}
	
	public boolean isFirst() {
		return previous == null;
	}
	
	
	public void input(float[] vals) {
		for (int i = 0; i < vals.length; i++) {
			neurons[i].finalize(vals[i]);
		}
	}
	
	public void finalize() {
		if (isFirst())
			return;
		for (Neuron n : neurons)
			n.finalize();
	}
	
	
	public void changeWeights() {
		for (int i = 0; i < neurons.length; i++) {
			for (NeuronAndWeight n : neurons[i].neuronsFrom())
				n.changeWeight(n.neuronTo().activation() * v[i]);
		}
	}
	
	public void prepareV(float[] expected) {
		for (int i = 0; i < neurons.length; i++) {
			v[i] = Functions.dCost(neurons[i].activation(), expected[i]) * Functions.dsigmoid(neurons[i].activation());
		}
		previous.prepV(wTv());
	}
	
	private void prepV(float[] weight_T_v) {
		if (!isFirst())
			previous.prepV(wTv());
	}
	
	private float[] wTv() {
		return Functions.mmult(prepW(), v);
	}
	
	@SuppressWarnings("unchecked")
	private Iterable<NeuronAndWeight>[] prepW() {
		Iterable<NeuronAndWeight>[] w = (Iterable<NeuronAndWeight>[]) Array.newInstance(neurons[0].neuronsTo().getClass(), neurons.length);
		for (int i = 0; i < w.length; i++)
			w[i] = neurons[i].neuronsTo();
		return w;
	}
	
	
	public String toString() {
		String ret = "";
		for (Neuron n : neurons)
			ret += n + " ";
		return ret;
	}
	
}
