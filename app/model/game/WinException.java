package model.game;

import model.game.entities.Color;

public class WinException extends RuntimeException {
	private static final long serialVersionUID = -3668164963003770568L;
	private Color color;

	public WinException(Color color) {
		this.color = color;
	}

	public Color getWinningColor() {
		return color;
	}

}
