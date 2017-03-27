package org._7hills.liueri19.game;

import java.util.Arrays;

/**
 * Stores information related to a single move.<br>
 * Move objects are immutable.
 * 
 * @author liueri19
 *
 */
public class Move {
	private final Piece piece;	//the mover
	private final Piece subject;	//may be null
	private final int[] origin, destination;
	
	/**
	 * Constructs a new Move object with <code>piece</code> as the initiator, moving from <code>from</code> to <code>to</code>.
	 * 
	 * @param piece	the initiator of the move
	 * @param subject	the piece taken by the initiator, maybe null if no piece is taken
	 * @param from	the origin of the move
	 * @param to	the destination of the move
	 */
	public Move(Piece piece, Piece subject, int[] from, int[] to) {
		this.piece = piece.copy();
		this.subject = subject == null ? null : subject.copy();
		origin = Arrays.copyOf(from, from.length);
		destination = Arrays.copyOf(to, to.length);
	}
	
	/**
	 * Constructs a new Move object with <code>piece</code> as the initiator, 
	 * moving from the current location of <code>piece</code> to <code>to</code>.
	 * 
	 * @param piece	the initiator of the move
	 * @param subject	the piece taken by the initiator, maybe null if no piece is taken
	 * @param to	the destination of the move
	 */
	public Move(Piece piece, Piece subject, int[] to) {
		this(piece, subject, Arrays.copyOf(piece.getSquare(), piece.getSquare().length), to);
	}
	
	/**
	 * Constructs a new Move object with <code>piece</code> as the initiator, moving from <code>from</code> to <code>to</code>. 
	 * Note that this constructor assumes no piece is taken.
	 * 
	 * @param piece	the initiator of the move
	 * @param from	the origin of the move
	 * @param to	the destination of the move
	 */
	public Move(Piece piece, int[] from, int[] to) {
		this(piece, null, from, to);
	}
	
	/**
	 * Constructs a new Move object with <code>piece</code> as the initiator, 
	 * moving from the current location of <code>piece</code> to <code>to</code>. 
	 * Note that this constructor assumes no piece is taken.
	 * 
	 * @param piece	the initiator of the move
	 * @param to	the destination of the move
	 */
	public Move(Piece piece, int[] to) {
		this(piece, null, Arrays.copyOf(piece.getSquare(), piece.getSquare().length), to);
	}
	
	/**
	 * Returns the initiator of the move.
	 * 
	 * @return the Piece object been moved
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Returns the piece been taken during the move.
	 * 
	 * @return the Piece object been taken during the move
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
	 * <code>[initiating_piece][origin_file][origin_rank][destination_file][destination_rank]</code><br>
	 * A white Pawn move from E2 to E4 would be represented as:<br>
	 * <code>WPe2e4</code>
	 */
	@Override
	public String toString() {
		return "" + getPiece().toString() + Board.parseFile(origin[0]) + origin[1] + Board.parseFile(destination[0]) + destination[1];
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
				&& Arrays.equals(getDestination(), ((Move) move).getDestination()) 
				&& getPiece().equals(((Move) move).getPiece()))
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
		return new Move(getPiece(), getSubject(), getOrigin(), getDestination());
	}
}
