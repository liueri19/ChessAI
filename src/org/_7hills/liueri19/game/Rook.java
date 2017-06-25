package org._7hills.liueri19.game;

/**
 * Represents a Rook. This class overrides certain methods in Piece.
 * @author liueri19
 *
 */
public class Rook extends Piece {
	
	private boolean castlable = true;

	public Rook(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	public Rook(Board board, boolean color, int[] coordinate) {
		super(board, color, coordinate);
	}

	@Override
	public String toString() {
		String result;
		if (this.getColor())
			result = "WR@";
		else
			result = "BR@";
		result += getFile();
		result += getRank();
		return result;
	}

	@Override
	public String toBriefString() {
		if (getColor())
			return "WR";
		return "BR";
	}

	@Override
	void updatePiece(boolean threatsOnly) {
		if (!threatsOnly)
			clearLegalMoves();
		clearThreats();
		int[] square = getSquare();
		Piece target;
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			target = getBoard().getPieceAt(fileP, square[1]);
			if (target == null)	//if the square is empty
				checkMove(new Move(this, square, new int[] {fileP, square[1]}), threatsOnly);
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				checkMove(new Move(this, target, square, new int[] {fileP, square[1]}), threatsOnly);
				break;
			}
			else	
				break;
		}
        for (int fileN = square[0] - 1; fileN > 0; fileN--) {
			target = getBoard().getPieceAt(fileN, square[1]);
			if (target == null)
				checkMove(new Move(this, square, new int[] {fileN, square[1]}), threatsOnly);
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				checkMove(new Move(this, target, square, new int[] {fileN, square[1]}), threatsOnly);
				break;
			}
			else
				break;
		}
        for (int rankP = square[1] + 1; rankP < 9; rankP++) {
			target = getBoard().getPieceAt(square[0], rankP);
			if (target == null)
				checkMove(new Move(this, square, new int[] {square[0], rankP}), threatsOnly);
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				checkMove(new Move(this, target, square, new int[] {square[0], rankP}), threatsOnly);
				break;
			}
			else
				break;
		}
		for (int rankN = square[1] - 1; rankN > 0; rankN--) {
			target = getBoard().getPieceAt(square[0], rankN);
			if (target == null)
				checkMove(new Move(this, square, new int[] {square[0], rankN}), threatsOnly);
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				checkMove(new Move(this, target, square, new int[] {square[0], rankN}), threatsOnly);
				break;
			}
			else
				break;
		}
	}
	
	public boolean isCastlable() {
		return castlable;
	}
	
	public void setCastlable(boolean castlable) {
		this.castlable = castlable;
	}

	@Override
	public Piece copy(Board board) {
		Piece p = new Rook(board, this.getColor(), this.getFile(), this.getRank());
//		for (Move move : this.getLegalMoves())
//			p.checkMove(move.copy());
		return p;
	}
}
