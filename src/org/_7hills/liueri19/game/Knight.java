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

	@Override
	public String toString() {
		if (this.getColor())
			return "WN";
		return "BN";
	}

	@Override
	protected void updatePiece(int[] square) {
		this.clearLegalMoves();
		
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
		Piece target;
		for (int[] move : candidates) {
			if (move[0] < 1 || move[1] < 1 || move[0] > 8 || move[1] > 8)
				continue;
//			else if (getBoard().getPieceAt(move[0], move[1]) == null ||
//					getBoard().getPieceAt(move[0], move[1]).getColor() != this.getColor())
//				addLegalMove(move);
			target = getBoard().getPieceAt(move[0], move[1]);
			if (target == null || target.getColor() != this.getColor())
				addLegalMove(new Move(this, square, move));
		}
	}

	@Override
	public Piece copy() {
		Piece p = new Knight(null, this.getColor(), this.getFile(), this.getRank());
//		for (Move move : this.getLegalMoves())
//			p.addLegalMove(move.copy());
		return p;
	}
}
