package model.game;

import java.util.Arrays;
import java.util.EnumMap;

import model.envelope.BoardEnvelope;
import model.game.entities.Board;
import model.game.entities.Color;
import model.game.entities.Field;
import model.game.entities.MovesBfs;

public class Game {

	private Board board;
	private MovesBfs movesBfs;
	private EnumMap<Color, Player> players;

	public Game(Player whitePlayer, Player blackPlayer) {
		board = new Board();
		movesBfs = new MovesBfs(board, Color.WHITE);
		players = new EnumMap<>(Color.class);
		players.put(Color.WHITE, whitePlayer);
		players.put(Color.BLACK, blackPlayer);
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public void updateBoard(BoardEnvelope envelope) {

		movesBfs.generateNextStep();

		for (MovesBfs nextMove : movesBfs.nextMoves) {
			if (Arrays.deepEquals(envelope.fields, nextMove.board.fields)) {
				board.setFields(nextMove.board.fields);
				movesBfs = nextMove;

				if (noMoreMoves()) {
					throw new WinException();
				}

				players.get(nextMove.getActualColor().getOpposite()).proceed(this);
				return;
			}
		}
		throw new IllegalMoveException();
	}

	private boolean noMoreMoves() {
		movesBfs.generateNextStep();
		return movesBfs.nextMoves.isEmpty();
	}

	public MovesBfs getMovesBfs() {
		return movesBfs;
	}

	public void printFields() {
		Field[][] fields = board.fields;
		for (int i = 0; i < fields.length; i++) {
			Field[] row = fields[i];
			for (int j = 0; j < row.length; j++) {
				Field field = row[j];
				System.out.print(field);
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();
	}

	public void start() {
		players.get(Color.WHITE).proceed(this);
	}

}
