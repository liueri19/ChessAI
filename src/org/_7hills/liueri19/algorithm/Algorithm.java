package org._7hills.liueri19.algorithm;

import org._7hills.liueri19.board.Board;
import org._7hills.liueri19.board.Move;

/**
 * A algorithm that attempts to select the optimal move from all legal moves.
 */
public abstract class Algorithm implements Runnable {
	protected EvaluatedMove move;
	protected final Board board;
	protected final boolean side;

	/**
	 * Constructs a new Algorithm, running on the specified board, playing the specified side.
	 * @param board	the Board to play on
	 * @param side	the side to play
	 */
	public Algorithm(Board board, boolean side) {
		this.board = board;
		this.side = side;
	}

	/**
	 * Return the optimal move found by this Algorithm. Note that this method may cause the
	 * executing thread to wait until an optimal move is found if none is found at the time
	 * of invocation.
	 * @return	the optimal move found
	 */
	public abstract Move getMove();
}
