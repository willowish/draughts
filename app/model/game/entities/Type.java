package model.game.entities;

public enum Type {
	PAWN(1), QUEEN(2);

	private int value;

	private Type(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
