package org._7hills.liueri19.game;

/**
 * Represents a Bishop. This class overrides certain methods in Piece.
 * @author liueri19
 *
 */
public class Bishop extends Piece {

	public Bishop(Board board, boolean color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor())
			return "WB";
		return "BB";
	}

	@Override
	protected void updatePiece(int[] square) {
		this.clearLegalMoves();
		
		boolean blockedPP, blockedPN, blockedNP, blockedNN;
		blockedPP = blockedPN = blockedNP = blockedNN = false;
		int rankPP, rankPN, rankNP, rankNN;
		rankPP = rankNP = square[1] + 1;
		rankPN = rankNN = square[1] - 1;
		
		Piece target;
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
	public Piece copy(Board board) {
		Piece p = new Bishop(board, this.getColor(), this.getFile(), this.getRank());
//		for (Move move : this.getLegalMoves())	// ArrayList.clone() creates shallow copy
//			p.addLegalMove(move.copy());
		return p;
	}
}
