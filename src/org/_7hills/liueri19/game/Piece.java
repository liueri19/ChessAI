package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Piece class is the super class of all pieces and defines most general methods.
 * @author liueri19
 *
 */
abstract class Piece implements Comparable<Piece> {
	private final boolean color;	//color the color of the piece. true for white, false for black
	private final Board board;
	private int[] coordinate;
	private List<Move> legalMoves = new ArrayList<>();
	private List<Move> threats = new ArrayList<>();
	
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
	 * Constructs a new Piece object. This is the super constructor for all Piece subclasses.
	 * To construct a Piece object, use the constructor of the desired subclass.
	 * @param board	the Board to set the piece on
	 * @param color	the color of the Piece, true for white, false for black
	 * @param coordinate	the location to set the piece at
	 */
	public Piece(Board board, boolean color, int[] coordinate) {
		this(board, color, coordinate[0], coordinate[1]);
	}

	/**
	 * Returns the Board object where this Piece is on.
	 * 
	 * @return the Board object where this Piece is on
	 */
	Board getBoard() {
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
	int[] getSquare() {
		return coordinate;
	}
	
	/**
	 * Change the current location of this Piece to the specified square.
	 * 
	 * @param square the new location to set the Piece on
	 */
	void setSquare(int[] square) {
		coordinate = square;
	}
	
	/**
	 * Change the current location of this Piece to the specified square.
	 * 
	 * @param file	the file of the new location
	 * @param rank	the rank of the new location
	 */
	void setSquare(int file, int rank) {
		coordinate[0] = file;
		coordinate[1] = rank;
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
	List<Move> getLegalMoves() {
		return legalMoves;
	}
	
	/**
	 * Change the current List of legal moves to the specified List.
	 * 
	 * @param moves	the new List of legal moves
	 */
	void setLegalMoves(List<Move> moves) {
		legalMoves = moves;
	}
	
	/**
	 * Clear the list of legal moves. All moves are considered illegal after a call to this method.
	 */
	void clearLegalMoves() {
		legalMoves.clear();
	}

	/**
	 * Add a Move to the existing list of legal moves.
	 * 
	 * @param move the Move to add to the legal moves
	 */
	void addLegalMove(Move move) {
		legalMoves.add(move);
	}

	/**
	 * Add the specified move to threats. Perform a final legal move check to test if King becomes exposed to
	 * threat after the specified move. Add move to legal moves if passes the test.
	 * @param move	the Move to be checked
	 * @param threatsOnly	true to update only threats, false to update threats and legal moves
	 */
	final void checkMove(Move move, boolean threatsOnly) {
		if (!(move.getInit() instanceof Pawn) || move.getSubject() != null)	//don't add regular pawn move
			addThreat(move);
		/*
		Although should be fixed, this does not cause pawns to check kings in front.
		Move is executed first, then pieces are updated (updating the pawn's threats),
		finally checking is king in check. A pawn with a piece in front does not attempt
		to check a forward move.
		 */
		if (!threatsOnly) {
			//if move is not promotion OR (if it is promotion) getPromoteTo() is not null
			if (!(move instanceof Promotion) || ((Promotion) move).getPromoteTo() != null) {
				move.execute(board);
				board.updatePieces(true);
				if (!board.isInCheck(getColor()))
					addLegalMove(move);
				move.revert(board);
			}
			else {
				Promotion promotion = (Promotion) move;
				for (PieceType type : PieceType.PROMOTABLES) {
					promotion.setPromoteTo(type);
					checkMove(promotion, false);
				}
			}
		}
	}
	
	/**
	 * Returns true if the specified Move is legal (in the list of legal moves), and false otherwise.
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
	 * Returns a List of Move objects representing the threats this Piece is posing.
	 * @return a List of Move objects representing the threats this Piece is posing
	 */
	List<Move> getThreats() {
		return threats;
	}
	
	/**
	 * Change the current List of threats to the specified List.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>setLegalMoves()</code>.
	 * @param moves the new List of threats
	 */
	void setThreats(List<Move> moves) {
		threats = moves;
	}
	
	/**
	 * Clear the list of threats.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>clearLegalMoves()</code>.
	 */
	void clearThreats() {
		threats.clear();
	}
	
	/**
	 * Add a Move to the existing list of threats.<br>
	 * For pieces other than King and Pawn, this method is equivalent to <code>checkMove()</code>.
	 * @param move the new Move to add to the threats
	 */
	void addThreat(Move move) {
		threats.add(move);
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
	
	/**
	 * Update the legal moves and threatened squares of this Piece using its current location.
	 * @param threatsOnly true to update only threats, false to update threats and legal moves
	 */
	abstract void updatePiece(boolean threatsOnly);
	
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
	 * A Piece will be formatted as: <code>[color][type]@[file][rank]</code><br>
	 * A white Knight at B1 would be represented as <code>WN@B1</code>,
	 * a black King at e8 would be represented as <code>BK@E8</code>.
	 * @return a String representation of this Piece object
	 */
	public abstract String toString();

	/**
	 * Returns a brief String representation of this Piece. This method uses the same format as toString() but
	 * without specifying the location of the Piece.<br>
	 * A Piece will be formatted as: <code>[color][type]</code>
	 * @return a brief String representation of this Piece
	 */
	public abstract String toBriefString();
	
	/**
	 * Compares this Piece to the specified Piece based on their location on the board. The Piece with
	 * a lower rank is considered greater; if two Piece objects have the same rank, the Piece with a
	 * greater file is considered greater. Piece objects on the same location are considered equal.
	 * @param piece the Piece to be compared
	 * @return -1, 0, or 1 as this Piece is less than, equal to, or greater than the specified Piece.
	 */
	@Override
	public int compareTo(Piece piece) {
		if (getRank() > piece.getRank())
			return -1;
		else if (getRank() < piece.getRank())
			return 1;
		else {
			if (getFile() < piece.getFile())
				return -1;
			else if (getFile() > piece.getFile())
				return 1;
			return 0;
		}
	}

	/**
	 * Compares the location of this Piece to the specified square. This method has the same behavior as compareTo(Piece piece).
	 * @param square	the square to be compared with
	 * @return -1, 0, or 1 as this Piece's location is less than, equal to, or greater than the specified square.
	 */
	@Deprecated
	public int compareToSquare(int[] square) {
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
		if (o instanceof Piece) {
			Piece piece = (Piece) o;
			if (getColor() == piece.getColor()
					&& getBoard() == piece.getBoard()	//the board reference should be exactly the same
					&& Arrays.equals(getSquare(), piece.getSquare()))
				return true;
		}
		return false;
	}

	/**
	 *
	 * @return	hash code value of this object.
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

	/**
	 * This enum represents all piece types and provides the short representations of each piece type.<br>
	 * K for King;<br>
	 * Q for Queen;<br>
	 * R for Rook;<br>
	 * B for Bishop;<br>
	 * N for Knight;<br>
	 * P for Pawn.
	 */
	public enum PieceType {
		KNIGHT	('N'),
		PAWN	('P'),
		QUEEN	('Q'),
		ROOK	('R'),
		BISHOP	('B'),
		KING	('K');

		private char charRep;
		/** List of PieceTypes that can be promoted to, containing QUEEN, KNIGHT, ROOK and BISHOP. */
		public static final List<PieceType> PROMOTABLES =
				Collections.unmodifiableList(Arrays.asList(QUEEN, KNIGHT, ROOK, BISHOP));

		PieceType(char charRep) {
			this.charRep = charRep;
		}

		/**
		 * Return the character representation of the corresponding piece type.
		 * @return the character representation of the specified piece type
		 */
		char getCharRep() { return charRep; }

		/**
		 * Return an instance of PieceType corresponding to the specified character representation.
		 * @param charRep	the short representation of a piece
		 * @return the corresponding PieceType instance
		 */
		static PieceType getInstance(char charRep) {
			switch (charRep) {
				case 'Q': return QUEEN;
				case 'N': return KNIGHT;
				case 'R': return ROOK;
				case 'B': return BISHOP;
				case 'P': return PAWN;
				case 'K': return KING;
				default:
					throw new IllegalArgumentException();
			}
		}
	}
}
