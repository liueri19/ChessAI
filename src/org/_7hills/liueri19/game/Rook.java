package org._7hills.liueri19.game;

public class Rook extends Piece {

	public Rook(Color color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WR";
		return "BR";
	}
}
