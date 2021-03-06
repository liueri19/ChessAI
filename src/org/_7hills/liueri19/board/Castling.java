package org._7hills.liueri19.board;

import java.util.Collections;
import java.util.List;

/**
 * This class describes a castling.
 * @author liueri19
 *
 */
public class Castling extends Move {
	private final Rook rook;
	private final boolean kingSide;
	
	/**
	 * Constructs a new Castling object representing a castling move.
	 * 
	 * @param king	the king
	 * @param rook	the rook
	 */
	public Castling(King king, Rook rook) {
		super(king, king.getSquare(), rook.getSquare());
		this.rook = rook;
		kingSide = rook.getFile() == 8;
	}

	/**
	 * Returns the Rook related to this Castling.
	 * @return the Rook related to this Castling
	 */
	public Rook getRook() {
		return rook;
	}
	
	/**
	 * Returns true if the castling is to the King side, false otherwise.
	 * @return true if the castling is to the King side, false otherwise
	 */
	public boolean isKingSide() {
		return kingSide;
	}
	
	/**
	 * Returns a string representation of this Castling object.
	 * <code>"O-O"</code> for king side castling, <code>"O-O-O"</code> for queen side castling.
	 */
	@Override
	public String toString() {
		if (getDestination()[0] == 1)
			return "O-O-O";
		return "O-O";
	}

	@Override
	public void execute(Board board) {
		King king = (King) getInit();
		if (isKingSide()) {
			//move pieces
			king.modifySquare(7, king.getRank());
			rook.modifySquare(6, rook.getRank());
		}
		else {
			king.modifySquare(3, king.getRank());
			rook.modifySquare(4, rook.getRank());
		}
		king.setCastlable(false);
		rook.setCastlable(false);
		List<Piece> pieces = board.getPieces();
		Collections.swap(pieces, pieces.indexOf(king), pieces.indexOf(rook));
	}

	@Override
	public void revert(Board board) {
		King king = (King) getInit();
		king.modifySquare(getOrigin());
		rook.modifySquare(getDestination());
		king.setCastlable(true);
		rook.setCastlable(true);
		List<Piece> pieces = board.getPieces();
		Collections.swap(pieces, pieces.indexOf(king), pieces.indexOf(rook));
	}
}
