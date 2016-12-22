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
}
