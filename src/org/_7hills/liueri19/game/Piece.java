package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece implements Comparable<Piece>{
	private boolean color;	//color the color of the piece. true for white, false for black
	private final Board board;
	private int[] coordinate;
	private List<int[]> legalMoves = new ArrayList<int[]>();
	
	public Piece(Board board, boolean color, int x, int y) {
		this.board = board;
		this.color = color;
		coordinate = new int[] {x, y};
	}
	
	public Board getBoard() {
		return board;
	}
	
	public boolean getColor() {
		return color;
	}
	
	public int[] getSquare() {
		return coordinate;
	}
	
	public void setSquare(int[] square) {
		coordinate = square;
	}
	
	public void setSquare(int file, int rank) {
		setSquare(new int[] {file, rank});
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
	
	public void setLegalMoves(ArrayList<int[]> moves) {
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
	
	/**
	 * For pieces other than King and Pawn, attacked squares are the same as legal moves
	 */
	public List<int[]> getAttackedSquares() {
		return getLegalMoves();
	}
	
	public void setAttackedSquares(ArrayList<int[]> moves) {
		setLegalMoves(moves);
	}
	
	/**
	 * All classes implementing attacked squares separately must call this method in updatePiece();
	 */
	public void clearAttackedSquares() {
		clearLegalMoves();
	}
	
	public void addAttackedSquare(int[] move) {
		addLegalMove(move);
	}
	
	public boolean isAttacking(int[] move) {
		for (int[] m : getAttackedSquares()) {
			if (Arrays.equals(move, m))
				return true;
		}
		return false;
	}
	////
	
	public boolean move(int file, int rank) {
		int[] move = new int[] {file, rank};
		if (isLegalMove(move)) {
			Piece p = getBoard().getPieceAt(move[0], move[1]);
			if (p != null)
				getBoard().removePiece(p);
			setSquare(move);
			return true;
		}
		return false;
	}
	
	public abstract void updatePiece(int[] square);
	
	public void updatePiece() {
		updatePiece(this.getSquare());
	}
	
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
