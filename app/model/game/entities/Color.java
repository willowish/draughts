package model.game.entities;

public enum Color {
	WHITE(-1), BLACK(1);

	private int value;

	private Color(int value) {
		this.value = value;
	}

	public Color getOpposite() {
		if (this == WHITE)
			return BLACK;
		return WHITE;
	}

	public int getValue() {
		return value;
	}
}
