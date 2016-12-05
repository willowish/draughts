package model.ai;

import model.game.entities;

public interface IArtificialInteligence {

	public Move makeMove (Board board);
	public int evaluate (Board board);
}
