package model.envelope;

import model.game.entities.Field;

public class BoardEnvelope {
	public Field[][] fields;

	public BoardEnvelope() {}

	public BoardEnvelope(Field[][] board) {
		this.fields = board;
	}
}
