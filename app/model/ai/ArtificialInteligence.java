package model.ai;

import static model.genetics.Configuration.*;

import model.ai.alphabeta.AlphaBetaCutoff;
import model.ai.nn.NeuralNetwork;
import model.game.entities.Board;
import model.game.entities.Field;
import model.game.entities.Piece;

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
	public double evaluate(Board board) {
		double[] input = new double[nodesInInputLayer];
		int idx = 0;

		Field[][] fields = board.getFields();
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j += 2) {
				input[idx++] = codeOf(fields[i][j].piece) + codeOf(fields[i][j].piece);
			}
		}
		return nn.compute(input);
	}

	private int codeOf(Piece piece) {
		if (piece == null)
			return 0;
		return piece.code();
	}

	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}
}
