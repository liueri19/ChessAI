package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece implements Comparable<Piece>{
	private Color color;	//color the color of the piece. 0 for black, 1 for white.
	private final Board board;
	private int[] coordinate;
	private List<int[]> legalMoves = new ArrayList<int[]>();
	
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
	
	public List<int[]> getLegalMoves() {
		return legalMoves;
	}
	
	public void setLegalMoves(List<int[]> moves) {
		legalMoves = moves;
	}
	
	public void clearLegalMoves() {
		legalMoves.clear();
	}
	
	public void addLegalMove(int[] move) {
		legalMoves.add(move);
	}
	
	public boolean isLegalMove(int[] move) {
		for (int[] m : getLegalMoves()) {
			if (Arrays.equals(move, m))
				return true;
		}
		return false;
	}
	
	public boolean move(int file, int rank) {
		int[] move = new int[] {file, rank};
		generateLegalMoves(getSquare());
		if (isLegalMove(move)) {
			Piece p = getBoard().getPieceAt(move[0], move[1]);
			if (p != null)
				getBoard().pieces.remove(p);
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
