package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	
	private boolean castlable = true;
	private List<int[]> attackedSquares = new ArrayList<int[]>();

	public King(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor())
			return "WK";
		return "BK";
	}

	@Override
	public void updatePiece(int[] square) {
		this.clearLegalMoves();
		
		//8 candidate moves
		int[][] candidates = new int[][] {
			new int[] {square[0] -1, square[1] +1},
			new int[] {square[0], square[1] +1},
			new int[] {square[0] +1, square[1] +1},
			new int[] {square[0] -1, square[1]},
			new int[] {square[0] +1, square[1]},
			new int[] {square[0] -1, square[1] -1},
			new int[] {square[0], square[1] -1},
			new int[] {square[0] +1, square[1] -1}
		};
		//eliminate illegal moves
		for (int[] move : candidates) {
			if (move[0] < 1 || move[1] < 1 || move[0] > 8 || move[1] > 8)
				continue;
			addAttackedSquare(move);
			if (!getBoard().isSquareAttacked(this.getColor(), move[0], move[1]) &&
					(getBoard().getPieceAt(move[0], move[1]) == null ||
					getBoard().getPieceAt(move[0], move[1]).getColor() != this.getColor()))
				addLegalMove(move);
		}
	}
	
	public boolean isCastlable() {
		return castlable;
	}
	
	public void setCastlable(boolean castlable) {
		this.castlable = castlable;
	}
	
	@Override
	public List<int[]> getAttackedSquares() {
		return attackedSquares;
	}
	
	@Override
	public void setAttackedSquares(List<int[]> moves) {
		attackedSquares = moves;
	}
	
	@Override
	public void clearAttackedSquares() {
		attackedSquares.clear();
	}
	
	@Override
	public void addAttackedSquare(int[] move) {
		attackedSquares.add(move);
	}
	
	@Override
	public boolean move(int file, int rank) {
		setCastlable(false);
		return super.move(file, rank);
	}
}
