package org._7hills.liueri19.game;

import java.util.Arrays;

public class Move {
	private final Piece piece;	//the mover
	private final Piece subject;	//may be null
	private final int[] origin, destination;
	
	public Move(Piece piece, int[] from, int[] to) {
		this.piece = piece.copy();
		this.subject = null;
		origin = Arrays.copyOf(from, from.length);
		destination = Arrays.copyOf(to, to.length);
	}
	
	public Move(Piece piece, Piece subject, int[] from, int[] to) {
		this.piece = piece;
		this.subject = subject;
		origin = Arrays.copyOf(from, from.length);
		destination = Arrays.copyOf(to, to.length);
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
