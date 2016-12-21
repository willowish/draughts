package model.game.entities;

public class Field {
	public Piece piece;

	@Override
	public String toString() {
		if (piece == null) {
			return "\t|";
		}
		return piece.toString() + "\t|";
	}
}
