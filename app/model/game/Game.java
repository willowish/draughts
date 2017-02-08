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
					Color wonColor = movesBfs.getActualColor().getOpposite();
					throw new WinException(players.get(wonColor));
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
				byte piece = fields[i][j];
				switch (piece) {
				case 0: {
					System.out.print(" ");
					break;
				}
				case 1: {
					System.out.print("O");
					break;
				}
				case -1: {
					System.out.print("X");
					break;
				}
				case 2: {
					System.out.print("B");
					break;
				}
				case -2: {
					System.out.print("R");
					break;
				}
				}
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
