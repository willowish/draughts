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

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece = fields[i][j].piece;
				// TODO spr najpierw czy jest bicie wymuszone gdzies
				if (piece == null) {
					continue;
				}
				if (piece.getType() == Type.PAWN) {
					if (piece.getColor() == Color.BLACK && playerColor == Color.BLACK) {
						if (canMove(i + 1, j + 1)) {
							saveMove(i, j, i + 1, j + 1);
						}
						if (canMove(i - 1, j + 1)) {
							saveMove(i, j, i - 1, j + 1);
						}
					} else if (piece.getColor() == Color.WHITE && playerColor == Color.WHITE) {
						if (canMove(i + 1, j - 1)) {
							saveMove(i, j, i + 1, j - 1);
						}
						if (canMove(i - 1, j - 1)) {
							saveMove(i, j, i - 1, j - 1);
						}
					}
				} else if (piece.getType() == Type.QUEEN) {
					// TODO move queen
				}
			}
		}
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
