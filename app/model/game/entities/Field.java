package model.game.entities;

public class Field {
	private Color color;

	@SuppressWarnings("unused")
	private Piece piece;

	public Field(Color color) {
		this.color = color;
	}

	public Field(Color color, Piece piece) {
		this.color = color;
		this.piece = piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Color getColor() {
		return color;
	}
}
