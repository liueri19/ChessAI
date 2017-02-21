package org._7hills.liueri19.game;

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
	public void updatePiece(int[] square) {
		this.clearLegalMoves();
		
		boolean blockedPP, blockedPN, blockedNP, blockedNN;
		blockedPP = blockedPN = blockedNP = blockedNN = false;
		int rankPP, rankPN, rankNP, rankNN;
		rankPP = rankNP = square[1] + 1;
		rankPN = rankNN = square[1] - 1;
		
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			if (!blockedPP && rankPP < 9) {
				if (getBoard().getPieceAt(fileP, rankPP) == null)
					addLegalMove(new int[] {fileP, rankPP});
				else {
					blockedPP = true;
					if (getBoard().getPieceAt(fileP, rankPP).getColor() != this.getColor())
						addLegalMove(new int[] {fileP, rankPP});
				}
			}
			else
				blockedPP = true;
			
			if (!blockedPN && rankPN > 0) {
				if (getBoard().getPieceAt(fileP, rankPN) == null)
					addLegalMove(new int[] {fileP, rankPN});
				else {
					blockedPN = true;
					if (getBoard().getPieceAt(fileP, rankPN).getColor() != this.getColor())
						addLegalMove(new int[] {fileP, rankPN});
				}
			}
			else
				blockedPN = true;
			rankPP++;
			rankPN--;
		}
		
		for (int fileN = square[0] - 1; fileN > 0; fileN--) {
			if (!blockedNP && rankNP < 9) {
				if (getBoard().getPieceAt(fileN, rankNP) == null)
					addLegalMove(new int[] {fileN, rankNP});
				else {
					blockedNP = true;
					if (getBoard().getPieceAt(fileN, rankNP).getColor() != this.getColor())
						addLegalMove(new int[] {fileN, rankNP});
				}
			}
			else
				blockedNP = true;
			
			if (!blockedNN && rankNN > 0) {
				if (getBoard().getPieceAt(fileN, rankNN) == null)
					addLegalMove(new int[] {fileN, rankNN});
				else {
					blockedNN = true;
					if (getBoard().getPieceAt(fileN, rankNN).getColor() != this.getColor())
						addLegalMove(new int[] {fileN, rankNN});
				}
			}
			else
				blockedNN = true;
			rankNP++;
			rankNN--;
		}
	}
}
