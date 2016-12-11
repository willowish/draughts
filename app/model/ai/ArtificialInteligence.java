package model.ai;

import static model.genetics.Configuration.nodesInInputLayer;

import model.ai.alphabeta.AlphaBetaCutoff;
import model.ai.nn.NeuralNetwork;
import model.game.entities.Board;
import model.game.entities.Field;
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
	public double evaluate(Board board) {
		// TODO check correctness
		double[] input = new double[nodesInInputLayer];
		int idx = 0;

		Field[][] fields = board.getBoardFields();
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j += 2) {
				input[idx++] = fields[i][j].getColor().ordinal() + fields[i][j + 1].getColor().ordinal();
			}
		}
		return nn.compute(input);
	}

	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}
}
