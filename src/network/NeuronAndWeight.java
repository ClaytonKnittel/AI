package network;

public class NeuronAndWeight {
	
	Neuron neuronFrom, neuronTo;
	float weight;
	
	NeuronAndWeight(Neuron neuronFrom, Neuron neuronTo, float weight) {
		this.neuronFrom = neuronFrom;
		this.neuronTo = neuronTo;
		this.weight = weight;
	}

	public Neuron neuronFrom() {
		return this.neuronFrom;
	}
	
	public Neuron neuronTo() {
		return this.neuronTo;
	}
	
	public float weight() {
		return weight;
	}
	
	public void changeWeight(float delta) {
		weight += delta;
	}
	
}
