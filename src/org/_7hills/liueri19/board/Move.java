package org._7hills.liueri19.board;

import java.util.Arrays;

/**
 * This class stores information related to a single move.
 * 
 * @author liueri19
 *
 */
/*
Making this class would require a defensive copy for every piece, and return defensive copies
for getters, which is too expensive for frequent operations.
Returning defensive copies would also require most uses of getters to call getPieceAt() on board,
which is also expensive.
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
		subject = null;
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
	 * A move from E2 to E4 would be represented as: e2e4;<br>
	 * King and queen side castling is: O-O and O-O-O;<br>
	 * A promotion will have an extra character specifying the piece promoted to
	 * (see {@link Piece.PieceType PieceType}).
	 */
	@Override
	public String toString() {
		return "" + Board.parseFile(origin[0]) + origin[1] + Board.parseFile(destination[0]) + destination[1];
	}
	
	/**
	 * Indicates whether some other move is "equal to" this Move. Returns true if
	 * and only if this Move has the same origin and destination of the specified Move.
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
	 * Execute the move represented by this Move object. All subclasses should override this method.
	 * @param board	the board to execute this move on
	 */
	public void execute(Board board) {
		if (subject != null)
			board.removePiece(subject);
		init.setSquare(getDestination());
		board.rearrange(init);
	}

	/**
	 * Undo changes of execute(). All subclasses must implement this method.
	 * @param board	the board to revert this move on
	 */
	public void revert(Board board) {
		Piece init = getInit();
		Piece subject = getSubject();
		init.setSquare(getOrigin());
		if (subject != null)
			board.addPiece(subject);
		board.rearrange(init);
	}
}
