package model.game.entities;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovesBfs {
	public byte[][] fields;

	public Set<MovesBfs> nextMoves;
	static int[][] allPossibleDirections = new int[][] { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };
	static int[][] whiteDirections = new int[][] { { -1, 1 }, { -1, -1 } };
	static int[][] blackDirections = new int[][] { { 1, 1 }, { 1, -1 } };

	private Map<Color, int[][]> directions;

	private Color actualColor;

	public MovesBfs(byte[][] fields, Color actualColor) {
		this.fields = fields;
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
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {

				byte piece = fields[i][j];

				if (piece == 0 || isOppositePlayersPiece(playerColor, i, j)) {
					continue;
				}
				if (isPawn(piece)) {// 1 is pawn
					for (int[] direction : playersDirections) {
						if (canMove(i + direction[0], j + direction[1])) {
							saveMove(i, j, direction[0], direction[1]);
						}
					}
				} else if (isQueen(i, j)) {// 2 is queen
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

	private boolean isQueen(int i, int j) {
		return Math.abs(fields[i][j]) == 2;
	}

	private boolean isPawn(int i) {
		return Math.abs(i) == 1;
	}

	private boolean isOppositePlayersPiece(Color playerColor, int i, int j) {
		return (fields[i][j] > 0 && playerColor == Color.WHITE) || (fields[i][j] < 0 && playerColor == Color.BLACK);
	}

	private boolean savePossibleAttacks(Color playerColor) {
		boolean attackPossible = false;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				byte piece = fields[i][j];
				if (fields[i][j] == 0 || isOppositePlayersPiece(playerColor, i, j)) {
					continue;
				}
				if (isPawn(piece)) {
					for (int[] direction : allPossibleDirections) {
						if (canAttack(playerColor, i, j, direction[0], direction[1])) {
							saveAttack(i, j, direction[0], direction[1]);
							attackPossible = true;
						}
					}

				} else if (isQueen(i, j)) {
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
		byte[][] newBoard = getBoardWithMovedPiece(i, j, i + directionI * 2, j + directionJ * 2);
		newBoard[i + directionI][j + directionJ] = 0;
		nextMoves.add(new MovesBfs(newBoard, actualColor.getOpposite()));
	}

	private void saveQueenAttack(int startX, int startY, int endX, int endY, int beatenX, int beatenY) {
		byte[][] newBoard = getBoardWithMovedPiece(startX, startY, endX, endY);
		newBoard[beatenX][beatenY] = 0;
		nextMoves.add(new MovesBfs(newBoard, actualColor.getOpposite()));
	}

	private void saveMove(int i, int j, int directionI, int directionJ) {
		byte[][] newBoard = getBoardWithMovedPiece(i, j, i + directionI, j + directionJ);
		nextMoves.add(new MovesBfs(newBoard, actualColor.getOpposite()));
	}

	private byte[][] getBoardWithMovedPiece(int i, int j, int newI, int newJ) {
		byte[][] newBoard = copyFields(fields);
		newBoard[newI][newJ] = newBoard[i][j];
		if ((newI == 0 || newI == 7) && isPawn(newBoard[newI][newJ])) {
			newBoard[newI][newJ] *= 2;
		}
		newBoard[i][j] = 0;
		return newBoard;
	}

	private boolean isThere(Color color, int i, int j) {
		if (i < 0 || i > 7 || j < 0 || j > 7) {
			return false;
		}
		if (fields[i][j] == 0) {
			return false;
		}
		return fields[i][j] > 0 && color == Color.WHITE;
	}

	private boolean canMove(int i, int j) {
		if (!isCoordInBoard(i, j)) {
			return false;
		}
		return fields[i][j] == 0;
	}

	public Color getActualColor() {
		return actualColor;
	}

	private byte[][] copyFields(byte[][] fields) {
		byte[][] newFields = new byte[fields.length][];
		for (int i = 0; i < fields.length; i++) {
			byte[] aMatrix = fields[i];
			int aLength = aMatrix.length;
			newFields[i] = new byte[aLength];
			System.arraycopy(aMatrix, 0, newFields[i], 0, aLength);
		}
		return newFields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(fields);
		return result;
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
		if (!Arrays.deepEquals(fields, other.fields))
			return false;
		return true;
	}

}
