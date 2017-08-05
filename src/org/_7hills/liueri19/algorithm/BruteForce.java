package org._7hills.liueri19.algorithm;

import org._7hills.liueri19.board.*;

/**
 * Implements a brute force algorithm to find a best move.
 * This class is only for demonstration and testing purposes.
 */
public class BruteForce extends Algorithm {
	/*
	Represent branches with linked lists?
	Algorithm should run on a separate thread (from the board).
	Search for move even on opponent's turn.
	Brute force simulate all moves for at least 3 minutes, if an acceptable move found:
		return move;
		else, search until one is found, or until 5 minutes is reached.
	Evaluate position based on values of pieces and positions of pieces. (class EvaluatedPiece ?)
	 */

	private static final int BILLION = 1000000000;
	/** The minimum time limit of a search in seconds.
	 * A search should run for at least {@code MIN_SEARCH_TIME} seconds
	 * before giving a result.
	 */
	public static final int MIN_SEARCH_TIME = 180;
	/** The maximum time allowed for a search in seconds.
	 * A search should complete once this limit is reached.
	 * Note: a search may complete before this time limit is reached if an
	 * acceptable move is found.
	 */
	public static final int MAX_SEARCH_TIME = 300;

	//copy documentation?
	public BruteForce(Board board, boolean side) {
		super(board, side);
	}

	@Override
	public synchronized Move getMove() {
		return move.getMove();
	}

	@Override
	public void run() {
		//do brute force here
		boolean moveFound = false;
		long startTime = System.nanoTime();

		//no need to check moveFound until time limit
		while (!moveFound) {	//while no move found
			while (System.nanoTime() / BILLION - startTime <= MIN_SEARCH_TIME) {	//while searched for less than 3 min

			}
			while (System.nanoTime() / BILLION - startTime <= MAX_SEARCH_TIME) {	//while less than MAX_SEARCH_TIME
				//moveFound = true;
			}
			//maximum time limit reached
			//moveFound = true;
		}
	}


}
