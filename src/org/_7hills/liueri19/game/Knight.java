package org._7hills.liueri19.game;

/**
 * Represents a Knight. This class overrides certain methods in Piece.
 * @author liueri19
 *
 */
public class Knight extends Piece {

	public Knight(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	public Knight(Board board, boolean color, int[] coordinate) {
		super(board, color, coordinate);
	}

	@Override
	public String toString() {
		String result;
		if (this.getColor())
			result = "WN@";
		else
			result = "BN@";
		result += getFile();
		result += getRank();
		return result;
	}

	@Override
	public String toBriefString() {
		if (getColor())
			return "WN";
		return "BN";
	}

	@Override
	void updatePiece(boolean threatsOnly) {
		if (!threatsOnly)
			clearLegalMoves();
		clearThreats();
		int[] square = getSquare();
		//8 candidate moves
		int[][] candidates = new int[][] {
			new int[] {square[0] -2, square[1] +1},
			new int[] {square[0] -2, square[1] -1},
			new int[] {square[0] -1, square[1] +2},
			new int[] {square[0] -1, square[1] -2},
			new int[] {square[0] +1, square[1] +2},
			new int[] {square[0] +1, square[1] -2},
			new int[] {square[0] +2, square[1] +1},
			new int[] {square[0] +2, square[1] -1}
		};
		//eliminate illegal moves
		Piece subject;
		for (int[] move : candidates) {
			if (move[0] < 1 || move[1] < 1 || move[0] > 8 || move[1] > 8)
				continue;
//			else if (getBoard().getPieceAt(move[0], move[1]) == null ||
//					getBoard().getPieceAt(move[0], move[1]).getColor() != this.getColor())
//				checkMove(move);
			subject = getBoard().getPieceAt(move[0], move[1]);
			if (subject == null || subject.getColor() != this.getColor())
				checkMove(new Move(this, subject, square, move), threatsOnly);
		}
	}

	@Override
	protected Piece copy(Board board) {
		return new Knight(board, this.getColor(), this.getFile(), this.getRank());
	}
}
