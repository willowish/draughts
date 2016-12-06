package model.genetics;

public class Dna {
	public double[] biases;
	public double[] weights;

	public Dna(int weightsCount, int biasesCount) {
		biases = new double[biasesCount];
		weights = new double[weightsCount];
	}

	public Dna(BiasedWighted biasedWighted) {
		biases = biasedWighted.getBiases();
		weights = biasedWighted.getWeights();
	}
}
