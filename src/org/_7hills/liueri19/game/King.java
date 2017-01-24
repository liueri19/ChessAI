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

}
