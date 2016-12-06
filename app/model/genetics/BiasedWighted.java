package model.genetics;

public interface BiasedWighted {
	double[] getWeights();

	double[] getBiases();

	void setWeights(double[] weights);

	void setBiases(double[] biases);
}
