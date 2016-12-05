package model.ai.nn;

public class NeuralNetwork {

	int nodesInInputLayer = 32;
	int nodesInFirstLayer = 40;
	int nodesInSecondLayer = 10;

	double[] inputLayer = new double[nodesInInputLayer];
	double[] firstLayer = new double[nodesInFirstLayer];
	double[] secondLayer = new double[nodesInSecondLayer];
	double outputNode = 0;
	double[][] ifWeights = new double[nodesInInputLayer][nodesInFirstLayer];
	double[][] fsWeights = new double[nodesInFirstLayer][nodesInSecondLayer];
	double[] soWeights = new double[nodesInSecondLayer];
	double[] fBiases = new double[nodesInFirstLayer];
	double[] sBiases = new double[nodesInSecondLayer];
	double outputBias = 0;

	public NeuralNetwork() {
		init();
	}

	void init() {
		for (int i = 0; i < nodesInInputLayer; i++) {
			for (int j = 0; j < nodesInFirstLayer; j++) {
				ifWeights[i][j] = Math.random() * (.4) - .2;
			}
		}

		for (int i = 0; i < nodesInFirstLayer; i++) {
			for (int j = 0; j < nodesInSecondLayer; j++) {
				fsWeights[i][j] = Math.random() * (.4) - .2;
			}
			fBiases[i] = Math.random() * (.4) - .2;
		}

		for (int i = 0; i < nodesInSecondLayer; i++) {
			soWeights[i] = Math.random() * (.4) - .2;
			sBiases[i] = Math.random() * (.4) - .2;
		}
	}

	static double tanh(double x) {
		double a = Math.exp(x);
		double b = Math.exp(-x);
		double result = (a - b) / (a + b);
		if (result > 1)
			result = 1;
		else if (result < -1)
			result = -1;
		return result;
	}

	public double compute(double[] input) {
		if (input.length != nodesInInputLayer) {
			System.out.println("Niewlasciwy argument");
		}
		for (int i = 0; i < nodesInInputLayer; i++) {
			outputBias += input[i];
		}
		for (int i = 0; i < nodesInFirstLayer; i++) {
			for (int j = 0; j < nodesInInputLayer; j++) {
				firstLayer[i] += input[j] * ifWeights[j][i];
			}
			firstLayer[i] += fBiases[i];
			firstLayer[i] = tanh(firstLayer[i]);
		}
		for (int i = 0; i < nodesInSecondLayer; i++) {
			for (int j = 0; j < nodesInFirstLayer; j++) {
				secondLayer[i] += firstLayer[j] * fsWeights[j][i];
			}
			secondLayer[i] += sBiases[i];
			secondLayer[i] = tanh(secondLayer[i]);
			outputNode += secondLayer[i] * soWeights[i];
		}
		// outputNode += outputBias;
		outputNode = tanh(outputNode);
		return outputNode;
	}

}
