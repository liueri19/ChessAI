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
		String result;
		if (this.getColor())
			result = "WQ@";
		else
			result = "BQ@";
		result += getFile();
		result += getRank();
		return result;
	}

	@Override
	public String toBriefString() {
		if (getColor())
			return "WQ";
		return "BQ";
	}

	@Override
	void updatePiece(boolean threatsOnly) {
		if (!threatsOnly)
			clearLegalMoves();
		clearThreats();
		int[] square = getSquare();
		Piece target;
		// rook's
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
		target = null;
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
		target = null;
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
		target = null;
		
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
		Piece p = new Queen(board, this.getColor(), this.getFile(), this.getRank());
//		for (Move move : this.getLegalMoves())
//			p.checkMove(move.copy());
		return p;
	}
}
