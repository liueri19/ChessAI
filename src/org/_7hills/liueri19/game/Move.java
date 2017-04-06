package org._7hills.liueri19.game;

import java.util.Arrays;

/**
 * Stores information related to a single move.
 * 
 * @author liueri19
 *
 */
public class Move {
	private final Piece init;
	private final int[] origin, destination;
	
	/**
	 * Constructs a new Move object moving from <code>from</code> to <code>to</code>.
	 * 
	 * @param from	the origin of the move
	 * @param to	the destination of the move
	 */
	public Move(Piece piece, int[] from, int[] to) {
		init = piece;	//a direct reference is desired
		origin = Arrays.copyOf(from, from.length);
		destination = Arrays.copyOf(to, to.length);
	}
	
	public Move(Piece piece, int[] to) {
		this(piece, piece.getSquare(), to);
	}
	
	/**
	 * Returns the Piece initiating the Move.
	 * @return
	 */
	public Piece getPiece() {
		return init;
	}
	
	/**
	 * Returns the origin of the move.
	 * 
	 * @return the origin of the move
	 */
	public int[] getOrigin() {
		return origin;
	}
	
	/**
	 * Returns the destination of the move.
	 * 
	 * @return the destination of the move
	 */
	public int[] getDestination() {
		return destination;
	}
	
	/**
	 * Returns a String representation of this Move object.<br>
	 * <p>
	 * A move will be formated as:<br>
	 * <code>[origin_file][origin_rank][destination_file][destination_rank]</code><br>
	 * A move from E2 to E4 would be represented as:<br>
	 * <code>e2e4</code>
	 */
	@Override
	public String toString() {
		return "" + Board.parseFile(origin[0]) + origin[1] + Board.parseFile(destination[0]) + destination[1];
	}
	
	/**
	 * Indicates whether some other object is "equal to" this Move.<br>
	 * <p>
	 * Returns true if and only if:<br>
	 * the specified object is an instance of Move, and<br>
	 * this Move has the same origin as the specified Move, and<br>
	 * this Move has the same destination as the specified Move, and<br>
	 * this Move has the same initiating Piece as the specified Move.
	 * @param move the Object to be compared with
	 */
	@Override
	public boolean equals(Object move) {
		if (!(move instanceof Move))
			return false;
		else if (Arrays.equals(getOrigin(), ((Move) move).getOrigin()) 
				&& Arrays.equals(getDestination(), ((Move) move).getDestination()))
			return true;
		return false;
	}
	
	/**
	 * Returns a deep copy of this Move object.
	 * This method is effectively the same as calling <code>Move(Piece piece, Piece subject, int[] from, int[] to)</code>
	 * with the same values from this object.
	 * 
	 * @return a deep copy of this Move object
	 */
	public Move copy() {
		return new Move(getPiece(), getOrigin(), getDestination());
	}
}
