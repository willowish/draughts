package model.game;


import model.game.entities.Board;

public class Game {

	private Board board;

	public Game() {
		this.board = new Board();
	}

	public Game(int boardSize) {
		this.board = new Board(boardSize);
	}

	public void initGame() {
		board.setPiecesToDefaultFields();
	}

}
