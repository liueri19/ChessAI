package org._7hills.liueri19.game;

import java.util.Arrays;

/**
 * This class stores information related to a single move.
 * 
 * @author liueri19
 *
 */
public class Move {
	private final Piece init, subject;
	private final int[] origin, destination;
	
	/**
	 * Constructs a new Move object moving Piece <code>init</code> from <code>from</code> to <code>to</code>,
	 * taking <code>subject</code>.<br>
	 * Note that this constructor attempts to find a <code>subject</code> based on <code>to</code> if passed
	 * in null.
	 * @param init    the initiating Piece
	 * @param subject    the Piece taken, may be null
	 * @param from    the origin of the move
	 * @param to    the destination of the move
	 */
	public Move(Piece init, Piece subject, int[] from, int[] to) {
		this.init = init;	//direct references are desired
		if (subject == null)
			this.subject = init.getBoard().getPieceAt(to);
		else
			this.subject = subject;
		origin = Arrays.copyOf(from, from.length);
		destination = Arrays.copyOf(to, to.length);
	}

	/**
	 * Constructs a new Move object moving Piece <code>init</code> from <code>from</code> to <code>to</code>,
	 * taking no Piece.<br>
	 * Note that this constructor assumes no piece is taken and does not attempt to find a <code>subject</code>.
	 * <code>subject</code> will be set to null.
	 * @param init	the initiating Piece
	 * @param from	the origin of the move
	 * @param to	the destination of the move
	 */
	public Move(Piece init, int[] from, int[] to) {
		this.init = init;
		subject = init.getBoard().getPieceAt(to);
		origin = Arrays.copyOf(from, from.length);
		destination = Arrays.copyOf(to, to.length);
	}

	/**
	 * Returns the Piece initiating the Move.
	 * @return the Piece initiating the Move
	 */
	public Piece getInit() {
		return init;
	}

	/**
	 * Returns the Piece taken during the Move, may be null.
	 * @return the Piece taken during the Move
	 */
	public Piece getSubject() {
		return subject;
	}

	/**
	 * Returns the origin of the move.
	 * 
	 * @return the origin of the move
	 */
	public int[] getOrigin() {
		return Arrays.copyOf(origin, origin.length);
	}
	
	/**
	 * Returns the destination of the move.
	 * 
	 * @return the destination of the move
	 */
	public int[] getDestination() {
		return Arrays.copyOf(destination, destination.length);
	}
	
	/**
	 * Returns a String representation of this Move object.<br>
	 * <p>
	 * A move will be formatted as:<br>
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
	 * this Move has the same destination as the specified Move.
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
	 * Execute the move represented by this Move object.
	 */
	public void execute(Board board) {
		if (subject != null)
			board.removePiece(subject);
		init.setSquare(getDestination());
	}

//	/**
//	 * Returns a deep copy of this Move object.
//	 * This method is effectively the same as calling <code>Move(Piece piece, Piece subject, int[] from, int[] to)</code>
//	 * with the same values from this object.
//	 *
//	 * @return a deep copy of this Move object
//	 */
//	public Move copy() {
//		return new Move(getPiece().copy(null), getOrigin(), getDestination());
//	}
}
