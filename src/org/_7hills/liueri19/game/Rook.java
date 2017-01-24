package org._7hills.liueri19.game;

public class Rook extends Piece {

	public Rook(Board board, Color color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WR";
		return "BR";
	}

	@Override
	public int[][] generateLegalMoves(int[] square) {
		for (int fileP = square[0]+1; fileP < 9; fileP++) {
			if (getBoard().getPieceAt(fileP, square[1]) == null)
				addLegalMove(new int[] {fileP, square[1]});
		}
	}
}
