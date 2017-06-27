package org._7hills.liueri19.game;

/**
 * This class describes a promotion.
 * @author liueri19
 */
public class Promotion extends Move {
	private Piece.PieceTypes promote;

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by promoteTo.
	 * @param init	the Pawn being promoted
	 * @param promoteTo	the type of Piece to promote to; can only be Queen, Knight, Rook or Bishop
	 */
	public Promotion(Pawn init, Piece.PieceTypes promoteTo) {
		//this is ridiculously ugly
		super(init, init.getSquare(),
				new int[] {init.getFile(), init.getColor() ? init.getRank()+1 : init.getRank()-1});
		if (promoteTo == Piece.PieceTypes.KING || promoteTo == Piece.PieceTypes.PAWN)
			throw new IllegalArgumentException("Can only promote to Queen, Knight, Rook or Bishop");
		promote = promoteTo;
	}

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by the specified string.
	 * @param init	the Pawn being promoted
	 * @param charRep	the character representation of the corresponding piece type; can only be 'Q', 'N', 'R' or 'B'
	 */
	public Promotion(Pawn init, char charRep) {
		this(init, Piece.PieceTypes.getInstance(charRep));
	}

	/**
	 * Construct a Promotion object without specifying the result of the promotion. This constructor should only be
	 * used in the updatePiece() method when not enough information is gathered. Invoking execute() without first
	 * specifying the result of the promotion will throw an IllegalStateException.
	 * @param init	the Pawn being promoted
	 */
	Promotion(Pawn init) {
		super(init, init.getSquare(),
				new int[] {init.getFile(), init.getColor() ? init.getRank()+1 : init.getRank()-1});
	}

	public void setPromoteTo(Piece.PieceTypes promoteTo) {
		promote = promoteTo;
	}

	@Override
	public void execute(Board board) {
		if (promote == null)
			throw new IllegalStateException();
		if (promote == Piece.PieceTypes.QUEEN)	//queen is most common for promotion
			board.addPiece(new Queen(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.PieceTypes.KNIGHT)	//followed by knight if there's an under-promotion
			board.addPiece(new Knight(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.PieceTypes.ROOK)
			board.addPiece(new Rook(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.PieceTypes.BISHOP)
			board.addPiece(new Bishop(board, getInit().getColor(), getDestination()));
		board.removePiece(getInit());
	}

	@Override
	public String toString() {
		return super.toString() + promote.getCharRep();
	}
}
