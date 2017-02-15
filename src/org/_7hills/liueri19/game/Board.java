package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	protected List<Piece> pieces = new ArrayList<Piece>();
	private boolean gameEnded = false;
	private int gameResult;
	private boolean autoPrint = true;
	private boolean whiteMove = true;
	private List<Object[]> history = new ArrayList<Object[]>();
	
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
			
			else if (input.length() == 4) {	//a move
				char fileO = input.charAt(0);
				int rankO = Character.getNumericValue(input.charAt(1));
				
				if (Character.toString(fileO).matches("[abcdefgh]")) {	//if the first character is a, b, c, d, e, f, g, or h
					piece = board.getPieceAt(fileO, rankO);
					if (piece == null) {
						System.out.println("Invalid input: no piece on the selected square");
						continue;
					}
					//a valid piece selected, validate turn
					if (piece.getColor() == Color.WHITE && !board.whiteMove) {
						System.out.println("Invalid input: cannot move white piece on black's turn");
						continue;
					}
					else if (piece.getColor() == Color.BLACK && board.whiteMove) {
						System.out.println("Invalid input: cannot move black piece on white's turn");
						continue;
					}
					//get the target square
					char fileD = input.charAt(2);
					int rankD = Character.getNumericValue(input.charAt(3));
					if (!piece.move(parseFile(fileD), rankD)) {
						System.out.println("Invalid input: illegal move");
						continue;
					}
					//move verified legal
					board.history.add(new Object[] {new Character(fileO), new Integer(rankO), new Character(fileD), new Integer(rankD)});
					board.changeTurn();
				}
				else {	//if the input did not start with abcdefgh
					System.out.println("Invalid input: invalid square coordinate");
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
	
	public void changeTurn() {
		//update legalMoves
		whiteMove = !whiteMove;
	}
	
	public Piece getPieceAt(char file, int rank) {
		return getPieceAt(parseFile(file), rank);
	}
	
	public Piece getPieceAt(int file, int rank) {
		pieces.sort(null);
		for (Piece p : pieces) {
			if (p.getFile() == file && p.getRank() == rank)
				return p;
			if (rank > p.getRank())
				break;
		}
		return null;
	}
	
	public void printHistory() {
		for (Object[] move : history) {
			System.out.printf("%s%s->%s%s\n", (Character) move[0], (Integer) move[1], (Character) move[2], (Integer) move[3]);
		}
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
				Piece p = new King(this, Color.WHITE, 0, 0);	//placeholder
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
		pieces.add(new Bishop(this, Color.BLACK, 3, 8));
		pieces.add(new Bishop(this, Color.BLACK, 6, 8));
		//queens
		pieces.add(new Queen(this, Color.WHITE, 4, 1));
		pieces.add(new Queen(this, Color.BLACK, 4, 8));
		//kings
		pieces.add(new King(this, Color.WHITE, 5, 1));
		pieces.add(new King(this, Color.BLACK, 5, 8));
		
		pieces.sort(null);
	}
	
	public List<Object[]> getHistory() {
		return history;
	}
	
	public Object[] getMove(int moveNum) {
		return history.get(moveNum);
	}
	
	public int getCurrentMoveNum() {
		return history.size();	//starts with 0
	}
	
	public boolean isSquareUnderAttack(Color color, int file, int rank) {
		for (Piece p : pieces) {
			if (p.getColor() != color && p.isLegalMove(new int[] {file, rank}))
				return true;
		}
		return false;
	}
}
