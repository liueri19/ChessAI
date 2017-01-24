package org._7hills.liueri19.game;

public abstract class Piece implements Comparable<Piece>{
	private Color color;	//color the color of the piece. 0 for black, 1 for white.
	private final Board board;
	private int[] coordinate;
	private int[][] legalMoves;
	
	public Piece(Board board, Color color, int x, int y) {
		this.board = board;
		this.color = color;
		coordinate = new int[] {x, y};
	}
	
	public Board getBoard() {
		return board;
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
			generateLegalMoves(square);
		return legalMoves;
	}
	
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
	
	public boolean move(int x, int y) {
		int[] move = new int[] {x, y};
		if (isLegalMove(move)) {
			setSquare(move);
			return true;
		}
		return false;
	}
	
	public abstract void generateLegalMoves(int[] square);
	
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
