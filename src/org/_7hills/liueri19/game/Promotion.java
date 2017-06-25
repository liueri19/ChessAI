package org._7hills.liueri19.game;

/**
 * This class describes a promotion.
 * @author liueri19
 */
public class Promotion extends Move {
	private Piece.Pieces promote;

	public Promotion(Pawn init, Piece.Pieces promoteTo) {
		//this is ridiculously ugly
		super(init, init.getSquare(),
				new int[] {init.getFile(), init.getColor() ? init.getRank()+1 : init.getRank()-1});
		promote = promoteTo;
	}

	@Override
	public void execute(Board board) {
		if (promote == Piece.Pieces.QUEEN)	//queen is most common for promotion
			board.addPiece(new Queen(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.Pieces.KNIGHT)	//followed by knight if there's an under-promotion
			board.addPiece(new Knight(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.Pieces.ROOK)
			board.addPiece(new Rook(board, getInit().getColor(), getDestination()));
		else if (promote == Piece.Pieces.BISHOP)
			board.addPiece(new Bishop(board, getInit().getColor(), getDestination()));
		else
			throw new IllegalArgumentException("Can only promote to Queen, Knight, Rook or Bishop");
		board.removePiece(getInit());
	}
}
