package org._7hills.liueri19.game;

public class Pawn extends Piece {

	public Pawn(Color color, int x, int y) {
		super(color, x, y);
		
	}

	@Override
	public boolean move(int x, int y) {
		int[] move = new int[] {x, y};
		if (isLegalMove(move)) {
			setSquare(move);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WP";
		return "BP";
	}

	@Override
	public int[][] generateLegalMoves(int[] square) {
		// TODO Auto-generated method stub
		return null;
	}

}
