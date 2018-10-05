package network;

import java.util.LinkedList;

import network.utils.Functions;

public class Neuron {
	
	private LinkedList<NeuronAndWeight> to, from;
	
	// length of the Layer this Neuron is in
	private int length;
	// which Neuron this is in the Layer
	private int pos;
	
	private float bias;
	private float activation;
	
	// keeps track of inputs from all Neurons leading to this one (z value, not normalized)
	private float input;
	
	Neuron(int length, int pos) {
		this(length, pos, random());
	}
	
	Neuron(int length, int pos, float bias) {
		this.length = length;
		this.pos = pos;
		this.bias = bias;
		resetLists();
	}
	
	private void resetLists() {
		to = new LinkedList<NeuronAndWeight>();
		from = new LinkedList<NeuronAndWeight>();
	}
	
	public int lengthLayer() {
		return length;
	}
	
	public int pos() {
		return pos;
	}
	
	public float activation() {
		return activation;
	}
	
	public float bias() {
		return bias;
	}
	
	public void clearBias() {
		bias = 0;
	}
	
	public void setActivation(float val) {
		activation = val;
	}
	
	public void setInput(float val) {
		input = val;
	}
	
	public void resetInput() {
		setInput(0);
	}
	
	public void input(float val) {
		input += val;
	}
	
	
	
	public void finalize() {
		activation = Functions.sigmoid(input + bias);
		finalize(activation);
	}
	
	public void finalize(float activation) {
		setActivation(activation);
		for (NeuronAndWeight nw : to)
			nw.neuronTo().input(activation * nw.weight());
	}
	
	
	public Iterable<NeuronAndWeight> neuronsTo() {
		return to;
	}
	
	public Iterable<NeuronAndWeight> neuronsFrom() {
		return from;
	}
	
	public void randomWeight(Layer nextLayer) {
		randomWeight(nextLayer.neurons);
	}
	
	void randomWeight(Neuron[] nextLayer) {
		for (Neuron n : nextLayer)
			to.add(new NeuronAndWeight(this, n, random()));
	}
	
	public void setWeight(Layer nextLayer, float[] weights) {
		setWeight(nextLayer.neurons, weights);
	}
	
	void setWeight(Neuron[] nextLayer, float[] weights) {
		for (int i = 0; i < nextLayer.length; i++)
			to.add(new NeuronAndWeight(this, nextLayer[i], weights[i]));
	}
	
	public void setFroms(Iterable<NeuronAndWeight> froms) {
		for (NeuronAndWeight n : froms)
			from.add(n);
	}
	
	public static float random() {
		return (float) (Math.random() * 2 - 1);
	}
	
	public String toString() {
		return "" + activation;
	}
}
