package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece implements Comparable<Piece>{
	private boolean color;	//color the color of the piece. true for white, false for black
	private final Board board;
	private int[] coordinate;
	private List<Move> legalMoves = new ArrayList<Move>();
	
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
	
	public List<Move> getLegalMoves() {
		return legalMoves;
	}
	
	public void setLegalMoves(ArrayList<Move> moves) {
		legalMoves = moves;
	}
	
	public void clearLegalMoves() {
		legalMoves.clear();
	}
	
	public void addLegalMove(Move move) {
		legalMoves.add(move);
	}
	
	public boolean isLegalMove(Move move) {
		for (Move m : getLegalMoves()) {
			if (move.equals(m))
				return true;
		}
		return false;
	}
	
	/**
	 * For pieces other than King, attacked squares are the same as legal moves
	 */
	public List<Move> getAttackedSquares() {
		return getLegalMoves();
	}
	
	public void setAttackedSquares(ArrayList<Move> moves) {
		setLegalMoves(moves);
	}
	
	public void clearAttackedSquares() {
		clearLegalMoves();
	}
	
	public void addAttackedSquare(Move move) {
		addLegalMove(move);
	}
	
	public boolean isAttacking(Move move) {
		for (Move m : getAttackedSquares()) {
			if (move.equals(m))
				return true;
		}
		return false;
	}
	////
	
	public boolean move(int file, int rank) {
		return move(new Move(this, this.getSquare(), new int[] {file, rank}));
	}
	
	public boolean move(Move move) {
		if (isLegalMove(move)) {
			Piece p = move.getPiece();
			if (move.getSubject() != null)
				getBoard().removePiece(move.getSubject());
			setSquare(move.getDestination());
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
	
	@Override
	public boolean equals(Object piece) {
		if (this.getClass().equals(piece.getClass()))
			return true;
		return false;
	}
}
