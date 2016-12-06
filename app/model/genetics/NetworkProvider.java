package model.genetics;

import model.ai.nn.NeuralNetwork;

public class NetworkProvider implements Provider<BiasedWighted> {

	@Override
	public NeuralNetwork get() {
		return new NeuralNetwork();
	}

}
