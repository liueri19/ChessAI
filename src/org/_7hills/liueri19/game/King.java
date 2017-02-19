package org._7hills.liueri19.game;

public class King extends Piece {

	public King(Board board, Color color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WK";
		return "BK";
	}

	@Override
	public void updateLegalMoves(int[] square) {
		this.clearLegalMoves();
		
		//8 candidate moves
		int[][] candidates = new int[][] {
			new int[] {square[0] -1, square[1] +1},
			new int[] {square[0], square[1] +1},
			new int[] {square[0] +1, square[1] +1},
			new int[] {square[0] -1, square[1]},
			new int[] {square[0] +1, square[1]},
			new int[] {square[0] -1, square[1] -1},
			new int[] {square[0], square[1] -1},
			new int[] {square[0] +1, square[1] -1}
		};
		//eliminate illegal moves
		for (int[] move : candidates) {
			if (move[0] < 1 || move[1] < 1 || move[0] > 8 || move[1] > 8)
				continue;
			else if (!getBoard().isSquareAttacked(this.getColor(), move[0], move[1]) &&
					(getBoard().getPieceAt(move[0], move[1]) == null ||
					getBoard().getPieceAt(move[0], move[1]).getColor() != this.getColor()))
				addLegalMove(move);
		}
	}

}
