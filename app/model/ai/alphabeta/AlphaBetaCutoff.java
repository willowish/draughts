package model.ai.alphabeta;

import model.ai.nn.NeuralNetwork;

public class AlphaBetaCutoff {

	NeuralNetwork nn;
	
	public AlphaBetaCutoff (NeuralNetwork nn) {
		this.nn = nn;
	}
}
