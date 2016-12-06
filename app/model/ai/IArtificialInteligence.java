package model.ai;

import model.game.entities.Board;
import model.game.entities.Move;

public interface IArtificialInteligence {

	public Move makeMove(Board board);

	public int evaluate(Board board);
}
