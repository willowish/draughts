package model.game.entities;

import static model.game.entities.Type.QUEEN;

public class Piece {
	private final Color color;
	private Type type;

	public Piece(Color color, Type type) {
		this.color = color;
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public Type getType() {
		return type;
	}

	public int code() {
		return color.getValue() * type.getValue();
	}

	public void promote() {
		type = QUEEN;
	}

	@Override
	public String toString() {
		return String.valueOf(code());
	}
}
