package model.ai;

import model.envelope.BoardEnvelope;
import model.game.Game;
import model.game.Player;
import model.game.entities.MovesBfs;

public class ArtificialPlayer implements Player {

	private ArtificialInteligence ai;

	public ArtificialPlayer() {
		ai = new ArtificialInteligence();
	}

	@Override
	public void proceed(Game game) {
		game.getMovesBfs().generateNextStep();

		Double maxVal = Double.NEGATIVE_INFINITY;
		MovesBfs bestMove = null;
		for (MovesBfs move : game.getMovesBfs().nextMoves) {
			Double evaluation = ai.evaluate(move.board);
			if (evaluation > maxVal) {
				maxVal = evaluation;
				bestMove = move;
			}
		}
		if (bestMove == null) {
			return;
		}

		BoardEnvelope envelope = new BoardEnvelope(bestMove.board.fields);
		game.updateBoard(envelope);
	}

}
