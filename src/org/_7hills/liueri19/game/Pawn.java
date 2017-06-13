package org._7hills.liueri19.game;

/**
 * Represents a Pawn. This class overrides certain methods in Piece.
 * @author liueri19
 *
 */
public class Pawn extends Piece {

	public Pawn(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		String result;
		if (this.getColor())
			result = "WP@";
		else
			result = "BP@";
		result += getFile();
		result += getRank();
		return result;
	}

	@Override
	public String toBriefString() {
		if (getColor())
			return "WP";
		return "BP";
	}

	@Override
	void updatePiece(boolean threatsOnly) {
		if (!threatsOnly)
			clearLegalMoves();
		clearThreats();
		int[] square = getSquare();
		Piece target;
		if (this.getColor()) {
			if (getBoard().getPieceAt(square[0], square[1] + 1) == null) {	//white pawn, moving up
				checkMove(new Move(this, square, new int[] {square[0], square[1] + 1}), threatsOnly);
				if (square[1] == 2 && getBoard().getPieceAt(square[0], square[1] + 2) == null)	//on the second rank
					checkMove(new Move(this, square, new int[] {square[0], square[1] + 2}), threatsOnly);
			}
			if ((target = getBoard().getPieceAt(square[0] - 1, square[1] + 1)) != null &&
					target.getColor() != this.getColor())
				checkMove(new Move(this, target, square, new int[] {square[0] - 1, square[1] + 1}), threatsOnly);
			if ((target = getBoard().getPieceAt(square[0] + 1, square[1] + 1)) != null &&
					target.getColor() != this.getColor())
				checkMove(new Move(this, target, square, new int[] {square[0] + 1, square[1] + 1}), threatsOnly);
			/*
			 * if (last move == file left || file right && is pawn move)
			 *   add en passant;
			 */
			if (this.getRank() == 5) {
				Move lastMove = getBoard().getMove(getBoard().getCurrentMoveNum() - 1);
				if (lastMove.getInit() instanceof Pawn && lastMove.getOrigin()[1] - lastMove.getDestination()[1] == 2) {
					int lastMoveFile = lastMove.getOrigin()[0];
					if (lastMoveFile == getFile() -1 || lastMoveFile == getFile() +1) {
						checkMove(new Move(this, lastMove.getInit(), getSquare(),
								new int[] {lastMoveFile, lastMove.getDestination()[1] + 1}), threatsOnly);
					}
				}
			}
		}
		else {
			if (getBoard().getPieceAt(square[0], square[1] - 1) == null) {	//black pawn, moving down
				checkMove(new Move(this, square, new int[] {square[0], square[1] - 1}), threatsOnly);
				if (square[1] == 7 && getBoard().getPieceAt(square[0], square[1] - 2) == null)	//on the second rank
					checkMove(new Move(this, square, new int[] {square[0], square[1] - 2}), threatsOnly);
			}
			if ((target = getBoard().getPieceAt(square[0] - 1, square[1] - 1)) != null &&
					target.getColor() != this.getColor())
				checkMove(new Move(this, target, square, new int[] {square[0] - 1, square[1] - 1}), threatsOnly);
			if ((target = getBoard().getPieceAt(square[0] + 1, square[1] - 1)) != null &&
					target.getColor() != this.getColor())
				checkMove(new Move(this, target, square, new int[] {square[0] + 1, square[1] - 1}), threatsOnly);
			if (this.getRank() == 4) {
				Move lastMove = getBoard().getMove(getBoard().getCurrentMoveNum() - 1);
				if (lastMove.getInit() instanceof Pawn && lastMove.getDestination()[1] - lastMove.getOrigin()[1] == 2) {
					int lastMoveFile = lastMove.getOrigin()[0];
					if (lastMoveFile == getFile() -1 || lastMoveFile == getFile() +1) {
						checkMove(new Move(this, lastMove.getInit(), getSquare(),
								new int[] {lastMoveFile, lastMove.getDestination()[1] - 1}), threatsOnly);
					}
				}
			}
		}
	}

	@Override
	protected Piece copy(Board board) {
		Piece p = new Pawn(board, this.getColor(), this.getFile(), this.getRank());
		return p;
	}

}
