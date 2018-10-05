package neuralNetwork;

import java.awt.Graphics;

public class Network {
	
	private Layer[] network;
	private FileManager file;
	private boolean regularization;
	private Cost costFunction = Cost.QUADRATIC;
	
	public static enum Cost {
		QUADRATIC,
		CROSS_ENTROPY
	}
	
	public static final boolean REGULARIZED = true;
	public static final boolean UNREGULARIZED = false;
	
	public static enum Smooth {
		SIGMOID,
		TANH,
		SHARP_RECTIFIER,
		SMOOTH_RECTIFIER
	}

	private static Smooth sFunction = Smooth.SIGMOID;
	
	public static float LAMBDA = .0005f;
	public static float NU = .2f;
	
	public Network(String location, Cost costFunction, boolean regularization) {
		file = new FileManager(location);
		file.open();
		network = file.getData();
		this.costFunction = costFunction;
		this.regularization = regularization;
	}
	
	/**
	 * Constructs a network with numbers[0] neurons in the first Layer, number[1] neurons
	 * in the second Layer, etc.
	 * <p>
	 * The first row are the inputs and the last row are the outputs.
	 * 
	 * @param numbers an integer array of the number of neurons in each Layer
	 */
	public Network(int[] numbers, String location, Cost costFunction, boolean regularization) {
		construct(numbers);
		file = new FileManager(location);
		file.save(network);
		this.costFunction = costFunction;
		this.regularization = regularization;
	}
	
	public Network(int[] numbers, Cost costFunction, boolean regularization) {
		construct(numbers);
		randomize();
		this.costFunction = costFunction;
		this.regularization = regularization;
	}
	
	public void randomize() {
		int[] numbers = new int[network.length];
		for (int x = 0; x < numbers.length; x++) {
			numbers[x] = network[x].size();
		}
		construct(numbers);
	}
	
	public void revert() {
		file.open();
		network = file.getData();
	}
	
	public void save() {
		file.save(network);
	}
	
	public String location() {
		return file.location();
	}
	
	public boolean regularized() {
		return regularization;
	}
	
	public Cost costFunction() {
		return costFunction;
	}
	
	public static Smooth sFunction() {
		return sFunction;
	}
	
	public void construct(int[] numbers) {
		network = new Layer[numbers.length];
		network[0] = new Layer(numbers[0], 0);
		for (int x = 1; x < network.length; x++) {
			network[x] = new Layer(numbers[x], numbers[x - 1]);
		}
	}

	 /** a is the activation of this neuron (a float from 0 to 1)
	  * @param l the lth row of this network
	  * @param j the jth neuron in the lth row
	  * @return a the activation of this neuron
	  */
	public float a(int l, int j) {
		return network[l].activation(j);
	}
	
	public static float sFunction(float a) {
		switch (sFunction) {
		case SIGMOID:
			return sigmoid(a);
		case TANH:
			return (float) (Math.tanh(a) + 1) / 2;
		case SHARP_RECTIFIER:
			if (a <= 0)
				return 0;
			return a;
		case SMOOTH_RECTIFIER:
			return (float) Math.log(1 + Math.exp(a));
		}
		return 0;
	}
	
	public static float sFunctionInverse(float a) {
		switch (sFunction) {
		case SIGMOID:
			return sigmoidInverse(a);
		case TANH:
			return (float) (Math.log((a) / (1 - a)) / 2);
		case SHARP_RECTIFIER:
			if (a < 0)
				System.err.println("Invalid inverse, a < 0 for sharp rectifier");
			return a;
		case SMOOTH_RECTIFIER:
			return (float) Math.log(Math.exp(a) - 1);
		}
		return 0;
	}
	
	public static float dsFunction(float a) {
		switch (sFunction) {
		case SIGMOID:
			return dsigmoid(a);
		case TANH:
			return 2 * (float) (Math.exp(2 * a) / Math.pow(1 + Math.exp(2 * a), 2));
		case SHARP_RECTIFIER:
			if (a < 0)
				return 0;
			return 1;
		case SMOOTH_RECTIFIER:
			return (float) (1 / (1 + Math.exp(-a)));
		}
		return 0;
	}
	
	public static float sigmoid(float a) {
		return (float) (1 / (1 + Math.exp(-a)));
	}
	
	public static float sigmoidInverse(float a) {
		return (float) Math.log(a / (1 - a));
	}
	
	public static float dsigmoid(float a) {
		return (float) (.5f / (1 + Math.cosh(a)));
	}
	
	public float cost(float a, float correct) {
		switch (costFunction) {
		case QUADRATIC:
			return quadraticCost(a, correct);
		case CROSS_ENTROPY:
			return crossEntropyCost(a, correct);
		}
		return 0;
	}
	
	public float dCost(float a, float correct) {
		switch (costFunction) {
		case QUADRATIC:
			return dQuadraticCost(a, correct);
		case CROSS_ENTROPY:
			return dCrossEntropyCost(a, correct);
		}
		return 0;
	}
	
	public static float quadraticCost(float a, float correct) {
		return (float) Math.pow(a - correct, 2);
	}
	
	public static float dQuadraticCost(float a, float correct) {
		return 2 * (a - correct);
	}
	
	public static float crossEntropyCost(float a, float correct) {
		if (correct == 0)
			return (float) (-Math.log(1 - a));
		if (correct == 1)
			return (float) (-Math.log(a));
		return (float) (-correct * Math.log(a) + (correct - 1) * Math.log(1 - a));
	}
	
	public static float dCrossEntropyCost(float a, float correct) {
		return (a - correct) / (a * (1 - a));
	}
	
	/**
	 * does not account for regularization
	 * @param correct
	 * @return the cost
	 */
	public float cost(float[] correct) {
		float cost = 0;
		for (int x = 0; x < correct.length; x++)
			cost += cost(network[network.length - 1].activation(x), correct[x]);
		return cost / correct.length;
	}
	
	public float cost(float[][] inputs, float[][] correct) {
		float add = 0;
		if (regularization) {
			for (int x = 1; x < network.length; x++) {
				add += network[x].sumWeightsSquared();
			}
			add *= LAMBDA / 2;
		}
		float cost = 0;
		for (int x = 0; x < inputs.length; x++) {
			input(inputs[x]);
			cost += cost(correct[x]);
		}
		return cost / inputs.length + add / inputs.length;
	}
	
	public float cost(float[][][] incor) {
		return cost(incor[0], incor[1]);
	}
	
	public float[] input(float[] inputs) {
		if (inputs.length != network[0].size()) {
			System.err.println("Incompatible input sizes");
			return null;
		}
		for (int x = 0; x < inputs.length; x++) {
			network[0].activate(x, sFunctionInverse(inputs[x]));
		}
		for (int l = 1; l < network.length; l++) {
			for (int j = 0; j < network[l].size(); j++) {
				network[l].activate(j, weightedInput(l, j));
			}
		}
		return network[network.length - 1].activations();
	}
	
	public int[] inputAbsolute(float[] inputs) {
		float[] results = input(inputs);
		int[] ret = new int[results.length];
		for (int x = 0; x < ret.length; x++)
			ret[x] = (results[x] < .5f ? 0 : 1);
		return ret;
	}
	
	public float output(int j) {
		return network[network.length - 1].activation(j);
	}
	
	public void train(float[][] inputs, float[][] correct) {
		for (int x = 0; x < inputs.length; x++) {
			train(inputs[x], correct[x]);
		}
		applyChanges(inputs.length); // inputs.length = minibatch size
	}
	
	private void train(float[] inputs, float[] correct) {
		this.input(inputs);
		
		float[] nudges = new float[correct.length];
		for (int j = 0; j < nudges.length; j++)
			nudges[j] = dCost(activation(network.length - 1, j), correct[j])
						* dsFunction(weightedActivation(network.length - 1, j));
		for (int l = network.length - 1; l > 0; l--) {
			nudges = network[l].gradient(nudges, network[l - 1].activations());
		}
	}
	
	private void applyChanges(int numTrials) {
		for (Layer l : network)
			l.applyChanges(numTrials, regularization);
	}
	
	public void slightMutation(float chanceMutate, float mutateVolatility) {
		for (Layer l : network)
			l.slightMutation(chanceMutate, mutateVolatility);
	}
	
	/**
	 * 
	 * @param l the lth row of this network
	 * @param j the jth neuron in the lth Layer
	 * @return z the weighted input to this neuron
	 */
	private float weightedInput(int l, int j) {
		float z = 0;
		float[] weights = network[l].weights(j);
		for (int x = 0; x < network[l - 1].size(); x++)
			z += network[l - 1].activation(x) * weights[x];
		z += network[l].bias(j);
		return z;
	}
	
	public float activation(int l, int j) {
		return network[l].activation(j);
	}
	
	public float weightedActivation(int l, int j) {
		return network[l].weightedActivation(j);
	}
	
	public void draw(Graphics g) {
		for (int x = 1; x < network.length; x++) {
			network[x].drawLines(g, (int) (Display.width * (.8f * (((float) x) / (network.length - 1)) + .1f)), (int) (Display.width * (.8f / (network.length - 1))), network[x - 1].size());
		}
		for (int x = 0; x < network.length; x++) {
			network[x].drawCircles(g, (int) (Display.width * (.8f * (((float) x) / (network.length - 1)) + .1f)));
		}
	}
}
