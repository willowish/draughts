package model.game;

import model.game.entities.Board;
import model.game.entities.Field;
import model.game.entities.MovesBfs;

public class Game {

	private Board board;

	public Game() {
		board = new Board();
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public static void main(String[] args) {
		Game game = new Game();
		Field[][] fields = game.board.getFields();

		printFields(fields);

		MovesBfs movesBfs = new MovesBfs(game.board);
		movesBfs.generateNextStep();
		movesBfs.nextSteps.forEach(nextStep -> {
			printFields(nextStep.board.fields);
			nextStep.generateNextStep();

			nextStep.nextSteps.forEach(nextnextStep -> {
				printFields(nextnextStep.board.fields);
			});
		});
	}

	private static void printFields(Field[][] fields) {
		for (int i = 0; i < fields.length; i++) {
			Field[] row = fields[i];
			for (int j = 0; j < row.length; j++) {
				Field field = row[j];
				System.out.print(field);
			}
			System.out.println();
			System.out.println();
		}

		System.out.println();
		System.out.println();
	}
}
