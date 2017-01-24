package org._7hills.liueri19.game;

public class Bishop extends Piece {

	public Bishop(Color color, int x, int y) {
		super(color, x, y);
		setLegalMoves(generateLegalMoves(getSquare()));
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
			return "WB";
		return "BB";
	}

	@Override
	public int[][] generateLegalMoves(int[] square) {
		
	}
}
