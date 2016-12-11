package model.ai.nn;

import static java.lang.Math.tanh;

import model.genetics.BiasedWeighted;

public class NeuralNetwork implements BiasedWeighted {

	public static final int nodesInInputLayer = 32;
	public static final int nodesInFirstLayer = 40;
	public static final int nodesInSecondLayer = 10;

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

	@Override
	public double[] getWeights() {
		double[] weights = new double[nodesInInputLayer * nodesInFirstLayer + nodesInFirstLayer * nodesInSecondLayer
				+ nodesInSecondLayer];

		int idx = 0;
		for (int i = 0; i < nodesInInputLayer; i++) {
			for (int j = 0; j < nodesInFirstLayer; j++) {
				weights[idx++] = ifWeights[i][j];
			}
		}
		for (int i = 0; i < nodesInFirstLayer; i++) {
			for (int j = 0; j < nodesInSecondLayer; j++) {
				weights[idx++] = fsWeights[i][j];
			}
		}
		for (int i = 0; i < nodesInSecondLayer; i++) {
			weights[idx++] = soWeights[i];
		}

		return weights;
	}

	@Override
	public double[] getBiases() {
		double[] biases = new double[nodesInFirstLayer + nodesInSecondLayer + 1];

		int idx = 0;
		for (int i = 0; i < fBiases.length; i++) {
			biases[idx++] = fBiases[i];
		}
		for (int i = 0; i < sBiases.length; i++) {
			biases[idx++] = sBiases[i];
		}
		biases[idx++] = outputBias;
		return biases;
	}

	@Override
	public void setWeights(double[] weights) {
		if (weights.length != nodesInInputLayer * nodesInFirstLayer + nodesInFirstLayer * nodesInSecondLayer
				+ nodesInSecondLayer) {
			throw new ArrayIndexOutOfBoundsException("Weights array length too small");
		}
		int idx = 0;
		for (int i = 0; i < nodesInInputLayer; i++) {
			for (int j = 0; j < nodesInFirstLayer; j++) {
				ifWeights[i][j] = weights[idx++];
			}
		}
		for (int i = 0; i < nodesInFirstLayer; i++) {
			for (int j = 0; j < nodesInSecondLayer; j++) {
				fsWeights[i][j] = weights[idx++];
			}
		}
		for (int i = 0; i < nodesInSecondLayer; i++) {
			soWeights[i] = weights[idx++];
		}
	}

	@Override
	public void setBiases(double[] biases) {
		if (biases.length != nodesInFirstLayer + nodesInSecondLayer + 1) {
			throw new ArrayIndexOutOfBoundsException("Biases array length too small");
		}

		int idx = 0;
		for (int i = 0; i < fBiases.length; i++) {
			fBiases[i] = biases[idx++];
		}
		for (int i = 0; i < sBiases.length; i++) {
			sBiases[i] = biases[idx++];
		}
		outputBias = biases[idx++];

	}

}
