package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King. This class overrides certain methods in Piece.
 * @author liueri19
 *
 */
public class King extends Piece {
	
	private boolean castlable = true;
	private List<Move> attackedSquares = new ArrayList<Move>();

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
	protected void updatePiece(int[] square) {
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
			addThreat(new Move(this, square, move));
			if (!getBoard().isSquareAttacked(this.getColor(), move[0], move[1])) {
				Piece target = getBoard().getPieceAt(move[0], move[1]);
				if (target == null)
					addLegalMove(new Move(this, square, move));
				else if (target.getColor() != this.getColor())
					addLegalMove(new Move(this, target, square, move));
			}
		}
	}
	
	public boolean isCastlable() {
		return castlable;
	}
	
	protected void setCastlable(boolean castlable) {
		this.castlable = castlable;
	}
	
	@Override
	public List<Move> getThreats() {
		return attackedSquares;
	}
	
	@Override
	protected void setThreats(List<Move> moves) {
		attackedSquares = moves;
	}
	
	@Override
	protected void clearThreats() {
		attackedSquares.clear();
	}
	
	@Override
	protected void addThreat(Move move) {
		attackedSquares.add(move);
	}
	
//	@Override
//	public boolean move(int file, int rank) {
//		setCastlable(false);
//		return super.move(file, rank);
//	}

	@Override
	public Piece copy() {
		King p = new King(this.getBoard(), this.getColor(), this.getFile(), this.getRank());
		p.setCastlable(this.isCastlable());
//		for (Move move : this.getLegalMoves())
//			p.addLegalMove(move.copy());
//		this.getLegalMoves().forEach((Move m)->p.addLegalMove(m.copy()));
		return p;
	}
}
