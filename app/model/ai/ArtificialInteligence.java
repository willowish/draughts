package model.ai;

import static model.genetics.Configuration.nodesInInputLayer;

import model.ai.alphabeta.AlphaBetaCutoff;
import model.ai.nn.NeuralNetwork;

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
	public double evaluate(byte[][] fields) {
		double[] input = new double[nodesInInputLayer];
		int idx = 0;

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j += 2) {
				input[idx++] = fields[i][j];
			}
		}
		return nn.compute(input);
	}

	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}

}
