package model.envelope;

import model.game.entities.Field;

public class BoardEnvelope {
	public Field[][] board;

	public BoardEnvelope(Field[][] board) {
		this.board = board;
	}
}
