package org._7hills.liueri19.game;

public class Queen extends Piece {

	public Queen(Board board, Color color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WQ";
		return "BQ";
	}

	@Override
	public void generateLegalMoves(int[] square) {
		// TODO Auto-generated method stub
		
	}
}
