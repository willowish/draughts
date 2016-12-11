package model.genetics;

public interface BiasedWeighted {
	double[] getWeights();

	double[] getBiases();

	void setWeights(double[] weights);

	void setBiases(double[] biases);
}
