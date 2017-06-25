package org._7hills.liueri19.game;

/**
 * This class describes a castling.
 * @author liueri19
 *
 */
public class Castling extends Move {
	private final Rook rook;
	private final boolean kingSide;
	
	/**
	 * Constructs a new Castling object identifying the move.
	 * 
	 * @param king	the king
	 * @param rook	the rook
	 */
	public Castling(King king, Rook rook) {
		super(king, king.getSquare(), rook.getSquare());	//just to satisfy the constructor
		this.rook = rook;
		kingSide = rook.getFile() == 8;
//		king.setCastlable(false);
	}
	
//	/**
//	 * Constructs a new Castling object identifying the move.
//	 * This constructor assumes the origin of the move is the current square of the specified King.
//	 * 
//	 * @param king	the king
//	 * @param rook	the rook
//	 * @param to	destination of the king
//	 */
//	public Castling(King king, Rook rook, int[] to) {
//		super(king, to);
//		this.rook = rook;
//		king.setCastlable(false);
//	}

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
	 * <code>"0-0"</code> for king side castling, <code>"0-0-0"</code> for queen side castling.
	 */
	@Override
	public String toString() {
		if (getDestination()[0] == 3)
			return "0-0-0";
		return "0-0";
	}

	@Override
	public void execute(Board board) {
		King king = (King) getInit();
		if (isKingSide()) {
			//move pieces
			king.setSquare(7, king.getRank());
			rook.setSquare(6, rook.getRank());
		}
		else {
			king.setSquare(3, king.getRank());
			rook.setSquare(4, rook.getRank());
		}
		king.setCastlable(false);
		rook.setCastlable(false);
	}
}
