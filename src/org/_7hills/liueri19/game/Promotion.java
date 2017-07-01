package org._7hills.liueri19.game;

/**
 * This class describes a promotion.
 * @author liueri19
 */
public class Promotion extends Move {
	private Piece.PieceType promoteTo;

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by promoteTo.
	 * @param init	the Pawn being promoted
	 * @param promoteTo	the type of Piece to promote to; can only be Queen, Knight, Rook or Bishop
	 */
	public Promotion(Pawn init, int[] to, Piece.PieceType promoteTo) {
		//this is ridiculously ugly
		super(init, init.getSquare(), to);
		if (promoteTo == Piece.PieceType.KING || promoteTo == Piece.PieceType.PAWN)
			throw new IllegalArgumentException("Can only promote to Queen, Knight, Rook or Bishop");
		this.promoteTo = promoteTo;
	}

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by the specified string.
	 * @param init	the Pawn being promoted
	 * @param charRep	the character representation of the corresponding piece type; can only be 'Q', 'N', 'R' or 'B'
	 */
	public Promotion(Pawn init, int[] to, char charRep) {
		this(init, to, Piece.PieceType.getInstance(charRep));
	}

	/**
	 * Return the PieceType to promote to.
	 * @return	the PieceType to promote to
	 */
	public Piece.PieceType getPromoteTo() {
		return promoteTo;
	}

	/**
	 * Set this Move to promote to the specified PieceType.
	 * @param type	the PieceType to promote to
	 */
	public void setPromoteTo(Piece.PieceType type) {
		if (promoteTo == Piece.PieceType.KING || promoteTo == Piece.PieceType.PAWN)
			throw new IllegalArgumentException("Can only promoteTo to Queen, Knight, Rook or Bishop");
		promoteTo = type;
	}

	@Override
	public void execute(Board board) {
		if (promoteTo == null)
			throw new IllegalStateException();
		if (promoteTo == Piece.PieceType.QUEEN)	//queen is most common for promotion
			board.addPiece(new Queen(board, getInit().getColor(), getDestination()));
		else if (promoteTo == Piece.PieceType.KNIGHT)	//followed by knight if there's an under-promotion
			board.addPiece(new Knight(board, getInit().getColor(), getDestination()));
		else if (promoteTo == Piece.PieceType.ROOK)
			board.addPiece(new Rook(board, getInit().getColor(), getDestination()));
		else if (promoteTo == Piece.PieceType.BISHOP)
			board.addPiece(new Bishop(board, getInit().getColor(), getDestination()));
		board.removePiece(getInit());
	}

	@Override
	public void revert(Board board) {
		board.addPiece(getInit());
		board.removePiece(board.getPieceAt(getDestination()));
	}

	@Override
	public String toString() {
		if (promoteTo == null)
			return super.toString();
		return super.toString() + promoteTo.getCharRep();
	}
}
