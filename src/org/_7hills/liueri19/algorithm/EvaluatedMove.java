package org._7hills.liueri19.algorithm;

import org._7hills.liueri19.board.Move;

/**
 * This class associates a value with a move.
 */
class EvaluatedMove {
	private double value;
	private final Move move;

	/**
	 * Constructs a new EvaluatedMove with the specified move and value.
	 * @param move	the Move to store
	 * @param value	the value to store
	 */
	public EvaluatedMove(Move move, double value) {
		this.move = move;	//TODO need a clone of move?
		this.value = value;
	}

	/**
	 * Returns the move stored.
	 * @return	the Move associated
	 */
	public Move getMove() {
		return move;	//TODO need a clone?
	}

	/**
	 * Returns the value of the move.
	 * @return	the value associated with the Move
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Assign the value of this move to the mean of the previous value and the specified value.
	 * @param value the value to take the mean with
	 * @return	the mean
	 */
	public double addValue(double value) {
		return this.value = (this.value + value) / 2;
	}

//	/**
//	 * An immutable object storing the information of a Move.
//	 */
//	public class ImmutableMove {	//separate class? subclass of Move?
//
//	}
}
