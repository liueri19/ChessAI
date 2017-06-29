package org._7hills.liueri19.game;

/**
 * This class describes a promotion.
 * @author liueri19
 */
public class Promotion extends Move {
	private Piece.PieceType promote;

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by promoteTo.
	 * @param init	the Pawn being promoted
	 * @param promoteTo	the type of Piece to promote to; can only be Queen, Knight, Rook or Bishop
	 */
	public Promotion(Pawn init, Piece.PieceType promoteTo) {
		//this is ridiculously ugly
		super(init, init.getSquare(),
				new int[] {init.getFile(), init.getColor() ? init.getRank()+1 : init.getRank()-1});
		if (promoteTo == Piece.PieceType.KING || promoteTo == Piece.PieceType.PAWN)
			throw new IllegalArgumentException("Can only promote to Queen, Knight, Rook or Bishop");
		promote = promoteTo;
	}

	/**
	 * Construct a Promotion promoting the specified Pawn to a Piece represented by the specified string.
	 * @param init	the Pawn being promoted
	 * @param charRep	the character representation of the corresponding piece type; can only be 'Q', 'N', 'R' or 'B'
	 */
	public Promotion(Pawn init, char charRep) {
		this(init, Piece.PieceType.getInstance(charRep));
	}

	@Override
	public void execute(Board board) {
		if (promote == Piece.PieceType.QUEEN)	//queen is most common for promotion
			board.addPiece(new Queen(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.PieceType.KNIGHT)	//followed by knight if there's an under-promotion
			board.addPiece(new Knight(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.PieceType.ROOK)
			board.addPiece(new Rook(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.PieceType.BISHOP)
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
		if (promote == null)
			return super.toString();
		return super.toString() + promote.getCharRep();
	}
}
