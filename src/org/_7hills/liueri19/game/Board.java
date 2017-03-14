package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	private List<Piece> pieces = new ArrayList<Piece>();
	private King whiteKing, blackKing;
	private boolean gameEnded = false;
	private int gameResult;
	private boolean autoPrint = true;
	private boolean whiteMove = true;
	//private List<Object[]> history = new ArrayList<Object[]>();
	private List<Move> history = new ArrayList<Move>();
	
	public Board() {
		setUpPieces();
	}
	
	public Board(boolean doSetUp) {
		if (doSetUp)
			setUpPieces();
	}
	
	public static void main(String[] args) {
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
		String input;
		Piece piece;
		
		System.out.println("Use command 'prtboard' to see a visual representation of the board.\nUse command 'autoprt' to switch automatic board print on/off.\nUse command 'prthistory' to see previous moves.");
		//board.printBoard();
		while(!board.gameEnded) {
			input = sc.nextLine();
			//parse input
			if (input.equals("prtboard")) {
				board.printBoard();
				continue;
			}
			else if (input.equals("autoprt")) {
				board.autoPrint = !board.autoPrint;
				System.out.println("autoprt changed to " + board.autoPrint);
			}
			else if (input.equals("prthistory")) {
				board.printHistory();
				continue;
			}
			else if (input.equals("resign"))
				board.gameEnded = true;
//			else if (input.equals("draw"))
//				//suggest draw to the opponent
			
			else if (input.equals("0-0")) {	//castling, king side
				//validate requirements
				/*
				 * The king and the chosen rook are on the player's first rank.
				 * Neither the king nor the chosen rook has previously moved.
				 * There are no pieces between the king and the chosen rook.
				 * The king is not currently in check.
				 * The king does not pass through a square that is attacked by an enemy piece.
				 * The king does not end up in check. (True of any legal move.)
				 */
				King king;
				Piece rook;
				if (board.whiteMove) {	//acquire the pieces
					king = board.whiteKing;
					rook = board.getPieceAt(8, 1);
				}
				else {
					king = board.blackKing;
					rook = board.getPieceAt(8, 8);
				}
				if (rook == null || !(rook instanceof Rook) || !((Rook) rook).isCastlable()) {
					System.out.println("The rook had been moved");
					continue;
				}
				if (!king.isCastlable()) {
					System.out.println("The king had been moved");
					continue;
				}
				if (board.getPieceAt(6, king.getRank()) != null || board.getPieceAt(7, king.getRank()) != null) {
					System.out.println("There are piece(s) blocking the castling");
					continue;
				}
				if (board.isInCheck(king.getColor())) {
					System.out.println("The king cannot castle while in check");
					continue;
				}
				if (board.isSquareAttacked(king.getColor(), 6, king.getRank())) {
					System.out.println("The king cannot move through an attacked square");
					continue;
				}
				if (board.isSquareAttacked(king.getColor(), 7, king.getRank())) {
					System.out.println("The king cannot catle into a check");
					continue;
				}
				
				int[] from = king.getSquare();
				//move pieces
				king.setSquare(7, king.getRank());
				rook.setSquare(6, rook.getRank());
				//add history entry
				board.history.add(new Castling(king, from, king.getSquare()));
				
				((Rook) rook).setCastlable(false);
				board.changeTurn();
			}
			
			else if (input.equals("0-0-0")) {	//castling, queen side
				King king;
				Piece rook;
				if (board.whiteMove) {	//acquire the pieces
					king = board.whiteKing;
					rook = board.getPieceAt(1, 1);
				}
				else {
					king = board.blackKing;
					rook = board.getPieceAt(1, 8);
				}
				if (rook == null || !(rook instanceof Rook) || !((Rook) rook).isCastlable()) {
					System.out.println("The rook had been moved");
					continue;
				}
				if (!king.isCastlable()) {
					System.out.println("The king had been moved");
					continue;
				}
				if (board.getPieceAt(2, king.getRank()) != null || board.getPieceAt(3, king.getRank()) != null ||
						board.getPieceAt(4, king.getRank()) != null) {
					System.out.println("There are piece(s) blocking the castling");
					continue;
				}
				if (board.isInCheck(king.getColor())) {
					System.out.println("The king cannot castle while in check");
					continue;
				}
				if (board.isSquareAttacked(king.getColor(), 4, king.getRank())) {
					System.out.println("The king cannot move through an attacked square");
					continue;
				}
				if (board.isSquareAttacked(king.getColor(), 3, king.getRank())) {
					System.out.println("The king cannot catle into a check");
					continue;
				}
				
				int[] from = king.getSquare();
				//move pieces
				king.setSquare(3, king.getRank());
				rook.setSquare(4, rook.getRank());
				//add history entry
				board.history.add(new Castling(king, from, king.getSquare()));
				
				((Rook) rook).setCastlable(false);
				board.changeTurn();
			}
			
			else if (input.length() == 4) {	//a move
				char fileO = input.charAt(0);
				int rankO = Character.getNumericValue(input.charAt(1));
				
				if (Character.toString(fileO).matches("[abcdefgh]")) {	//if the first character is a, b, c, d, e, f, g, or h
					piece = board.getPieceAt(fileO, rankO);
					if (piece == null) {
						System.out.println("No piece on the selected square");
						continue;
					}
					//a valid piece selected, validate turn
					if (piece.getColor() && !board.whiteMove) {
						System.out.println("Cannot move white piece on black's turn");
						continue;
					}
					else if (!piece.getColor() && board.whiteMove) {
						System.out.println("Cannot move black piece on white's turn");
						continue;
					}
					
					//get the target square
					char fileD = input.charAt(2);
					int rankD = Character.getNumericValue(input.charAt(3));
					int[] from = piece.getSquare();
					int[] to = new int[] {parseFile(fileD), rankD};
					if (!piece.move(to)) {
						System.out.println("Illegal move");
						continue;
					}
					//move verified legal
					board.history.add(new Move(piece, from, to));
					board.changeTurn();
				}
				else {	//if the input did not start with abcdefgh
					System.out.println("Invalid square coordinate");
					continue;
				}
			}
			
			if (board.autoPrint)
				board.printBoard();
		}
		sc.close();
	}
	
	public static int parseFile(char file) {
		int fileNum = 0;
		switch (file) {
			case 'a': fileNum = 1; break;
			case 'b': fileNum = 2; break;
			case 'c': fileNum = 3; break;
			case 'd': fileNum = 4; break;
			case 'e': fileNum = 5; break;
			case 'f': fileNum = 6; break;
			case 'g': fileNum = 7; break;
			case 'h': fileNum = 8; break;
		}
		return fileNum;
	}
	
	public static char parseFile(int file) {
		char fileNum = '0';
		switch (file) {
			case 1: fileNum = 'a'; break;
			case 2: fileNum = 'b'; break;
			case 3: fileNum = 'c'; break;
			case 4: fileNum = 'd'; break;
			case 5: fileNum = 'e'; break;
			case 6: fileNum = 'f'; break;
			case 7: fileNum = 'g'; break;
			case 8: fileNum = 'h'; break;
		}
		return fileNum;
	}
	
	public void changeTurn() {
		this.updatePieces();
		whiteMove = !whiteMove;
	}
	
	public Piece getPieceAt(char file, int rank) {
		return getPieceAt(parseFile(file), rank);
	}
	
	public Piece getPieceAt(int file, int rank) {
		for (Piece p : pieces) {
			if (p.getFile() == file && p.getRank() == rank)
				return p;
		}
		return null;
	}
	
	public Piece getPieceAt(int[] square) {
		return getPieceAt(square[0], square[1]);
	}
	
	public boolean removePiece(Piece p) {
		return this.pieces.remove(p);
	}
	
	public Piece removePiece(int index) {
		return this.pieces.remove(index);
	}
	
	public void printHistory() {
		for (Move move : history)
			System.out.println(move.toString());
	}
	
	public void printBoard() {
		String whiteSpace = "|    ";
		String blackSpace = "|////";
		boolean even, white;
		int index = 0;
		pieces.sort(null);
		//first line, upper border
		System.out.println("_________________________________________");
		
		//squares
		for (int rank = 8; rank > 0; rank--) {
			even = white = rank % 2 == 0;
			//rank first line
			for (int i = 0; i < 4; i++) {
				if (even)
					System.out.print(whiteSpace + blackSpace);
				else
					System.out.print(blackSpace + whiteSpace);
			}
			System.out.println("|");
			//rank second line
			for (int file = 1; file < 9; file++) {
				Piece p = new King(this, true, 0, 0);	//placeholder
				try {
				p = pieces.get(index);
				}
				catch (IndexOutOfBoundsException e) {
				}
				if (p.getRank() == rank && p.getFile() == file) {
					index++;
					if (white)
						System.out.printf("| %s ", p);
					else
						System.out.printf("|/%s/", p);
				}
				else {
					if (white)
						System.out.print("|    ");
					else
						System.out.print("|////");
				}
				white = !white;
			}
			System.out.println("|");
			//rank third line
			System.out.println("|____|____|____|____|____|____|____|____|");
		}
	}
	
	public void setUpPieces() {
		//pawns
		for (int y = 2; y < 8; y += 5) {
			for (int x = 1; x < 9; x++) {
				if (y == 2)
					pieces.add(new Pawn(this, true, x, y));
				else
					pieces.add(new Pawn(this, false, x, y));
			}
		}
		//rooks
		pieces.add(new Rook(this, true, 1, 1));
		pieces.add(new Rook(this, true, 8, 1));
		pieces.add(new Rook(this, false, 1, 8));
		pieces.add(new Rook(this, false, 8, 8));
		//knights
		pieces.add(new Knight(this, true, 2, 1));
		pieces.add(new Knight(this, true, 7, 1));
		pieces.add(new Knight(this, false, 2, 8));
		pieces.add(new Knight(this, false, 7, 8));
		//bishops
		pieces.add(new Bishop(this, true, 3, 1));
		pieces.add(new Bishop(this, true, 6, 1));
		pieces.add(new Bishop(this, false, 3, 8));
		pieces.add(new Bishop(this, false, 6, 8));
		//queens
		pieces.add(new Queen(this, true, 4, 1));
		pieces.add(new Queen(this, false, 4, 8));
		//kings
		pieces.add(whiteKing = new King(this, true, 5, 1));
		pieces.add(blackKing = new King(this, false, 5, 8));
		
		updatePieces();
	}
	
	public List<Move> getHistory() {
		return history;
	}
	
	public Move getMove(int moveNum) {
		return history.get(moveNum);
	}
	
	public int getCurrentMoveNum() {
		return history.size();	//starts with 0
	}
	
	public boolean isSquareAttacked(boolean color, int file, int rank) {
		for (Piece p : pieces) {
			if (p.getColor() != color && p.isThreating(new int[] {file, rank}))
				return true;
		}
		return false;
	}
	
	public boolean isInCheck(boolean color) {
		return color ? isSquareAttacked(color, whiteKing.getFile(), whiteKing.getRank()) : isSquareAttacked(color, blackKing.getFile(), blackKing.getRank());
	}
	
	public void updatePieces() {
		for (Piece p : pieces) {
			if (!(p instanceof King))
				p.updatePiece();
		}
		//kings need to be updated last
		whiteKing.updatePiece();
		blackKing.updatePiece();
	}
}
