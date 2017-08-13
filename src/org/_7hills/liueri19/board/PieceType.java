package org._7hills.liueri19.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This enum represents all piece types and provides the short representations of each piece type.<br>
 * K for King;<br>
 * Q for Queen;<br>
 * R for Rook;<br>
 * B for Bishop;<br>
 * N for Knight;<br>
 * P for Pawn.
 */
public enum PieceType {
	KNIGHT	('N'),
	PAWN	('P'),
	QUEEN	('Q'),
	ROOK	('R'),
	BISHOP	('B'),
	KING	('K');

	private char charRep;
	/** List of PieceTypes that can be promoted to, containing QUEEN, KNIGHT, ROOK and BISHOP. */
	public static final List<PieceType> PROMOTABLES =
			Collections.unmodifiableList(Arrays.asList(QUEEN, KNIGHT, ROOK, BISHOP));

	PieceType(char charRep) {
		this.charRep = charRep;
	}

	/**
	 * Return the character representation of the corresponding piece type.
	 * @return the character representation of the specified piece type
	 */
	char getCharRep() { return charRep; }

	/**
	 * Return an instance of PieceType corresponding to the specified character representation.
	 * @param charRep	the short representation of a piece
	 * @return the corresponding PieceType instance
	 */
	static PieceType getInstance(char charRep) {
		switch (charRep) {
			case 'Q': return QUEEN;
			case 'N': return KNIGHT;
			case 'R': return ROOK;
			case 'B': return BISHOP;
			case 'P': return PAWN;
			case 'K': return KING;
			default:
				throw new IllegalArgumentException();
		}
	}
}
