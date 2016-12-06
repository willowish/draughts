package model.genetics;

public interface Network {
	double[] getWeights();

	double[] getBiases();

	void setWeights(double[] weights);

	void setBiases(double[] biases);
}
