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

	@Override
	public String toString() {
		if (this.getColor())
			return "WR";
		return "BR";
	}

	@Override
	public void updatePiece(int[] square) {
		this.clearLegalMoves();
		
		Piece target;
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			target = getBoard().getPieceAt(fileP, square[1]);
			if (target == null)	//if the square is empty
				addLegalMove(new Move(this, square, new int[] {fileP, square[1]}));
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new Move(this, target, square, new int[] {fileP, square[1]}));
				break;
			}
			else	
				break;
		}
		target = null;
		for (int fileN = square[0] - 1; fileN > 0; fileN--) {
			target = getBoard().getPieceAt(fileN, square[1]);
			if (target == null)
				addLegalMove(new Move(this, square, new int[] {fileN, square[1]}));
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new Move(this, target, square, new int[] {fileN, square[1]}));
				break;
			}
			else
				break;
		}
		target = null;
		for (int rankP = square[1] + 1; rankP < 9; rankP++) {
			target = getBoard().getPieceAt(square[0], rankP);
			if (target == null)
				addLegalMove(new Move(this, square, new int[] {square[0], rankP}));
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new Move(this, target, square, new int[] {square[0], rankP}));
				break;
			}
			else
				break;
		}
		for (int rankN = square[1] - 1; rankN > 0; rankN--) {
			target = getBoard().getPieceAt(square[0], rankN);
			if (target == null)
				addLegalMove(new Move(this, square, new int[] {square[0], rankN}));
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new Move(this, target, square, new int[] {square[0], rankN}));
				break;
			}
			else
				break;
		}
	}
	
	@Override
	public boolean move(int file, int rank) {
		setCastlable(false);
		return super.move(file, rank);
	}
	
	public boolean isCastlable() {
		return castlable;
	}
	
	public void setCastlable(boolean castlable) {
		this.castlable = castlable;
	}

	@Override
	public Piece copy() {
		Piece p = new Pawn(this.getBoard(), this.getColor(), this.getFile(), this.getRank());
		for (Move move : this.getLegalMoves())
			p.addLegalMove(move.copy());
		return p;
	}
}
