package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	//protected Map<int[], Piece> pieces = new HashMap<int[], Piece>();
	protected List<Piece> pieces = new ArrayList<Piece>();
	private boolean gameEnded = false;
	private boolean autoPrint = false;
	
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
		
		System.out.println("Use command 'prtboard' to see a visual representation of the board.\nUse command 'autoprt' to switch automatic board print on/off.");
		//board.printBoard();
		while(!board.gameEnded) {
			input = sc.nextLine();
			//parse input
			if (input.equals("prtboard"))
				board.printBoard();
			else if (input.equals("autoprt"))
				board.autoPrint = !board.autoPrint;
			else if (input.equals("resign"))
				board.gameEnded = true;
//			else if (input.equals("draw"))
//				//suggest draw to the opponent
			
			else if (input.length() == 4) {	//a move
				char fileO = input.charAt(0);
				int rankO = Integer.valueOf(input.charAt(1));
				
				if (Character.toString(fileO).matches("[abcdefgh]"))	//if the first character is a, b, c, d, e, f, g, or h
					piece = board.getPieceAt(fileO, rankO);
			}
			
			if (board.autoPrint)
				board.printBoard();
		}
		sc.close();
	}
	
	public Piece getPieceAt(char file, int rank) {
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
		
		if (fileNum == 0)
			return null;
		
		pieces.sort(null);
		for (Piece p : pieces) {
			if (p.getFile() == fileNum && p.getRank() == rank)
				return p;
			if (p.getRank() > rank)
				break;
		}
		return null;
	}
	
	public void printBoard() {
		String whiteSpace = "|    ";
		String blackSpace = "|////";
		boolean even, white;
		int index = 0;
		
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
				Piece p = pieces.get(index);
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
					pieces.add(new Pawn(this, Color.WHITE, x, y));
				else
					pieces.add(new Pawn(this, Color.BLACK, x, y));
			}
		}
		//rooks
		pieces.add(new Rook(this, Color.WHITE, 1, 1));
		pieces.add(new Rook(this, Color.WHITE, 8, 1));
		pieces.add(new Rook(this, Color.BLACK, 1, 8));
		pieces.add(new Rook(this, Color.BLACK, 8, 8));
		//knights
		pieces.add(new Knight(this, Color.WHITE, 2, 1));
		pieces.add(new Knight(this, Color.WHITE, 7, 1));
		pieces.add(new Knight(this, Color.BLACK, 2, 8));
		pieces.add(new Knight(this, Color.BLACK, 7, 8));
		//bishops
		pieces.add(new Bishop(this, Color.WHITE, 3, 1));
		pieces.add(new Bishop(this, Color.WHITE, 6, 1));
		pieces.add(new Bishop(this, Color.WHITE, 3, 8));
		pieces.add(new Bishop(this, Color.WHITE, 6, 8));
		//queens
		pieces.add(new Queen(this, Color.WHITE, 4, 1));
		pieces.add(new Queen(this, Color.BLACK, 4, 8));
		//kings
		pieces.add(new King(this, Color.WHITE, 5, 1));
		pieces.add(new King(this, Color.BLACK, 5, 8));
		
		pieces.sort(null);
	}
}
