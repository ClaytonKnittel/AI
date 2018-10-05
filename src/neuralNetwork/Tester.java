package neuralNetwork;

import arrays.AO;

public class Tester {
	
	public Network network;
	private int testMode;
	private float maxScore = 0;
	
	public static final int BASIC = 1;
	public static final int ADDITION = 2;
	public static final int ONEDIM = 3;
	
	public static float m;
	
	public Tester(Network network, int testMode) {
		this.network = network;
		this.testMode = testMode;
		m = .05f;
	}
	
	public void findHyperParameters(int minLayers, int maxLayers, int minNeurons, int maxNeurons) {
		int[] trialNetwork;
		int bestLayers = 0;
		int bestNeurons = 0;
		float bestScore = 0;
		for (int layers = minLayers; layers <= maxLayers; layers++) {
			trialNetwork = new int[layers];
			for (int neurons = minNeurons; neurons <= maxNeurons; neurons++) {
				trialNetwork = AO.fill(trialNetwork, neurons);
				trialNetwork[0] = 4;
				trialNetwork[trialNetwork.length - 1] = 4;
				network = new Network(trialNetwork, network.location(), network.costFunction(), network.regularized());
				maxScore = 0;
				float score = testHypers(30, 200, 20);
				Display.render(network);
				System.out.println(score);
				if (score > bestScore) {
					bestScore = score;
					bestLayers = layers;
					bestNeurons = neurons;
				}
				System.out.println();
			}
		}
		System.out.println("Score: " + bestScore + "\nLayers: " + bestLayers + "\nNeurons: " + bestNeurons);
	}
	
	private float testHypers(int trials, int batches, int batchSize) {
		maxScore = 0;
		for (int tries = 0; tries < trials; tries++) {
			trainBatches(batches, batchSize);
		}
		return maxScore;
	}
	
	public void learningTest(int datapts, int times, int batchSize, int terminate, float threshhold,
			float learnRateScale, float learnRateMin, boolean costMeasure) {
		float best = 0, trial = 0;
		int counter = 0;
		for (int x = 0; counter <= terminate; x++) {
			if (datapts != 0) {
				if (x >= datapts)
					return;
			}
			trainBatches(times, batchSize);
			Display.render(network);
			counter ++;
			
			if (costMeasure) {
				trial = costCurrentState(1000);
				if (trial < best) {
					best = trial;
					counter = 0;
				}
				if (trial > threshhold)
					counter = 0;
			}
			else {
				trial = accuracyCurrentState(1000);
				if (trial > best) {
					best = trial;
					counter = 0;
				}
				if (trial < threshhold)
					counter = 0;
			}
			
			if (counter > terminate && Network.NU > learnRateMin) {
				Network.NU /= learnRateScale;
				counter = 0;
			}
			System.out.println(trial);
		}
		System.out.println("Cost: " + costCurrentState(10000) + "\nAccuracy: " + accuracyCurrentState(10000));
	}
	
	public void printFunction(int datapts) {
		for (float x = 0; x <= 1; x += 1f / datapts) {
			System.out.println(network.input(new float[] {x})[0]);
		}
	}
	
	public void trainBatches(int times, int batchSize) {
		for (int x = 0; x < times; x++) {
			trainBatch(batchSize);
		}
	}
	
	public void trainBatch(int size) {
		float[][][] a = tests(size);
		network.train(a[0], a[1]);
	}
	
	public float costCurrentState(int trials) {
		return network.cost(tests(trials));
	}
	
	public float accuracyCurrentState(int trials) {
		int count = 0;
		for (int x = 0; x < trials; x++) {
			float[][] res = test();
			int[] response = network.inputAbsolute(res[0]);
			if (passes(response, res[1]))
				count ++;
		}
		return ((float) count) / trials;
	}
	
	public void printTest(int num) {
		System.out.println((accuracyCurrentState(num) * num) + " / " + num);
	}
	
	private boolean passes(int[] input, float[] answer) {
		for (int x = 0; x < input.length; x++) {
			if (input[x] != answer[x])
				return false;
		}
		return true;
	}
	
	private float[][][] tests(int num) {
		switch(testMode) {
		case BASIC:
			return basicTests(num);
		case ADDITION:
			return additionTests(num);
		case ONEDIM:
			return onedimTests(num);
		}
		return null;
	}
	
	private float[][] test() {
		switch(testMode) {
		case BASIC:
			return basicTest();
		case ADDITION:
			return additionTest(2);
		case ONEDIM:
			return onedimTest();
		}
		return null;
	}
	
	private float[][][] basicTests(int num) {
		float[][][] ret = new float[2][num][8];
		for (int x = 0; x < num; x++) {
			float[][] r = basicTest();
			ret[0][x] = r[0];
			ret[1][x] = r[1];
		}
		return ret;
	}
	
	private float[][] basicTest() {
		float[] a = new float[] {(int) (Math.random() * 2), (int) (Math.random() * 2),
				(int) (Math.random() * 2), (int) (Math.random() * 2)};
		float c = a[2];
		float d = a[0];
		return new float[][] {a, {c, c, d, d}};
	}
	
	private float[][][] additionTests(int num) {
		float[][][] ret = new float[2][num][4];
		for (int x = 0; x < num; x++) {
			float[][] r = additionTest(2);
			ret[0][x] = r[0];
			ret[1][x] = r[1];
		}
		return ret;
	}
	
	private float[][] additionTest(int bits) {
		float[] a = new float[2 * bits];
		int an = (int) (Math.random() * Math.pow(2, bits));
		int bn = (int) (Math.random() * Math.pow(2, bits));
		for (int x = 0; x < bits; x++)
			a[x] = ((int) (an / Math.pow(2, bits - 1 - x))) % 2;
		for (int x = 0; x < bits; x++)
			a[x + bits] = ((int) (bn / Math.pow(2, bits - 1 - x))) % 2;
		float[] c = new float[2 * bits];
		for (int x = 0; x < c.length; x++)
			c[x] = ((int) ((an + bn) / Math.pow(2, 2 * bits - 1 - x))) % 2;
		return new float[][] {a, c};
	}
	
	private float[][][] onedimTests(int num) {
		float [][][] ret = new float[2][num][1];
		for (int x = 0; x < num; x++) {
			float[][] r = onedimTest();
			ret[0][x] = r[0];
			ret[1][x] = r[1];
		}
		return ret;
	}
	
	private float[][] onedimTest() {
		float[] a = new float[] {(float) Math.random()};
		//float[] b = new float[] {(float) (Math.cos(a[0] * Math.PI) + 1) / 2};
		float[] b = new float[] {a[0] * a[0]};
		return new float[][] {a, b};
	}
	
}
