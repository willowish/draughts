package model.game.entities;

import java.util.HashSet;
import java.util.Set;

public class MovesBfs {
	public Board board;

	public Set<MovesBfs> nextSteps;

	public MovesBfs(Board board) {
		this.board = board;
		nextSteps = new HashSet<>();
	}

	public void generateNextStep(Color playerColor) {
		Field[][] fields = board.getFields();
		boolean attackPossible = false;
		// checkk for attack
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece = fields[i][j].piece;
				if (piece == null) {
					continue;
				}
				if (piece.getType() == Type.PAWN) {

					if (piece.getColor() == Color.BLACK && playerColor == Color.BLACK) {
						if (canMove(i + 2, j + 2) && isThere(Color.WHITE, i + 1, j + 1)) {
							saveAttack(i, j, i + 1, j + 1, i + 2, j + 2);
							attackPossible = true;
						}
						if (canMove(i + 2, j - 2) && isThere(Color.WHITE, i + 1, j - 1)) {
							saveAttack(i, j, i + 1, j - 1, i + 2, j - 2);
							attackPossible = true;
						}
					} else if (piece.getColor() == Color.WHITE && playerColor == Color.WHITE) {
						if (canMove(i - 2, j + 2) && isThere(Color.BLACK, i - 1, j + 1)) {
							saveAttack(i, j, i - 1, j + 1, i - 2, j + 2);
							attackPossible = true;
						}
						if (canMove(i - 2, j - 2) && isThere(Color.BLACK, i - 1, j - 1)) {
							saveAttack(i, j, i - 1, j - 1, i - 2, j - 2);
							attackPossible = true;
						}
					}
				} else if (piece.getType() == Type.QUEEN) {
					// TODO move queen
				}
			}
		}

		if (attackPossible) {
			return;
		}
		// check for normal moves
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece = fields[i][j].piece;
				if (piece == null) {
					continue;
				}
				if (piece.getType() == Type.PAWN) {

					if (piece.getColor() == Color.BLACK && playerColor == Color.BLACK) {
						if (canMove(i + 1, j + 1)) {
							saveMove(i, j, i + 1, j + 1);
						}
						if (canMove(i + 1, j - 1)) {
							saveMove(i, j, i + 1, j - 1);
						}
					} else if (piece.getColor() == Color.WHITE && playerColor == Color.WHITE) {
						if (canMove(i - 1, j - 1)) {
							saveMove(i, j, i - 1, j - 1);
						}
						if (canMove(i - 1, j + 1)) {
							saveMove(i, j, i - 1, j + 1);
						}
					}
				} else if (piece.getType() == Type.QUEEN) {
					// TODO move queen
				}
			}
		}
	}

	private void saveAttack(int i, int j, int newI, int newJ, int defeatedI, int defeatedJ) {
		Board newBoard = new Board(board.getFields());
		newBoard.fields[newI][newJ].piece = newBoard.fields[i][j].piece;
		newBoard.fields[i][j].piece = null;
		newBoard.fields[defeatedI][defeatedJ].piece = null;
		nextSteps.add(new MovesBfs(newBoard));
	}

	private boolean isThere(Color color, int i, int j) {
		if (i < 0 || i > 7 || j < 0 || j > 7) {
			return false;
		}
		if (board.getFields()[i][j].piece == null) {
			return false;
		}
		return board.getFields()[i][j].piece.getColor() == color;
	}

	private void saveMove(int i, int j, int newI, int newJ) {
		Board newBoard = new Board(board.getFields());
		newBoard.fields[newI][newJ].piece = newBoard.fields[i][j].piece;
		newBoard.fields[i][j].piece = null;
		nextSteps.add(new MovesBfs(newBoard));
	}

	private boolean canMove(int i, int j) {
		if (i < 0 || i > 7 || j < 0 || j > 7) {
			return false;
		}
		return board.getFields()[i][j].piece == null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovesBfs other = (MovesBfs) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else {
			for (int i = 0; i < board.fields.length; i++) {
				for (int j = 0; j < board.fields[i].length; j++) {
					if (board.fields[i][j] != other.board.fields[i][j]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int sum = 31;
		for (int i = 0; i < board.fields.length; i++) {
			for (int j = 0; j < board.fields[i].length; j++) {
				if (board.fields[i][j].piece != null)
					sum += i * 100 + j * 10 + board.fields[i][j].piece.code();
			}
		}
		return sum;
	}
}
