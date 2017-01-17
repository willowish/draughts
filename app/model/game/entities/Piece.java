package model.game.entities;

import static model.game.entities.Type.QUEEN;

public class Piece {
	private Color color;
	private Type type;

	public Piece() {
	}

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
		if (type == Type.PAWN) {
			if (color == Color.WHITE)
				return "O";
			else
				return "X";
		} else {
			if (color == Color.WHITE)
				return "Ô";
			else
				return "¥";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (color != other.color)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
