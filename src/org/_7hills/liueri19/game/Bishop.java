package org._7hills.liueri19.game;

public class Bishop extends Piece {

	public Bishop(Board board, Color color, int x, int y) {
		super(board, color, x, y);
		setLegalMoves(generateLegalMoves(getSquare()));
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WB";
		return "BB";
	}

	@Override
	public int[][] generateLegalMoves(int[] square) {
		
	}
}
