package model.game;

import java.util.Arrays;
import java.util.EnumMap;

import model.envelope.BoardEnvelope;
import model.game.entities.Board;
import model.game.entities.Color;
import model.game.entities.MovesBfs;

public class Game {

	private MovesBfs movesBfs;
	private EnumMap<Color, Player> players;
	private int movesMade;
	private byte[][] fields;

	public Game(Player whitePlayer, Player blackPlayer) {
		Board board = new Board();
		fields = board.toByteArray();
		movesBfs = new MovesBfs(fields, Color.WHITE);
		players = new EnumMap<>(Color.class);
		players.put(Color.WHITE, whitePlayer);
		players.put(Color.BLACK, blackPlayer);
	}

	public void updateBoard(BoardEnvelope envelope) {

		movesBfs.generateNextStep();

		for (MovesBfs nextMove : movesBfs.nextMoves) {
			if (Arrays.deepEquals(envelope.fields, nextMove.fields)) {
				fields = nextMove.fields;
				movesBfs = nextMove;

				checkHistoryForDraw();

				if (noMoreMoves()) {
					throw new WinException(movesBfs.getActualColor().getOpposite());
				}
				return;
			}
		}
		throw new IllegalMoveException();
	}

	private void checkHistoryForDraw() {
		movesMade++;
		if (movesMade >= 100) {
			throw new WinException(null);
		}
	}

	private boolean noMoreMoves() {
		movesBfs.generateNextStep();
		return movesBfs.nextMoves.isEmpty();
	}

	public MovesBfs getMovesBfs() {
		return movesBfs;
	}

	public void printFields() {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				System.out.print(fields[i][j]);
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();
	}

	public void start() {
		while (true) {
			players.get(Color.WHITE).proceed(this);
			players.get(Color.BLACK).proceed(this);
		}
	}

}
