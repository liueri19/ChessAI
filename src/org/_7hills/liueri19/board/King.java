package org._7hills.liueri19.board;

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
		String result;
		if (getColor())
			result = "WK@";
		else
			result = "BK@";
		result += getFile();
		result += getRank();
		return result;
	}

	@Override
	public String toBriefString() {
		if (this.getColor())
			return "WK";
		return "BK";
	}

	@Override
	void updatePiece(boolean threatsOnly) {
		if (!threatsOnly)
			clearLegalMoves();
		clearThreats();
		int[] square = getSquare();
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
		for (int[] m : candidates) {
			if (m[0] < 1 || m[1] < 1 || m[0] > 8 || m[1] > 8)
				continue;
			Move move = new Move(this, null, square, m);	//constructor will attempt to find a subject
			Piece subject = move.getSubject();
			if (subject != null && subject.getColor() == getColor())
				continue;
			checkMove(move, threatsOnly);
		}
		//add Castling
		//validate requirements
		/*
		 * The king and the chosen rook are on the player's first rank.
		 * Neither the king nor the chosen rook has previously moved.
		 * There are no pieces between the king and the chosen rook.
		 * The king is not currently in check.
		 * The king does not pass through a square that is attacked by an enemy piece.
		 * The king does not end up in check. (True of any legal move.)
		 */
		if (isCastlable()) {
			for (int file = 1; file < 9; file += 7) {	//for only values 1 and 8
				Piece p = getBoard().getPieceAt(file, getRank());
				Board board = getBoard();
				if (p instanceof Rook && ((Rook) p).isCastlable() && !board.isInCheck(this.getColor())) {
					if (file == 1) {
						if (board.getPieceAt(4, getRank()) != null || board.getPieceAt(3, getRank()) != null
								|| board.getPieceAt(2, getRank()) != null)
							continue;
						if (board.isSquareAttacked(getColor(), 4, getRank())
								|| board.isSquareAttacked(getColor(), 3, getRank()))
							continue;
					}
					else {
						if (board.getPieceAt(6, getRank()) != null || board.getPieceAt(7, getRank()) != null)
							continue;
						if (board.isSquareAttacked(getColor(), 6, getRank())
								|| board.isSquareAttacked(getColor(), 7, getRank()))
							continue;
					}
					checkMove(new Castling(this, (Rook) p), threatsOnly);
				}
			}
		}
	}
	
	/**
	 * Returns true if this King can be castled.
	 * @return true if this King can be castled
	 */
	public boolean isCastlable() {
		return castlable;
	}
	
	void setCastlable(boolean castlable) {
		this.castlable = castlable;
	}
	
	@Override
	public List<Move> getThreats() {
		return attackedSquares;
	}
	
//	@Override
//	protected void setThreats(List<Move> moves) {
//		attackedSquares = moves;
//	}
	
	@Override
	protected void clearThreats() {
		attackedSquares.clear();
	}
	
	@Override
	protected void addThreat(Move move) {
		attackedSquares.add(move);
	}

}
