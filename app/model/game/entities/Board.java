package model.game.entities;

public class Board {


	private final int DEFAULT_BOARD_SIZE = 10;
	/**
	 * [0][0] is top left corner of the board
	 **/
	private Field[][] boardFields;

	public Board() {
		new Board(DEFAULT_BOARD_SIZE);
	}

	public Board(int boardSize) {
		this.boardFields = new Field[boardSize][boardSize];
		initBoard();
	}

	private void initBoard() {
		setFieldsColors();
		setPiecesToDefaultFields();
	}


	private void setFieldsColors() {
		Color actualColor = Color.WHITE;
		for (int i = 0; i < boardFields.length; i++) {
			initRow(actualColor, boardFields[i]);
			actualColor = actualColor.getOpposite();
		}
	}

	private void setPiecesToDefaultFields() {
		clearBoard();
		int startPositionY = 0; // we start at [0][1]
		int startPositionX = 1; // we start at [0][1]
		initPiecesWithGivenColor(startPositionY, startPositionX, Color.BLACK);
		startPositionY = 6;
		startPositionX = 1;
		initPiecesWithGivenColor(startPositionY, startPositionX, Color.WHITE);
	}

	private void initPiecesWithGivenColor(int startPositionY, int startPositionX, Color color) {
		int numberOfRowsToSet = (boardFields.length - 2) / 2 + startPositionY;
		for (int i = startPositionY; i < numberOfRowsToSet; i++) {
			setPiecesInRow(boardFields[i], color, startPositionX);
			startPositionX = startPositionX == 1 ? 0 : 1;
		}
	}

	private void setPiecesInRow(Field[] row, Color color, int startPositionX) {
		for (int i = startPositionX; i < row.length; i += 2) {
			row[i].setPiece(new Piece(color));
		}
	}

	private void initRow(Color actualColor, Field[] boardField) {
		for (int j = 0; j < boardField.length; j++) {
			boardField[j] = new Field(actualColor);
			actualColor = actualColor.getOpposite();
		}
	}

	private void clearBoard() {
		for (Field[] boardField : boardFields) {
			for (Field field : boardField) {
				field.setPiece(null);
			}
		}
	}


}
