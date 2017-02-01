package org._7hills.liueri19.game;

public class Pawn extends Piece {
	private boolean isWhite;

	public Pawn(Board board, Color color, int x, int y) {
		super(board, color, x, y);
		if (this.getColor() == Color.WHITE)
			isWhite = true;
		else
			isWhite = false;
	}

	@Override
	public String toString() {
		if (isWhite)
			return "WP";
		return "BP";
	}

	@Override
	public void generateLegalMoves(int[] square) {
		if (isWhite) {
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
