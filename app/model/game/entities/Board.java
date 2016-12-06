package model.game.entities;

import java.util.Arrays;

public class Board {


	private final int DEFAULT_BOARD_SIZE = 10;
	/**
	 * [0][0] is top left corner of the board
	 * **/
	private Field[][] boardFields;

	public Board() {
		new Board(DEFAULT_BOARD_SIZE);
	}
	public Board(int boardSize) {
		this.boardFields = new Field[boardSize][boardSize];
		initBoard();
	}

	public void setPiecesToDefaultFields() {
		clearBoard();

	}

	private void initBoard() {
		setFieldsColors();
//		setPiecesToDefaultFields();
	}

	private void setFieldsColors() {
		Color actualColor = Color.WHITE;
		for (int i = 0; i < boardFields.length; i++) {
			initRow(actualColor, boardFields[i]);
			actualColor = actualColor.getOpposite();
		}
	}

	private void initRow(Color actualColor, Field[] boardField) {
		for (int j = 0; j < boardField.length; j++) {
			boardField[j] = new Field(actualColor);
			actualColor = actualColor.getOpposite();
		}
	}

	private void clearBoard() {
		Arrays.fill(boardFields, null);
	}


}
