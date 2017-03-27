package org._7hills.liueri19.game;

/**
 * This is the special move for castling.
 * @author liueri19
 *
 */
public class Castling extends Move {
	
	/**
	 * Constructs a new Castling object identifying the move.
	 * 
	 * @param king	the king
	 * @param from	origin of the king
	 * @param to	destination of the king
	 */
	public Castling(King king, int[] from, int[] to) {
		super(king, from, to);
		king.setCastlable(false);
	}
	
	/**
	 * Constructs a new Castling object identifying the move.
	 * This constructor assumes the origin of the move is the current square of the specified King.
	 * 
	 * @param king	the king
	 * @param to	destination of the king
	 */
	public Castling(King king, int[] to) {
		super(king, to);
		king.setCastlable(false);
	}
	
	/**
	 * Returns a string representation of this Castling object.
	 * Only two values could be returned by this method, <code>"0-0"</code> for king side castling;
	 * <code>"0-0-0"</code> for queen side castling. 
	 */
	@Override
	public String toString() {
		if (getDestination()[0] == 3)
			return "0-0-0";
		return "0-0";
	}
}
