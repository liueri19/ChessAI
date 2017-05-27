package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Piece class is the super class of all pieces and defines most general methods.
 * @author liueri19
 *
 */
public abstract class Piece implements Comparable<Piece>{
	private boolean color;	//color the color of the piece. true for white, false for black
	private final Board board;
	private int[] coordinate;
	private List<Move> legalMoves = new ArrayList<Move>();
	
	/**
	 * Constructs a new Piece object. This is the super constructor for all Piece subclasses.
	 * To construct a Piece object, use the constructor of the desired subclass.
	 * 
	 * @param board	the Board to set the piece on
	 * @param color	the color of the Piece, true for white, false for black
	 * @param x		the file to set the piece on
	 * @param y		the rank to set the piece on
	 */
	public Piece(Board board, boolean color, int x, int y) {
		this.board = board;
		this.color = color;
		coordinate = new int[] {x, y};
	}
	
	/**
	 * Returns a deep copy of this Piece object with reference to the specified Board.
	 * @param board the Board object to link this Piece to.
	 * 
	 * @return a deep copy of this Piece object
	 */
	public abstract Piece copy(Board board);
	
	/**
	 * Returns the Board object where this Piece is on.
	 * 
	 * @return the Board object where this Piece is on
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the color of this Piece.
	 * 
	 * @return the color of this Piece
	 */
	public boolean getColor() {
		return color;
	}
	
	/**
	 * Returns the current location of this Piece represented as an int array.
	 * 
	 * @return the current location of this Piece
	 */
	public int[] getSquare() {
		return coordinate;
	}
	
	/**
	 * Change the current location of this Piece to the specified square.
	 * 
	 * @param square the new location to set the Piece on
	 */
	protected void setSquare(int[] square) {
		coordinate = square;
	}
	
	/**
	 * Change the current location of this Piece to the specified square.
	 * 
	 * @param file	the file of the new location
	 * @param rank	the rank of the new location
	 */
	protected void setSquare(int file, int rank) {
		setSquare(new int[] {file, rank});
	}
	
	/**
	 * Returns the current file of this Piece.
	 * 
	 * @return the current file of this Piece represented in int
	 */
	public int getFile() {
		return coordinate[0];
	}
	
	/**
	 * Returns the current rank of this Piece.
	 * @return the current rank of this Piece
	 */
	public int getRank() {
		return coordinate[1];
	}
	
	/**
	 * Returns a List of Move objects containing all legal moves.
	 * All moves not on this List is considered illegal.
	 * 
	 * @return a List of Move objects containing all legal moves
	 */
	public List<Move> getLegalMoves() {
		return legalMoves;
	}
	
	/**
	 * Change the current List of legal moves to the specified List.
	 * 
	 * @param moves	the new List of legal moves
	 */
	protected void setLegalMoves(List<Move> moves) {
		legalMoves = moves;
	}
	
	/**
	 * Clear the list of legal moves. All moves are considered illegal after a call to this method.
	 */
	protected void clearLegalMoves() {
		legalMoves.clear();
	}
	
	/**
	 * Add a Move to the existing list of legal moves.
	 * 
	 * @param move the new Move to add to the legal moves
	 */
	protected void addLegalMove(Move move) {
		Board board = new Board(getBoard());
		board.uncheckedMove(move);
		if (!board.isInCheck(getColor()))
			legalMoves.add(move);
	}
	
	/**
	 * Returns true if the specified Move is legal (in the list of legal moves), and false otherwise.方
	 * @param move	the Move to be checked
	 * @return true if the specified Move is legal, and false otherwise.
	 */
	public boolean isLegalMove(Move move) {
		for (Move m : getLegalMoves()) {
			if (move.equals(m))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns a List of Move objects representing the threats this Piece is posing.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>getLegalMoves()</code>.
	 * @return a List of Move objects representing the threats this Piece is posing
	 */
	public List<Move> getThreats() {
		return getLegalMoves();
	}
	
	/**
	 * Change the current List of threats to the specified List.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>setLegalMoves()</code>.
	 * @param moves the new List of threats
	 */
	protected void setThreats(List<Move> moves) {
		setLegalMoves(moves);
	}
	
	/**
	 * Clear the list of threats.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>clearLegalMoves()</code>.
	 */
	protected void clearThreats() {
		clearLegalMoves();
	}
	
	/**
	 * Add a Move to the existing list of threats.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>addLegalMove()</code>.
	 * @param move the new Move to add to the threats
	 */
	protected void addThreat(Move move) {
		addLegalMove(move);
	}
	
	/**
	 * Returns true if the specified Move is threatened, false otherwise.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>isLegalMove()</code>.
	 * @param move	the Move to be checked
	 * @return	true if the specified Move is threatened, false otherwise
	 */
	public boolean isThreatening(Move move) {
		for (Move m : getThreats()) {
			if (move.equals(m))
				return true;
		}
		return false;
	}
	
//	/**
//	 * Move this Piece to the specified location. Returns true if the move succeeds, and false if the move failed.
//	 * @param file	the file to move this Piece to
//	 * @param rank	the rank to move this Piece to
//	 * @return	true if the move succeeds, and false if the move failed
//	 */
//	public boolean move(int file, int rank) {
//		return move(new Move(this, new int[] {file, rank}));
//	}
//	
//	/**
//	 * Move this Piece as described by the Move object. Returns true if the move succeeds, and false if the move failed.
//	 * @param move	the Move to execute
//	 * @return true if the move succeeds, and false if the move failed
//	 */
//	public boolean move(Move move) {
//		if (isLegalMove(move)) {
//			if (move.getSubject() != null)
//				getBoard().removePiece(move.getSubject());
//			setSquare(move.getDestination());
//			return true;
//		}
//		return false;
//	}
	
	/**
	 * Update the legal moves and threatened squares of this Piece with the specified location.
	 * @param square	the square for the legal moves and threatened squares generation algorithms to run on
	 */
	protected abstract void updatePiece(int[] square);
	
	/**
	 * Update the legal moves and threatened squares of this Piece using its current location.
	 */
	protected void updatePiece() {
		updatePiece(this.getSquare());
	}
	
	/**
	 * Returns a String representation of this Piece object.<br>
	 * <p>
	 * The subclasses are represented as:<br>
	 * Pawn - P<br>
	 * Rook - R<br>
	 * Bishop - B<br>
	 * Knight - N<br>
	 * Queen - Q<br>
	 * King - K<br>
	 * <br>
	 * The colors are represented as:<br>
	 * Black - B<br>
	 * White - W<br>
	 * <br>
	 * A Piece will be formated as:<br>
	 * <code>[color][type]</code><br>
	 * A white Knight would be represented as <code>WN</code>,
	 * a black King would be represented as <code>BK</code>.
	 * @return a String representation of this Piece object
	 */
	public abstract String toString();
	
	/**
	 * Compares this Piece to the specified Piece based on their location on the board. The Piece with
	 * a higher rank is considered greater; if two Piece objects have the same rank, the Piece with a
	 * greater file is considered greater. Piece objects on the same location are considered equal.
	 * @param piece the Piece to be compared
	 * @return -1, 0, or 1 as this Piece is less than, equal to, or greater than the specified Piece.
	 */
	@Override
	public int compareTo(Piece piece) {
		if (this.getRank() == piece.getRank()) {
			if (this.getFile() < piece.getFile())
				return -1;
			else if (this.getFile() > piece.getFile())
				return 1;
			return 0;
		}
		if (this.getRank() > piece.getRank())
			return -1;
		return 1;
	}

	/**
	 * Compares the location of this Piece to the specified square. This method has the same behavior as compareTo(Piece piece).
	 * @param square	the square to be compared with
	 * @return -1, 0, or 1 as this Piece's location is less than, equal to, or greater than the specified square.
	 */
	@Deprecated
	protected int compareToSquare(int[] square) {
		if (this.getRank() == square[1]) {
			if (this.getFile() < square[0])
				return -1;
			else if (this.getFile() > square[0])
				return 1;
			return 0;
		}
		if (this.getRank() > square[1])
			return -1;
		return 1;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this Piece.<br>
	 * Returns true if:<br>
	 * the specified Object is of the same class as this Piece, and<br>
	 * the specified Piece has the same color as this Piece, and<br>
	 * the specified Piece has the same Board reference as this Piece, and<br>
	 * both Piece objects have the same location.
	 * @param o	the Object to be compared with
	 * @return true if the specified Object meets all requirements, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this.getClass().equals(o.getClass())) {
			Piece piece = (Piece) o;
			if (this.getColor() == piece.getColor()
					&& this.getBoard() == piece.getBoard()	//the board reference should be exactly the same
					&& Arrays.equals(this.getSquare(), piece.getSquare()))
				return true;
		}
		return false;
	}

	/**
	 *
	 * @return	a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		if (getColor())
			hash++;
		hash += getBoard().hashCode() * 31;
		hash += getFile() * 31;

		return hash;
	}
}
