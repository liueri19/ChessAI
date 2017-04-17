package org._7hills.liueri19.game;

/**
 * Represents a Queen. This class overrides certain methods in Piece.
 * @author liueri19
 *
 */
public class Queen extends Piece {

	public Queen(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor())
			return "WQ";
		return "BQ";
	}

	@Override
	protected void updatePiece(int[] square) {
		this.clearLegalMoves();
		Piece target;
		
		// rook's
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			target = getBoard().getPieceAt(fileP, square[1]);
			if (target == null)	//if the square is empty
				addLegalMove(new Move(this, square, new int[] {fileP, square[1]}));
			else if (target.getColor() != this.getColor()) {	//if the square has an piece of the opposite color
				addLegalMove(new Move(this, square, new int[] {fileP, square[1]}));
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
				addLegalMove(new Move(this, square, new int[] {fileN, square[1]}));
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
				addLegalMove(new Move(this, square, new int[] {square[0], rankP}));
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
				addLegalMove(new Move(this, square, new int[] {square[0], rankN}));
				break;
			}
			else
				break;
		}
		//bishop's
		boolean blockedPP, blockedPN, blockedNP, blockedNN;
		blockedPP = blockedPN = blockedNP = blockedNN = false;
		int rankPP, rankPN, rankNP, rankNN;
		rankPP = rankNP = square[1] + 1;
		rankPN = rankNN = square[1] - 1;
		target = null;	// not necessary, but just for safety
		
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			if (!blockedPP && rankPP < 9) {
				target = getBoard().getPieceAt(fileP, rankPP);	//may be null
				if (target == null)
					addLegalMove(new Move(this, square, new int[] {fileP, rankPP}));
				else {
					blockedPP = true;
					if (target.getColor() != this.getColor())
						addLegalMove(new Move(this, square, new int[] {fileP, rankPP}));
				}
			}
			else
				blockedPP = true;
			
			if (!blockedPN && rankPN > 0) {
				target = getBoard().getPieceAt(fileP, rankPN);
				if (target == null)
					addLegalMove(new Move(this, square, new int[] {fileP, rankPN}));
				else {
					blockedPN = true;
					if (target.getColor() != this.getColor())
						addLegalMove(new Move(this, square, new int[] {fileP, rankPN}));
				}
			}
			else
				blockedPN = true;
			rankPP++;
			rankPN--;
		}
		target = null;
		
		for (int fileN = square[0] - 1; fileN > 0; fileN--) {
			if (!blockedNP && rankNP < 9) {
				target = getBoard().getPieceAt(fileN, rankNP);
				if (target == null)
					addLegalMove(new Move(this, square, new int[] {fileN, rankNP}));
				else {
					blockedNP = true;
					if (target.getColor() != this.getColor())
						addLegalMove(new Move(this, square, new int[] {fileN, rankNP}));
				}
			}
			else
				blockedNP = true;
			
			if (!blockedNN && rankNN > 0) {
				target = getBoard().getPieceAt(fileN, rankNN);
				if (target == null)
					addLegalMove(new Move(this, square, new int[] {fileN, rankNN}));
				else {
					blockedNN = true;
					if (getBoard().getPieceAt(fileN, rankNN).getColor() != this.getColor())
						addLegalMove(new Move(this, square, new int[] {fileN, rankNN}));
				}
			}
			else
				blockedNN = true;
			rankNP++;
			rankNN--;
		}
	}

	@Override
	public Piece copy() {
		Piece p = new Queen(this.getBoard(), this.getColor(), this.getFile(), this.getRank());
//		for (Move move : this.getLegalMoves())
//			p.addLegalMove(move.copy());
		return p;
	}
}
