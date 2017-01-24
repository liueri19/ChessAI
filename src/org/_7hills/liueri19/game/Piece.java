package org._7hills.liueri19.game;

public abstract class Piece implements Comparable<Piece>{
	private Color color;	//color the color of the piece. 0 for black, 1 for white.
	private int[] coordinate;
	private int[][] legalMoves;
	
	public Piece(Color color, int x, int y) {
		this.color = color;
		coordinate = new int[] {x, y};
	}
	
	public Color getColor() {
		return color;
	}
	
	public int[] getSquare() {
		return coordinate;
	}
	
	public void setSquare(int[] square) {
		coordinate = square;
	}
	
	public int getFile() {
		return coordinate[0];
	}
	
	public int getRank() {
		return coordinate[1];
	}
	
	public int[][] getLegalMoves(int[] square) {
		if (legalMoves.length == 0)
			setLegalMoves(generateLegalMoves(square));
		return legalMoves;
	}
	
	public abstract int[][] generateLegalMoves(int[] square);
	
	public void setLegalMoves(int[][] moves) {
		legalMoves = moves;
	}
	
	public void clearLegalMoves() {
		legalMoves = new int[][] {};
	}
	
	public void addLegalMove(int[] move) {
		legalMoves[legalMoves.length] = move;
	}
	
	public boolean isLegalMove(int[] move) {
		for (int[] m : legalMoves) {
			if (move.equals(m))
				return true;
		}
		return false;
	}
	
	public abstract boolean move(int x, int y);
	
	public abstract String toString();
	
	@Override
	public int compareTo(Piece piece) {
		if (this.getRank() == piece.getRank()) {
			if (this.getFile() < piece.getFile())
				return -1;
			return 1;
		}
		if (this.getRank() > piece.getRank())
			return -1;
		return 1;
	}
}
