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

	public Pawn(Board board, boolean color, int[] coordinate) {
		super(board, color, coordinate);
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
				if (square[1] == 2 && getBoard().getPieceAt(square[0], square[1] + 2) == null)	//on the second rank
					checkMove(new Move(this, square, new int[] {square[0], square[1] + 2}), threatsOnly);
				else if (square[1] == 7)	//on the 7th rank, promotion
					checkMove(new Promotion(this), threatsOnly);
				else
					checkMove(new Move(this, square, new int[] {square[0], square[1] + 1}), threatsOnly);
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
				if (lastMove != null) {
					int[] origin = lastMove.getOrigin();
					int[] des = lastMove.getDestination();
					if (lastMove.getInit() instanceof Pawn && origin[1] - des[1] == 2) {
						if (origin[0] == getFile() - 1 || origin[0] == getFile() + 1) {
							checkMove(new Move(this, lastMove.getInit(), getSquare(),
									new int[] {origin[0], des[1] + 1}), threatsOnly);
						}
					}
				}
			}
		}
		else {
			if (getBoard().getPieceAt(square[0], square[1] - 1) == null) {	//black pawn, moving down
				if (square[1] == 7 && getBoard().getPieceAt(square[0], square[1] - 2) == null)
					checkMove(new Move(this, square, new int[] {square[0], square[1] - 2}), threatsOnly);
				else if (square[1] == 2)
					checkMove(new Promotion(this), threatsOnly);
				else
					checkMove(new Move(this, square, new int[] {square[0], square[1] - 1}), threatsOnly);
			}
			if ((target = getBoard().getPieceAt(square[0] - 1, square[1] - 1)) != null &&
					target.getColor() != this.getColor())
				checkMove(new Move(this, target, square, new int[] {square[0] - 1, square[1] - 1}), threatsOnly);
			if ((target = getBoard().getPieceAt(square[0] + 1, square[1] - 1)) != null &&
					target.getColor() != this.getColor())
				checkMove(new Move(this, target, square, new int[] {square[0] + 1, square[1] - 1}), threatsOnly);
			if (this.getRank() == 4) {
				Move lastMove = getBoard().getMove(getBoard().getCurrentMoveNum() - 1);
				if (lastMove != null) {
					int[] origin = lastMove.getOrigin();
					int[] des = lastMove.getDestination();
					if (lastMove.getInit() instanceof Pawn && des[1] - origin[1] == 2) {
						int lastMoveFile = origin[0];
						if (lastMoveFile == getFile() - 1 || lastMoveFile == getFile() + 1) {
							checkMove(new Move(this, lastMove.getInit(), getSquare(),
									new int[] {lastMoveFile, des[1] - 1}), threatsOnly);
						}
					}
				}
			}
		}
	}

	@Override
	protected Piece copy(Board board) {
		return new Pawn(board, this.getColor(), this.getFile(), this.getRank());
	}

}
