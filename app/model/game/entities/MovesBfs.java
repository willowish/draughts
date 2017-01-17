package model.game.entities;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovesBfs {
	public Board board;

	public Set<MovesBfs> nextMoves;
	static int[][] allPossibleDirections = new int[][] { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };
	static int[][] whiteDirections = new int[][] { { -1, 1 }, { -1, -1 } };
	static int[][] blackDirections = new int[][] { { 1, 1 }, { 1, -1 } };

	private Map<Color, int[][]> directions;

	private Color actualColor;

	public MovesBfs(Board board, Color actualColor) {
		this.board = board;
		directions = new EnumMap<>(Color.class);
		directions.put(Color.WHITE, whiteDirections);
		directions.put(Color.BLACK, blackDirections);
		this.actualColor = actualColor;
	}

	public void generateNextStep() {
		if (nextMoves != null) {
			return;
		}
		nextMoves = new HashSet<>();

		boolean attackPossible = savePossibleAttacks(actualColor);

		if (attackPossible) {
			return;
		}

		savePossibleMoves(actualColor);
	}

	private void savePossibleMoves(Color playerColor) {
		int[][] playersDirections = directions.get(playerColor);
		Field[][] fields = board.fields;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece = fields[i][j].piece;
				if (piece == null || piece.getColor() != playerColor) {
					continue;
				}
				if (piece.getType() == Type.PAWN) {
					for (int[] direction : playersDirections) {
						if (canMove(i + direction[0], j + direction[1])) {
							saveMove(i, j, direction[0], direction[1]);
						}
					}
				} else if (piece.getType() == Type.QUEEN) {
					for (int[] direction : allPossibleDirections) {
						for (int positionX = i, positionY = j; isCoordInBoard(positionX, positionY)
								&& !isThere(actualColor.getOpposite(), positionX,
										positionY); positionX += direction[0], positionY += direction[1]) {
							if (canMove(positionX, positionY)) {
								saveMove(i, j, positionX - i, positionY - j);
							}
						}
					}
				}
			}
		}
	}

	private boolean savePossibleAttacks(Color playerColor) {
		Field[][] fields = board.fields;
		boolean attackPossible = false;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				Piece piece = fields[i][j].piece;
				if (piece == null || piece.getColor() != playerColor) {
					continue;
				}
				if (piece.getType() == Type.PAWN) {
					for (int[] direction : allPossibleDirections) {
						if (canAttack(playerColor, i, j, direction[0], direction[1])) {
							saveAttack(i, j, direction[0], direction[1]);
							attackPossible = true;
						}
					}

				} else if (piece.getType() == Type.QUEEN) {
					for (int[] direction : allPossibleDirections) {
						for (int positionX = i, positionY = j; isCoordInBoard(positionX,
								positionY); positionX += direction[0], positionY += direction[1]) {

							if (canMove(positionX, positionY) && isThere(playerColor.getOpposite(),
									positionX - direction[0], positionY - direction[1])) {
								saveQueenAttack(i, j, positionX, positionY, positionX - direction[0],
										positionY - direction[1]);
								attackPossible = true;
								break;
							}
						}
					}
				}
			}
		}
		return attackPossible;
	}

	private boolean isCoordInBoard(int positionX, int positionY) {
		return positionX >= 0 && positionX <= 7 && positionY >= 0 && positionY <= 7;
	}

	private boolean canAttack(Color playerColor, int i, int j, int iDelta, int jDelta) {
		return canMove(i + iDelta * 2, j + jDelta * 2) && isThere(playerColor.getOpposite(), i + iDelta, j + jDelta);
	}

	private void saveAttack(int i, int j, int directionI, int directionJ) {
		Board newBoard = getBoardWithMovedPawn(i, j, i + directionI * 2, j + directionJ * 2);
		newBoard.fields[i + directionI][j + directionJ].piece = null;
		nextMoves.add(new MovesBfs(newBoard, actualColor.getOpposite()));
	}

	private void saveQueenAttack(int startX, int startY, int endX, int endY, int beatenX, int beatenY) {
		Board newBoard = getBoardWithMovedPawn(startX, startY, endX, endY);
		newBoard.fields[beatenX][beatenY].piece = null;
		nextMoves.add(new MovesBfs(newBoard, actualColor.getOpposite()));
	}

	private void saveMove(int i, int j, int directionI, int directionJ) {
		Board newBoard = getBoardWithMovedPawn(i, j, i + directionI, j + directionJ);
		nextMoves.add(new MovesBfs(newBoard, actualColor.getOpposite()));
	}

	private Board getBoardWithMovedPawn(int i, int j, int newI, int newJ) {
		Board newBoard = new Board(board.getFields());
		newBoard.fields[newI][newJ].piece = newBoard.fields[i][j].piece;
		if (newI == 0 || newI == 7) {
			newBoard.fields[newI][newJ].piece.promote();
		}
		newBoard.fields[i][j].piece = null;
		return newBoard;
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

	private boolean canMove(int i, int j) {
		if (i < 0 || i > 7 || j < 0 || j > 7) {
			return false;
		}
		return board.getFields()[i][j].piece == null;
	}

	public Color getActualColor() {
		return actualColor;
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
