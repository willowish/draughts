package model.genetics;

import model.ai.nn.NeuralNetwork;

public class NetworkProvider implements Provider<BiasedWeighted> {

	@Override
	public NeuralNetwork get() {
		return new NeuralNetwork();
	}

}
