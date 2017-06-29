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

	public Bishop(Board board, boolean color, int[] coordinate) {
		super(board, color, coordinate);
	}

	@Override
	public String toString() {
		String result;
		if (this.getColor())
			result = "WB@";
		else
			result = "BB@";
		result += getFile();
		result += getRank();
		return result;
	}

	@Override
	public String toBriefString() {
		if (getColor())
			return "WB";
		return "BB";
	}

	@Override
	void updatePiece(boolean threatsOnly) {
		if (!threatsOnly)
			clearLegalMoves();
		clearThreats();
		int[] square = getSquare();
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
					checkMove(new Move(this, square, new int[] {fileP, rankPP}), threatsOnly);
				else {
					blockedPP = true;
					if (target.getColor() != this.getColor())
						checkMove(new Move(this, target, square, new int[] {fileP, rankPP}), threatsOnly);
				}
			}
			else
				blockedPP = true;
			
			if (!blockedPN && rankPN > 0) {
				target = getBoard().getPieceAt(fileP, rankPN);
				if (target == null)
					checkMove(new Move(this, square, new int[] {fileP, rankPN}), threatsOnly);
				else {
					blockedPN = true;
					if (target.getColor() != this.getColor())
						checkMove(new Move(this, target, square, new int[] {fileP, rankPN}), threatsOnly);
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
					checkMove(new Move(this, square, new int[] {fileN, rankNP}), threatsOnly);
				else {
					blockedNP = true;
					if (target.getColor() != this.getColor())
						checkMove(new Move(this, target, square, new int[] {fileN, rankNP}), threatsOnly);
				}
			}
			else
				blockedNP = true;
			
			if (!blockedNN && rankNN > 0) {
				target = getBoard().getPieceAt(fileN, rankNN);
				if (target == null)
					checkMove(new Move(this, square, new int[] {fileN, rankNN}), threatsOnly);
				else {
					blockedNN = true;
					if (getBoard().getPieceAt(fileN, rankNN).getColor() != this.getColor())
						checkMove(new Move(this, target, square, new int[] {fileN, rankNN}), threatsOnly);
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
		return new Bishop(board, this.getColor(), this.getFile(), this.getRank());
	}
}
