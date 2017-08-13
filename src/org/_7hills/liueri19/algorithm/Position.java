package org._7hills.liueri19.algorithm;

import org._7hills.liueri19.board.Board;
import org._7hills.liueri19.board.Piece;
import org._7hills.liueri19.board.PieceType;

/**
 * This is an immutable class representing a specific position of a board.
 * PieceType is used instead of Pieces to achieve immutability.
 */
public final class Position {
	private final PieceType[][] pieces = new PieceType[9][9];

	/**
	 * Construct a new Position based on the specified board.
	 * @param board	the board to gather the positions of pieces from
	 */
	public Position(Board board) {
		for (Piece piece : board.getPieces())
			addPieceTo(piece.getPieceType(), piece.getFile(), piece.getRank());
	}

	/**
	 * Get the PieceType on the specified square.
	 * @param file	the file of the target square
	 * @param rank	the rank of the target square
	 * @return	the PieceType on the specified square
	 */
	public PieceType getPieceAt(int file, int rank) {
		return pieces[rank][file];
	}

	/**
	 * Add PieceType to the specified square
	 * @param pieceType	the PieceType to add
	 * @param file		the file of the target square
	 * @param rank		the rank of the target square
	 */
	private void addPieceTo(PieceType pieceType, int file, int rank) {
		pieces[rank][file] = pieceType;
	}
}
