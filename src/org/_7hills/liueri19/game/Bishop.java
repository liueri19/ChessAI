package org._7hills.liueri19.game;

public class Bishop extends Piece {

	public Bishop(Board board, Color color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WB";
		return "BB";
	}

	@Override
	public void generateLegalMoves(int[] square) {
		boolean blockedPP, blockedPN, blockedNP, blockedNN;
		blockedPP = blockedPN = blockedNP = blockedNN = false;
		int rankPP, rankPN, rankNP, rankNN;
		rankPP = rankNP = square[1] + 1;
		rankPN = rankNN = square[1] - 1;
		
		for (int fileP = square[0] + 1; fileP < 9; fileP++) {
			if (!blockedPP && rankPP < 9) {
				if (getBoard().getPieceAt(fileP, rankPP) == null)
					addLegalMove(new int[] {fileP, rankPP});
				else if (getBoard().getPieceAt(fileP, rankPP).getColor() != this.getColor()) {
					addLegalMove(new int[] {fileP, rankPP});
					blockedPP = true;
				}
			}
			else
				blockedPP = true;
			
			if (!blockedPN && rankPN > 0) {
				if (getBoard().getPieceAt(fileP, rankPN) == null)
					addLegalMove(new int[] {fileP, rankPN});
				else if (getBoard().getPieceAt(fileP, rankPN).getColor() != this.getColor()) {
					addLegalMove(new int[] {fileP, rankPN});
					blockedPN = true;
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
				else if (getBoard().getPieceAt(fileN, rankNP).getColor() != this.getColor()) {
					addLegalMove(new int[] {fileN, rankNP});
					blockedNP = true;
				}
			}
			else
				blockedNP = true;
			
			if (!blockedNN && rankNN > 0) {
				if (getBoard().getPieceAt(fileN, rankNN) == null)
					addLegalMove(new int[] {fileN, rankNN});
				else if (getBoard().getPieceAt(fileN, rankNN).getColor() != this.getColor()) {
					addLegalMove(new int[] {fileN, rankNN});
					blockedNN = true;
				}
			}
			else
				blockedNN = true;
			rankNP++;
			rankNN--;
		}
	}
}
