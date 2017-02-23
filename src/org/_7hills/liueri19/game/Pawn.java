package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
	private List<int[]> attackedSquares = new ArrayList<int[]>();

	public Pawn(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (getColor())
			return "WP";
		return "BP";
	}

	@Override
	public void updatePiece(int[] square) {
		this.clearLegalMoves();
		this.clearAttackedSquares();
		
		if (getColor()) {
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
				int lastMoveFile = Board.parseFile((char) lastMove[0]);	//not working
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
		
		updateAttackedSquares();
	}
	
	private void updateAttackedSquares() {
		int file = getFile();
		int rank = getRank();
		if (getColor()) {
			if (file == 1)
				addAttackedSquare(new int[] {2, rank + 1});
			else if (file == 8)
				addAttackedSquare(new int[] {7, rank + 1});
			else {
				addAttackedSquare(new int[] {file - 1, rank + 1});
				addAttackedSquare(new int[] {file + 1, rank + 1});
			}
		}
		else {
			if (file == 1)
				addAttackedSquare(new int[] {2, rank - 1});
			else if (file == 8)
				addAttackedSquare(new int[] {7, rank - 1});
			else {
				addAttackedSquare(new int[] {file - 1, rank - 1});
				addAttackedSquare(new int[] {file + 1, rank - 1});
			}
		}
	}
	
	//this can be done differently, but I chose this way for consistency with the King class
	@Override
	public List<int[]> getAttackedSquares() {
		return attackedSquares;
	}
	
	@Override
	public void setAttackedSquares(ArrayList<int[]> moves) {
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
}
