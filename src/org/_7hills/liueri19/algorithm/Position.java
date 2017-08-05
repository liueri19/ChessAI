package org._7hills.liueri19.algorithm;

import org._7hills.liueri19.board.Board;

/**
 * Represent a specific position of a board.
 * This class is effectively an immutable Board.
 */
public final class Position {
	private final Board board;

	public Position(Board board) {
		this.board = board;	//TODO need a board copy
	}
}
