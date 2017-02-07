package model.genetics;

import java.util.Arrays;
import java.util.stream.Collectors;

import model.ai.nn.NeuralNetwork;

public class Dna {
	public double[] biases;
	public double[] weights;

	public Dna(int weightsCount, int biasesCount) {
		biases = new double[biasesCount];
		weights = new double[weightsCount];
	}

	public Dna(NeuralNetwork biasedWighted) {
		biases = biasedWighted.getBiases();
		weights = biasedWighted.getWeights();
	}

	public Dna(double[] biases, double[] weights) {
		this.biases = biases;
		this.weights = weights;
	}

	public String serialize() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.stream(biases).mapToObj(d -> Double.toString(d)).collect(Collectors.joining(";")));
		sb.append(";;;");
		sb.append(Arrays.stream(weights).mapToObj(d -> Double.toString(d)).collect(Collectors.joining(";")));
		return sb.toString();
	}

	public static Dna deserialize(String str) {
		String[] split = str.split(";;;");
		double[] biases = Arrays.stream(split[0].split(";")).mapToDouble(Double::valueOf).toArray();
		double[] weights = Arrays.stream(split[1].split(";")).mapToDouble(Double::valueOf).toArray();
		return new Dna(biases, weights);
	}
}
