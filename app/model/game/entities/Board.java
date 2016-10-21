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
		//TODO: finish that
	}

	private void clearBoard() {
		for (Field[] boardField : boardFields) {

		}
		Arrays.fill(boardFields, null);
	}


}
