package controllers;

import model.envelope.BoardEnvelope;
import model.game.entities.Field;

public class WinEnvelope extends BoardEnvelope {
	public WinEnvelope(Field[][] board) {
		super(board);
	}

	public boolean win = true;
}
