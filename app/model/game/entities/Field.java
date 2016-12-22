package model.game.entities;

public class Field {
	public Piece piece;

	public Color color;

	public Field() {}

	public Field(Color color) {
		this.color = color;
	}

	public Field(Color color, Piece piece) {
		this.color = color;
		this.piece = piece;
	}

	@Override
	public String toString() {
		if (piece == null) {
			return "\t|";
		}
		return piece.toString() + "\t|";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
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
		Field other = (Field) obj;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece))
			return false;
		return true;
	}

}
