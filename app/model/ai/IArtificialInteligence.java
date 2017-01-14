package model.ai;

import model.game.entities.Board;

public interface IArtificialInteligence {
	public double evaluate(Board board);
}
