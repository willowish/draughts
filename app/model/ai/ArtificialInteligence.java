package model.ai;

import model.ai.alphabeta.AlphaBetaCutoff;
import model.ai.nn.NeuralNetwork;
import model.game.entities.Board;
import model.game.entities.Move;

public class ArtificialInteligence implements IArtificialInteligence {

	NeuralNetwork nn;
	AlphaBetaCutoff ab;

	public ArtificialInteligence() {
		nn = new NeuralNetwork();
		ab = new AlphaBetaCutoff(nn);
	}

	public ArtificialInteligence(NeuralNetwork nn) {
		this.nn = nn;
		ab = new AlphaBetaCutoff(nn);
	}

	@Override
	public Move makeMove(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int evaluate(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}
}
