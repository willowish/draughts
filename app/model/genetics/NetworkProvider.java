package model.genetics;

import model.ai.nn.NeuralNetwork;

public class NetworkProvider implements Provider<NeuralNetwork> {

	@Override
	public NeuralNetwork get() {
		return new NeuralNetwork();
	}

}
