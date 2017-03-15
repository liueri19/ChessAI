package org._7hills.liueri19.game;

public class Castling extends Move {
	
	/**
	 * This is the special move for castling.
	 * 
	 * @param king	the king
	 * @param from	origin of the king
	 * @param to	destination of the king
	 */
	public Castling(King king, int[] from, int[] to) {
		super(king, from, to);
		king.setCastlable(false);
	}
	
	public Castling(King king, int[] to) {
		super(king, to);
		king.setCastlable(false);
	}
	
	@Override
	public String toString() {
		if (getDestination()[0] == 3)
			return "0-0-0";
		return "0-0";
	}
}
