package org._7hills.liueri19.board;

/**
 * This class describes a promotion.
 * @author liueri19
 */
public class Promotion extends Move {
	private Piece.PieceType promoteTo;
	private Piece promotedPiece;

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by promoteTo.
	 * This constructor assumes no piece is taken.
	 * @param init	the Pawn being promoted
	 * @param to	destination of the move
	 * @param promoteTo	the type of Piece to promote to; can only be Queen, Knight, Rook or Bishop
	 */
	public Promotion(Pawn init, int[] to, Piece.PieceType promoteTo) {
		super(init, init.getSquare(), to);
		if (promoteTo == Piece.PieceType.KING || promoteTo == Piece.PieceType.PAWN)
			throw new IllegalArgumentException("Can only promote to Queen, Knight, Rook or Bishop");
		this.promoteTo = promoteTo;
	}

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by promoteTo, taking
	 * {@code subject}. Note that this constructor attempts to find a {@code subject} based on
	 * {@code to} if passed in null.
	 * @param init	the Pawn being promoted
	 * @param subject	the Piece being taken
	 * @param to	destination of the move
	 * @param promoteTo	the type of Piece to promote to; can only be Queen, Knight, Rook or Bishop
	 */
	public Promotion(Pawn init, Piece subject, int[] to, Piece.PieceType promoteTo) {
		super(init, subject, init.getSquare(), to);
		if (promoteTo == Piece.PieceType.KING || promoteTo == Piece.PieceType.PAWN)
			throw new IllegalArgumentException("Can only promote to Queen, Knight, Rook or Bishop");
		this.promoteTo = promoteTo;
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
		Piece subject = getSubject();
		if (subject != null)
			board.removePiece(subject);
		if (promoteTo == Piece.PieceType.QUEEN)	//queen is most common for promotion
			board.addPiece(promotedPiece = new Queen(board, getInit().getColor(), getDestination()));
		else if (promoteTo == Piece.PieceType.KNIGHT)	//followed by knight if there's an under-promotion
			board.addPiece(promotedPiece = new Knight(board, getInit().getColor(), getDestination()));
		else if (promoteTo == Piece.PieceType.ROOK)
			board.addPiece(promotedPiece = new Rook(board, getInit().getColor(), getDestination()));
		else if (promoteTo == Piece.PieceType.BISHOP)
			board.addPiece(promotedPiece = new Bishop(board, getInit().getColor(), getDestination()));
		board.removePiece(getInit());
	}

	@Override
	public void revert(Board board) {
		board.removePiece(promotedPiece);
		board.addPiece(getInit());
		Piece subject = getSubject();
		if (subject != null)
			board.addPiece(getSubject());
	}

	@Override
	public String toString() {
		if (promoteTo == null)
			return super.toString();
		return super.toString() + promoteTo.getCharRep();
	}
}
