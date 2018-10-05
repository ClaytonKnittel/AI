package network.utils;

import network.NeuronAndWeight;

public class Functions {
	
	public static float sigmoid(float a) {
		return (float) (1 / (1 + Math.exp(-a)));
	}
	
	public static float sigmoidInverse(float a) {
		return (float) Math.log(a / (1 - a));
	}
	
	public static float dsigmoid(float a) {
		return (float) (.5 / (1 + Math.cosh(a)));
	}
	
	public static float cost(float i, float e) {
		if (e == 0)
			return (float) (-Math.log(1 - i));
		if (e == 1)
			return (float) (-Math.log(i));
		return (float) (-e * Math.log(i) + (e - 1) * Math.log(1 - i));
	}
	
	public static float dCost(float i, float e) {
		return (i - e) / (i * (1 - i));
	}
	
	
	public static float[] mmult(Iterable<NeuronAndWeight>[] w, float[] v) {
		float[] ret = new float[w.length];
		for (int i = 0; i < ret.length; i++) {
			int vp = 0;
			for (NeuronAndWeight n : w[i])
				ret[i] += n.weight() * v[vp++];
		}
		return ret;
	}
	
}
