package org._7hills.liueri19.game;

import java.util.Arrays;

public class Move {
	private Piece piece;	//the mover
	private Piece subject;	//may be null
	private int[] origin, destination;
	
	public Move(Piece piece, int[] from, int[] to) {
		this.piece = piece;
		origin = from;
		destination = to;
	}
	
	public Move(Piece piece, Piece subject, int[] from, int[] to) {
		this(piece, from, to);
		this.subject = subject;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Piece getSubject() {
		return subject;
	}
	
	public int[] getOrigin() {
		return origin;
	}
	
	public int[] getDestination() {
		return destination;
	}
	
	@Override
	public String toString() {
		return "" + Board.parseFile(origin[0]) + origin[1] + Board.parseFile(destination[0]) + destination[1];
	}
	
	@Override
	public boolean equals(Object move) {
		if (move instanceof Move)
			return false;
		else if (Arrays.equals(getOrigin(), ((Move) move).getOrigin()) 
				&& Arrays.equals(getDestination(), ((Move) move).getDestination()) 
				&& getPiece().equals(((Move) move).getPiece())
				&& getSubject() != null
				&& ((Move) move).getSubject() != null
				&& getSubject().equals(((Move) move).getSubject()))
			return true;
		return false;
	}
}
