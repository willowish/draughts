package model.ai.nn;

import model.genetics.BiasedWighted;

public class NeuralNetwork implements BiasedWighted {

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

	// TODO why not Math.tanh(x)? ew. if(x>10)return +-1 else Math.tanh
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

	@Override
	public double[] getWeights() {
		// TODO is it correct?
		double[] weights = new double[inputLayer.length + firstLayer.length + secondLayer.length];
		System.arraycopy(inputLayer, 0, weights, 0, inputLayer.length);
		System.arraycopy(firstLayer, 0, weights, inputLayer.length, firstLayer.length);
		System.arraycopy(secondLayer, 0, weights, firstLayer.length, secondLayer.length);
		return weights;
	}

	@Override
	public double[] getBiases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWeights(double[] weights) {
		// TODO is it correct?
		System.arraycopy(weights, 0, inputLayer, 0, inputLayer.length);
		System.arraycopy(weights, inputLayer.length, firstLayer, 0, firstLayer.length);
		System.arraycopy(weights, firstLayer.length, secondLayer, 0, secondLayer.length);
	}

	@Override
	public void setBiases(double[] biases) {
		// TODO Auto-generated method stub

	}

}
