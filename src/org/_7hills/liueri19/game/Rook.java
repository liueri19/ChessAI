package org._7hills.liueri19.game;

public class Rook extends Piece {
	
	private boolean castlable = true;

	public Rook(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor())
			return "WR";
		return "BR";
	}

	@Override
	public void updatePiece(int[] square) {
		this.clearLegalMoves();
		
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			if (getBoard().getPieceAt(fileP, square[1]) == null)	//if the square is empty
				addLegalMove(new int[] {fileP, square[1]});
			else if (getBoard().getPieceAt(fileP, square[1]).getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new int[] {fileP, square[1]});
				break;
			}
			else	
				break;
		}
		for (int fileN = square[0] - 1; fileN > 0; fileN--) {
			if (getBoard().getPieceAt(fileN, square[1]) == null)
				addLegalMove(new int[] {fileN, square[1]});
			else if (getBoard().getPieceAt(fileN, square[1]).getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new int[] {fileN, square[1]});
				break;
			}
			else
				break;
		}
		for (int rankP = square[1] + 1; rankP < 9; rankP++) {
			if (getBoard().getPieceAt(square[0], rankP) == null)
				addLegalMove(new int[] {square[0], rankP});
			else if (getBoard().getPieceAt(square[0], rankP).getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new int[] {square[0], rankP});
				break;
			}
			else
				break;
		}
		for (int rankN = square[1] - 1; rankN > 0; rankN--) {
			if (getBoard().getPieceAt(square[0], rankN) == null)
				addLegalMove(new int[] {square[0], rankN});
			else if (getBoard().getPieceAt(square[0], rankN).getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new int[] {square[0], rankN});
				break;
			}
			else
				break;
		}
	}
	
	@Override
	public boolean move(int file, int rank) {
		boolean isLegal = super.move(file, rank);
		if (isLegal)
			setCastlable(false);
		return isLegal;
	}
	
	public boolean isCastlable() {
		return castlable;
	}
	
	public void setCastlable(boolean castlable) {
		this.castlable = castlable;
	}
}
