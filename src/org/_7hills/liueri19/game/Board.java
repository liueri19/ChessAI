package org._7hills.liueri19.game;

import java.util.*;

/**
 * The Board where PieceTypes rest on and the game would be played.
 * 
 * @author liueri19
 */
public final class Board {
	//this list must always be sorted. all manipulations must not break the order of this list. see PieceTypes.compareTo()
	private List<Piece> pieces = new ArrayList<>();
	private King whiteKing, blackKing;
	private boolean gameEnded = false;
	private boolean drawSuggested = false;
	private int gameResult;	//1 white win, -1 black win, 0 draw
	private boolean autoPrint = true;
	private boolean whiteMove = true;
	private List<Move> history = new ArrayList<>();
	private final Piece PLACEHOLDER = new Piece(null, true, 0, 0) {	//serves as a placeholder
		@Override
		public Piece copy(Board board) {
			return null;
		}
		@Override
		public void updatePiece(boolean threatsOnly) {}
		@Override
		public String toString() {
			return "PLACEHOLDER@" + getFile() + getRank();
		}
		@Override
		public String toBriefString() { return "PLACEHOLDER"; }
	};
	
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
	
	/**
	 * Constructs a new chess board with the same states of the specified board.
	 * @param board	the board to copy from
	 */
	public Board(Board board) {
		for (Piece p : board.getPieces())
			this.pieces.add(p.copy(this));
		whiteKing = (King) board.whiteKing.copy(this);
		blackKing = (King) board.blackKing.copy(this);
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
			else if (input.equals("resign")) {
				board.gameEnded = true;
				if (board.whiteMove)
					board.gameResult = -1;
				else
					board.gameResult = 1;
			}
			else if (input.equals("draw")) {    //TODO implement draw https://en.wikipedia.org/wiki/Draw_(chess)
				//suggest draw to the opponent
				if (board.drawSuggested) {
					board.gameEnded = true;
					board.gameResult = 0;
				}
				board.drawSuggested = true;
				continue;
			}
			
			else if (input.equals("O-O")) {	//castling, king side
				board.drawSuggested = false;
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
			}
			
			else if (input.equals("O-O-O")) {	//castling, queen side
				board.drawSuggested = false;
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
					System.out.println("The king cannot castle into a check");
					continue;
				}
				
				board.move(new Castling(king, (Rook) rook));
			}
			
			else if (input.length() == 4 || input.length() == 5) {	//a move
				board.drawSuggested = false;
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

					Move m = new Move(piece, piece.getSquare(), to);
					if (input.length() == 5 && piece instanceof Pawn)	//promotion
						m = new Promotion((Pawn) piece, input.charAt(4));
					if (!board.move(m)) {
						System.out.println("Illegal move");
						continue;
					}
				}
			}
			else {
				System.out.println("Invalid input");
				continue;
			}
			if (board.autoPrint)
				board.printBoard();
		}
		System.out.println("GAME ENDED");
		if (board.gameResult == 1)
			System.out.println("White won");
		else if (board.gameResult == -1)
			System.out.println("Black won");
		else
			System.out.println("Game draw");

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
	void changeTurn() {
		updatePieces(false);
		whiteMove = !whiteMove;
	}
	
	/**
	 * Returns a list containing all the pieces on this board.
	 * @return a list containing all the pieces on this board
	 */
	List<Piece> getPieces() {
		return new ArrayList<>(pieces);
	}
	
	/**
	 * Iterates through the list of pieces currently on the board and returns the piece at the specified location.
	 * 
	 * @param file	the file of the piece represented as char
	 * @param rank	the rank of the piece represented as int
	 * @return the Piece object with the specified file and rank, or null if none has the specified value
	 */
	Piece getPieceAt(char file, int rank) {
		return getPieceAt(parseFile(file), rank);
	}
	
	/**
	 * Searches through the list of pieces currently on the board and returns the piece at the specified location.
	 * 
	 * @param file	the file of the piece represented as int
	 * @param rank	the rank of the piece represented as int
	 * @return the Piece object with the specified file and rank, or null if none has the specified value
	 */
	Piece getPieceAt(int file, int rank) {
		//a placeholder to meet the arguments of Collections.binarySearch()
		//the following methods are implemented only because they are abstract in Piece.
		PLACEHOLDER.setSquare(file, rank);
        return getPiece(PLACEHOLDER);
	}
	
	/**
	 * Searches through the list of pieces currently on the board and returns the piece at the specified location.
	 * 
	 * @param square	the file and rank in an int array
	 * @return the Piece object with the specified file and rank, or null if none has the specified value
	 */
	Piece getPieceAt(int[] square) {
		return getPieceAt(square[0], square[1]);
	}
	
	/**
	 * Returns the equivalent Piece object of the specified Piece in the List of PieceTypes on the Board,
	 * or null if none is found.
	 * @param piece the Piece to find
	 * @return the equivalent of the specified Piece, or null if such Piece is not on the Board
	 */
	Piece getPiece(Piece piece) {
		int index = Collections.binarySearch(pieces, piece);	//binarySearch uses compareTo instead of equals
		if (index < 0)
			return null;
		return pieces.get(index);
	}
	
	/**
	 * Removes the specified Piece object from the list of PieceTypes currently on the board.
	 * 
	 * @param p	the Piece to remove
	 * @return true if this list contained the specified element
	 */
	boolean removePiece(Piece p) {
		return pieces.remove(p);
	}
	
	/**
	 * Removes the Piece object at the specified location in the list of PieceTypes currently on the board.
	 * 
	 * @param index	the index of the Piece to be removed
	 * @return the Piece that was removed
	 */
	Piece removePiece(int index) {
		return pieces.remove(index);
	}
	
	/**
	 * Add a Piece to the List of Piece objects on the Board.
	 * 
	 * @param piece the Piece to add
	 */
	void addPiece(Piece piece) {
		int index = Collections.binarySearch(pieces, piece);
		index = -index - 1;	//see documentation of Collections.binarySearch()
		pieces.add(index, piece);
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
				catch (IndexOutOfBoundsException ignored) {
				}
				if (p.getRank() == rank && p.getFile() == file) {
					index++;
					if (white)
						System.out.printf("| %s ", p.toBriefString());
					else
						System.out.printf("|/%s/", p.toBriefString());
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
		
		//print textual
//		for (Piece p : pieces)
//			System.out.println(p);
	}
	
	/**
	 * Construct new Piece objects each with their standard starting position.
	 */
	void setupPieces() {
		//pawns
//		for (int y = 2; y < 8; y += 5) {
//			for (int x = 1; x < 9; x++) {
//				if (y == 2)
//					addPiece(new Pawn(this, true, x, y));
//				else
//					addPiece(new Pawn(this, false, x, y));
//			}
//		}
//		//rooks
//		addPiece(new Rook(this, true, 1, 1));
//        addPiece(new Rook(this, true, 8, 1));
//        addPiece(new Rook(this, false, 1, 8));
//        addPiece(new Rook(this, false, 8, 8));
//		//knights
//        addPiece(new Knight(this, true, 2, 1));
//        addPiece(new Knight(this, true, 7, 1));
//		addPiece(new Knight(this, false, 2, 8));
//        addPiece(new Knight(this, false, 7, 8));
//		//bishops
//        addPiece(new Bishop(this, true, 3, 1));
//        addPiece(new Bishop(this, true, 6, 1));
//        addPiece(new Bishop(this, false, 3, 8));
//        addPiece(new Bishop(this, false, 6, 8));
//		//queens
//        addPiece(new Queen(this, true, 4, 1));
//        addPiece(new Queen(this, false, 4, 8));
		//kings
        addPiece(whiteKing = new King(this, true, 5, 1));
        addPiece(blackKing = new King(this, false, 5, 8));

//        //test for illegal move check
//        addPiece(new Rook(this, false, 4, 7));
//        addPiece(new Bishop(this, true, 5, 2));

//		//test for en passant
//		addPiece(new Pawn(this, true, 1, 2));
//		addPiece(new Pawn(this, false, 2, 4));
//		addPiece(new Pawn(this, true, 8, 5));
//		addPiece(new Pawn(this, false, 7, 7));

//		//test for promotion
		addPiece(new Pawn(this, true, 1, 7));
		addPiece(new Pawn(this, false, 7, 2));
		
		updatePieces(false);
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
		if (moveNum < 0 || moveNum >= history.size())
			return null;
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
	public boolean isSquareAttacked(boolean color, int file, int rank) {	//TODO: optimize this?
		for (Piece p : pieces) {
			if (p.getColor() != color && p.isThreatening(new Move(p, p.getSquare(), new int[] {file, rank})))
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
	 * @param threatsOnly	true to update only threats, false to update threats and legal moves
	 */
	void updatePieces(boolean threatsOnly) {
		for (Object o : pieces.toArray()) {	//to avoid concurrent mod, probably not very efficient
			Piece p = (Piece) o;
			if (!(p instanceof King))
				p.updatePiece(threatsOnly);
		}
		//kings should be updated last (shouldn't matter in current implementation, too lazy to change)
		whiteKing.updatePiece(threatsOnly);
		blackKing.updatePiece(threatsOnly);

		//check for checkmate
		if (!threatsOnly) {
			boolean whiteCheckmate, blackCheckmate;
			whiteCheckmate = blackCheckmate = true;
			for (Piece p : pieces) {
				if (whiteCheckmate && p.getColor()) {
					whiteCheckmate = p.getLegalMoves().isEmpty();
				}
				else if (blackCheckmate) {
					blackCheckmate = p.getLegalMoves().isEmpty();
				}
			}
			if (whiteCheckmate) {
				gameEnded = true;
				gameResult = -1;
			}
			else if (blackCheckmate) {
				gameEnded = true;
				gameResult = 1;
			}
		}
	}
	
	/**
	 * Execute the specified Move object. Returns true if the move described is legal, or false otherwise.
	 * @param move the Move to execute
	 * @return true if the move described is legal, false otherwise
	 */
	public boolean move(Move move) {
		Piece init = move.getInit();
		if (init.isLegalMove(move)) {
			List<Move> moves = init.getLegalMoves();
			//get the move from legal moves generated from Piece, Move passed in from main have null in subject field
			Move generatedMove = moves.get(moves.indexOf(move));
			if (move instanceof  Promotion)
				move.execute(this);
			else if (move instanceof Castling) {
				generatedMove.execute(this);
				Collections.swap(pieces, pieces.indexOf(init),
						pieces.indexOf(((Castling) generatedMove).getRook()));	//ensure correct order in pieces
			}
			else {	//otherwise, a usual move
				generatedMove.execute(this);
				//ensure correct order in pieces
				rearrange(pieces, init);
			}
			history.add(move);
			changeTurn();	//calls updatePieces()
			return true;
		}
		return false;
	}
	
	/**
	 * Execute the specified Move object without any checking.
	 * @param move	the move to execute
	 */
	void uncheckedMove(Move move) {
		Piece init = move.getInit();
		Piece subject = move.getSubject();
		if (subject != null)
			removePiece(subject);
		init.setSquare(move.getDestination());
		rearrange(pieces, init);
	}

	/**
	 * Reverts the changes of uncheckedMove()
	 * @param move	the move to revert
	 */
	void revert(Move move) {	//TODO handle promotion and castling?
		Piece init = move.getInit();
		Piece subject = move.getSubject();
		init.setSquare(move.getOrigin());
		if (subject != null)
			addPiece(subject);
		rearrange(pieces, init);
	}

	/**
	 * Rearrange the location of the moved piece in the specified list to match the order specified by compareTo.
	 * The piece need not have the same reference as the matching one in the list, but they must be logical equivalents.
	 * (a comparision using equals() must return true)
	 * @param pieces	the list to rearrange
	 * @param piece	the piece with the wrong index
	 */
	void rearrange(List<Piece> pieces, Piece piece) {
		int index = pieces.indexOf(piece);
		while (index > 0 && piece.compareTo(pieces.get(index - 1)) < 0)
			Collections.swap(pieces, index, --index);
		while (index < pieces.size() -1 && piece.compareTo(pieces.get(index + 1)) > 0)
			Collections.swap(pieces, index, ++index);
	}
}
