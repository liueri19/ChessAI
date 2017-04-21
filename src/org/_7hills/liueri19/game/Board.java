package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The Board where Pieces rest on and the game would be played.
 * 
 * @author liueri19
 */
public class Board {
	//private List<Piece> pieces = new ArrayList<Piece>();
	private Map<int[], Piece> pieces = new HashMap<>();
	private King whiteKing, blackKing;
	private boolean gameEnded = false;
	private int gameResult;
	private boolean autoPrint = true;
	private boolean whiteMove = true;
	private List<Move> history = new ArrayList<Move>();
	
	/**
	 * Constructs a chess board with standard setup.
	 */
	public Board() {
		setupPieces();
	}
	
	/**
	 * Constructs a chess board with setup optional.
	 * 
	 * @param doSetUp	option for setup pieces.
	 */
	public Board(boolean doSetUp) {
		if (doSetUp)
			setupPieces();
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
					System.out.println("The king cannot castle into a check");
					continue;
				}
				
				//move
				board.move(new Castling(king, (Rook) rook));
				
//				//move pieces
//				king.setSquare(7, king.getRank());
//				rook.setSquare(6, rook.getRank());
//				//add history entry
//				board.history.add(new Castling(king, king.getSquare()));
//				
//				((Rook) rook).setCastlable(false);
//				board.changeTurn();
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
				
				board.move(new Castling(king, (Rook) rook));
				
//				//move pieces
//				king.setSquare(3, king.getRank());
//				rook.setSquare(4, rook.getRank());
//				//add history entry
//				board.history.add(new Castling(king, king.getSquare()));
//				
//				((Rook) rook).setCastlable(false);
//				board.changeTurn();
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
					int[] to = new int[] {parseFile(fileD), rankD};
					Move m = new Move(piece, to);
					if (!board.move(m)) {
						System.out.println("Illegal move");
						continue;
					}
//					//move verified legal
//					board.history.add(m);
//					board.changeTurn();
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
	
	/**
	 * Parse the file represented as a char into an int.
	 * 
	 * @param file	the char representation of a file, always lower-case
	 * @return the int representation of the input file
	 */
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
	
	/**
	 * Parse the file represented as an int into a char.
	 * 
	 * @param file	the int representation of a file
	 * @return the char representation of the input file
	 */
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
	
	/**
	 * Change white's turn to black's turn or vice versa. This method also updates all pieces.
	 */
	protected void changeTurn() {
		this.updatePieces();
		whiteMove = !whiteMove;
	}
	
	/**
	 * Iterates through the list of pieces currently on the board and returns the piece at the specified location.
	 * 
	 * @param file	the file of the piece represented as char
	 * @param rank	the rank of the piece represented as int
	 * @return the Piece object with the specified file and rank, or null if none has the specified value
	 */
	public Piece getPieceAt(char file, int rank) {
		return getPieceAt(parseFile(file), rank);
	}
	
	/**
	 * Iterates through the list of pieces currently on the board and returns the piece at the specified location.
	 * 
	 * @param file	the file of the piece represented as int
	 * @param rank	the rank of the piece represented as int
	 * @return the Piece object with the specified file and rank, or null if none has the specified value
	 */
	public Piece getPieceAt(int file, int rank) {	//should change implementation to use a map
		for (Piece p : pieces) {
			if (p.getFile() == file && p.getRank() == rank)
				return p;
		}
		return null;
	}
	
	/**
	 * Iterates through the list of pieces currently on the board and returns the piece at the specified location.
	 * 
	 * @param square	the file and rank in an int array
	 * @return the Piece object with the specified file and rank, or null if none has the specified value
	 */
	public Piece getPieceAt(int[] square) {
		return getPieceAt(square[0], square[1]);
	}
	
	/**
	 * Returns the equivalent Piece object of the specified Piece in the List of Pieces no the Board,
	 * or null if none is found.
	 * @param copy the Piece to find
	 * @return the equivalent of the specified Piece, or null if such Piece is not on the Board
	 */
	public Piece getPiece(Piece copy) {
		for (Piece p : pieces) {
			if (p.equals(copy))
				return p;
		}
		return null;
	}
	
	/**
	 * Removes the specified Piece object from the list of Pieces currently on the board.
	 * 
	 * @param p	the Piece to remove
	 * @return true if this list contained the specified element
	 */
	protected boolean removePiece(Piece p) {
		return this.pieces.remove(p);
	}
	
	/**
	 * Removes the Piece object at the specified location in the list of Pieces currently on the board.
	 * 
	 * @param index	the index of the Piece to be removed
	 * @return the Piece that was removed
	 */
	protected Piece removePiece(int index) {
		return this.pieces.remove(index);
	}
	
	/**
	 * Add a Piece to the List of Piece objects on the Board.
	 * 
	 * @param piece the Piece to add
	 * @return true if the Piece is added to the List
	 */
	protected boolean addPiece(Piece piece) {
		return pieces.add(piece);
	}
	
	/**
	 * Prints a list of moves played to the console.
	 */
	public void printHistory() {
		for (Move move : history)
			System.out.println(move.toString());
	}
	
	/**
	 * Print a visual representation of the current state of the board to the console.
	 */
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
	
	/**
	 * Construct new Piece objects each with their standard starting position.
	 */
	protected void setupPieces() {
		int[] location = new int[2];
		//pawns
		for (int y = 2; y < 8; y += 5) {
			for (int x = 1; x < 9; x++) {
				if (y == 2) {
					location[0] = x;
					location[1] = y;
					pieces.put(location, new Pawn(this, true, location));
				}
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
	
	/**
	 * Returns a List of Move objects representing the previous moves played.
	 * 
	 * @return a List of Move objects representing the previous moves played
	 */
	public List<Move> getHistory() {
		return history;
	}
	
	/**
	 * Returns the Move object at the specified index in history. Note that the index starts at 0, and each Move will occupy an index in history, therefore the index is not the same as the move number in standard transcript.
	 * 
	 * @param moveNum	the index of the desired Move object
	 * @return the Move object at the specified index in history
	 */
	public Move getMove(int moveNum) {
		return history.get(moveNum);
	}
	
	/**
	 * Returns the number of Move objects recorded.
	 * 
	 * @return the number of Move objects recorded (the size of the history)
	 */
	public int getCurrentMoveNum() {
		return history.size();	//starts with 0
	}
	
	/**
	 * Returns true if the specified square is being attacked by the opponent of <code>color</code>, and false otherwise.<br>
	 * <p>
	 * <code>isSquareAttacked(true, 1, 1)</code> returns true if square A1 is being attacked by black;
	 * likewise, <code>isSquareAttacked(false, 1, 1)</code> returns true if square A1 is being attacked by white.
	 * 
	 * @param color	the color of the friendly side
	 * @param file	the file of the square
	 * @param rank	the rank of the square
	 * @return true if the specified square is being attacked by the opponent of <code>color</code>, and false otherwise
	 */
	public boolean isSquareAttacked(boolean color, int file, int rank) {
		for (Piece p : pieces) {
			if (p.getColor() != color && p.isThreatening(new Move(p, new int[] {file, rank})))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the King of the specified color is in check, false otherwise.
	 * 
	 * @param color	the color of the specified king
	 * @return true if the King of the specified color is in check, false otherwise
	 */
	public boolean isInCheck(boolean color) {
		return color ? isSquareAttacked(color, whiteKing.getFile(), whiteKing.getRank()) : isSquareAttacked(color, blackKing.getFile(), blackKing.getRank());
	}
	
	/**
	 * Call <code>updatePiece()</code> on all Piece objects currently on the board.
	 */
	protected void updatePieces() {
		for (Piece p : pieces) {
			if (!(p instanceof King))
				p.updatePiece();
		}
		//kings need to be updated last
		whiteKing.updatePiece();
		blackKing.updatePiece();
	}
	
	/**
	 * Execute the specified Move object. Returns true if the move described is legal, or false otherwise.
	 * @param move the Move to execute
	 * @return true if the move described is legal, false otherwise
	 */
	public boolean move(Move move) {
		Piece init = move.getPiece();
		if (init.isLegalMove(move)) {
			if (move instanceof Castling) {
				King king = (King) init;
				Rook rook = ((Castling) move).getRook();
				if (((Castling) move).isKingSide()) {
					//move pieces
					king.setSquare(7, king.getRank());
					rook.setSquare(6, rook.getRank());
				}
				else {
					king.setSquare(3, king.getRank());
					rook.setSquare(4, rook.getRank());
				}
				king.setCastlable(false);
				rook.setCastlable(false);
			}
			else {	//otherwise, a usual move
				Piece subject = getPieceAt(move.getDestination());
				if (subject != null)
					removePiece(subject);
				init.setSquare(move.getDestination());
			}
			this.history.add(move);
			changeTurn();
			return true;
		}
		return false;
	}
	
//	/**
//	 * Revert the specified number of moves.<br>
//	 * Note that "move" is not to be confused with "turn". In this context, one move will be
//	 * considered as one action taken by one side. Two moves make one full turn.
//	 * @param numMoves the number of moves to revert
//	 */
//	protected void revert(int numMoves) {
//		for (int i = numMoves; i > 0; i--) {
//			Move move = history.remove(history.size()-1);
//			move.getPiece().setSquare(move.getOrigin());	//reset location
//			Piece subject = move.getSubject(); 
//			if (subject != null)
//				addPiece(subject);
//		}
//		if (numMoves % 2 == 0)
//			updatePieces();
//		else
//			changeTurn();	//changeTurn() call updatePieces()
//	}
//	
//	/**
//	 * Revert one move.
//	 */
//	protected void revert() {
//		revert(1);
//	}
}
