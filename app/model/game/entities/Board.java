package model.game.entities;

import static model.game.entities.Type.PAWN;

public class Board {

	private final static int DEFAULT_BOARD_SIZE = 8;
	/**
	 * [0][0] is top left corner of the board
	 **/
	public Field[][] fields;

	public Board() {
		this(DEFAULT_BOARD_SIZE);
	}

	public Board(int boardSize) {
		this.fields = new Field[boardSize][boardSize];
		setFieldsColors();
		setPiecesToDefaultFields();
	}

	public Board(Field[][] fields) {
		this.fields = new Field[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
		setFieldsColors();
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j].piece != null) {
					this.fields[i][j].piece = new Piece(fields[i][j].piece.getColor(), fields[i][j].piece.getType());
				}
			}
		}
	}

	private void setFieldsColors() {
		Color actualColor = Color.WHITE;
		for (int i = 0; i < fields.length; i++) {
			initRow(actualColor, fields[i]);
			actualColor = actualColor.getOpposite();
		}
	}

	private void setPiecesToDefaultFields() {
		clearBoard();
		int startPositionY = 0; // we start at [0][1]
		int startPositionX = 1; // we start at [0][1]
		initPiecesWithGivenColor(startPositionY, startPositionX, Color.BLACK);
		startPositionY = 5;
		startPositionX = 0;
		initPiecesWithGivenColor(startPositionY, startPositionX, Color.WHITE);
	}

	private void initPiecesWithGivenColor(int startPositionY, int startPositionX, Color color) {
		int numberOfRowsToSet = (fields.length - 2) / 2 + startPositionY;
		for (int i = startPositionY; i < numberOfRowsToSet; i++) {
			setPiecesInRow(fields[i], color, startPositionX);
			startPositionX = startPositionX == 1 ? 0 : 1;
		}
	}

	private void setPiecesInRow(Field[] row, Color color, int startPositionX) {
		for (int i = startPositionX; i < row.length; i += 2) {
			row[i].piece = new Piece(color, PAWN);
		}
	}

	private void initRow(Color actualColor, Field[] boardField) {
		for (int j = 0; j < boardField.length; j++) {
			boardField[j] = new Field(actualColor);
			actualColor = actualColor.getOpposite();
		}
	}

	private void clearBoard() {
		for (Field[] boardField : fields) {
			for (Field field : boardField) {
				field.piece = null;
			}
		}
	}

	public Field[][] getFields() {
		return fields;
	}

	public void setFields(Field[][] fields) {
		this.fields = fields;
	}
}
