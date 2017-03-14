package org._7hills.liueri19.game;

public class Pawn extends Piece {

	public Pawn(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor())
			return "WP";
		return "BP";
	}

	@Override
	public void updatePiece(int[] square) {
		this.clearLegalMoves();
		
		if (this.getColor()) {
			if (getBoard().getPieceAt(square[0], square[1] + 1) == null) {	//white pawn, moving up
				addLegalMove(new int[] {square[0], square[1] + 1});
				if (square[1] == 2)	//on the second rank
					addLegalMove(new int[] {square[0], square[1] + 2});
			}
			if (getBoard().getPieceAt(square[0] - 1, square[1] + 1) != null &&
					getBoard().getPieceAt(square[0] - 1, square[1] + 1).getColor() != this.getColor())
				addLegalMove(new int[] {square[0] - 1, square[1] + 1});
			if (getBoard().getPieceAt(square[0] + 1, square[1] + 1) != null &&
					getBoard().getPieceAt(square[0] + 1, square[1] + 1).getColor() != this.getColor())
				addLegalMove(new int[] {square[0] + 1, square[1] + 1});
			//en passant
			/*
			 * if (last move == file left || file right && is pawn move)
			 *   add en passant;
			 */
			if (this.getRank() == 5) {
				Move lastMove = getBoard().getMove(getBoard().getCurrentMoveNum() - 1);
				int lastMoveFile = lastMove.getOrigin()[0];
				if (lastMove.getPiece() instanceof Pawn && (lastMoveFile == this.getFile() -1 || lastMoveFile == this.getFile() +1)) {
					
				}
			}
		}
		else {
			if (getBoard().getPieceAt(square[0], square[1] - 1) == null) {	//black pawn, moving down
				addLegalMove(new int[] {square[0], square[1] - 1});
				if (square[1] == 7)	//on the second rank
					addLegalMove(new int[] {square[0], square[1] - 2});
			}
			if (getBoard().getPieceAt(square[0] - 1, square[1] - 1) != null &&
					getBoard().getPieceAt(square[0] - 1, square[1] - 1).getColor() != this.getColor())
				addLegalMove(new int[] {square[0] - 1, square[1] - 1});
			if (getBoard().getPieceAt(square[0] + 1, square[1] - 1) != null &&
					getBoard().getPieceAt(square[0] + 1, square[1] - 1).getColor() != this.getColor())
				addLegalMove(new int[] {square[0] + 1, square[1] - 1});
		}
	}

}
