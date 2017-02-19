package org._7hills.liueri19.game;

public class Queen extends Piece {

	public Queen(Board board, Color color, int x, int y) {
		super(board, color, x, y);
	}

	@Override
	public String toString() {
		if (this.getColor() == Color.WHITE)
			return "WQ";
		return "BQ";
	}

	@Override
	public void updateLegalMoves(int[] square) {
		this.clearLegalMoves();
		
		// rook's
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
		//bishop's
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
