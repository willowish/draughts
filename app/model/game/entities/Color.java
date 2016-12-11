package model.game.entities;

public enum Color {
	WHITE, BLACK;

	public Color getOpposite() {
		if (this == WHITE)
			return BLACK;
		return WHITE;
	}
}
