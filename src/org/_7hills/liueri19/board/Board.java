package org._7hills.liueri19.board;

import org._7hills.liueri19.algorithm.Algorithm;
import org._7hills.liueri19.algorithm.BruteForce;
import org._7hills.liueri19.algorithm.Position;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Board where PieceType rest on and the board would be played.
 *
 * @author liueri19
 */
public class Board {
	//9*9 array for the board, Piece should be accessed with pieces[rank][file]
	private final Piece[][] pieces = new Piece[9][9];
	private King whiteKing, blackKing;
	private boolean gameEnded = false;
	private boolean drawSuggested = false;
	/**
	 * 1 white win, -1 black win, 0 draw
	 */
	private int gameResult;
	private List<Move> history = new ArrayList<>();

	/**
	 * the Algorithm to use
	 */
	private Algorithm ALGORITHM;
	private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	/**
	 * Constructs a chess board with standard setup.
	 */
	public Board() {
		setupPieces();
	}

//	/**
//	 * Constructs a chess board with setup optional.
//	 *
//	 * @param doSetUp	option for setup pieces.
//	 */
//	public Board(boolean doSetUp) {
//		if (doSetUp)
//			setupPieces();
//	}

	public static void main(String[] args) throws Exception {
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
		String input;
		Piece piece;
		boolean whiteMove = true;

		System.out.println("Use command 'prtboard' to see a visual representation of the board.");
		System.out.println("Use command 'prthistory' to see previous moves.");
		System.out.println("White side or black side (W/B)");
		boolean playerTurn = !sc.nextLine().equalsIgnoreCase("B");    //if not "B", default white
		board.ALGORITHM = new BruteForce(board, !playerTurn);
		board.EXECUTOR.submit(board.ALGORITHM);
		while (!board.gameEnded) {
			if (playerTurn) {
				input = sc.nextLine();
				//parse input
				if (input.equals("prtboard")) {
					board.printBoard();
					continue;
				}
				else if (input.equals("prthistory")) {
					board.printHistory();
					continue;
				}
				else if (input.equals("resign")) {
					board.gameEnded = true;
					if (whiteMove)
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
				else if (input.equals("O-O")) {    //castling, king side
					board.drawSuggested = false;
					King king;
					Piece rook;
					if (whiteMove) {    //acquire the pieces
						king = board.whiteKing;
						rook = board.getPieceAt(8, 1);
					}
					else {
						king = board.blackKing;
						rook = board.getPieceAt(8, 8);
					}
					if (!(rook instanceof Rook)) {
						System.out.println("The rook had been moved");
						continue;
					}

					//move
					board.move(new Castling(king, (Rook) rook));
				}
				else if (input.equals("O-O-O")) {    //castling, queen side
					board.drawSuggested = false;
					King king;
					Piece rook;
					if (whiteMove) {    //acquire the pieces
						king = board.whiteKing;
						rook = board.getPieceAt(1, 1);
					}
					else {
						king = board.blackKing;
						rook = board.getPieceAt(1, 8);
					}
					if (!(rook instanceof Rook)) {
						System.out.println("The rook had been moved");
						continue;
					}

					board.move(new Castling(king, (Rook) rook));
				}
				else if (input.length() == 4 || input.length() == 5) {    //a move
					board.drawSuggested = false;
					char fileO = input.charAt(0);
					int rankO = Character.getNumericValue(input.charAt(1));

					if (Character.toString(fileO).matches("[abcdefgh]")) {    //if the first character is a, b, c, d, e, f, g, or h
						piece = board.getPieceAt(fileO, rankO);
						if (piece == null) {
							System.out.println("No piece on the selected square");
							continue;
						}
						//a valid piece selected, validate turn
//						if (piece.getColor() && !whiteMove) {
//							System.out.println("Cannot move white piece on black's turn");
//							continue;
//						} else if (!piece.getColor() && whiteMove) {
//							System.out.println("Cannot move black piece on white's turn");
//							continue;
//						}
						if (piece.getColor() != whiteMove) {
							System.out.println("Cannot ");
							continue;
						}

						//get the target square
						char fileD = input.charAt(2);
						int rankD = Character.getNumericValue(input.charAt(3));
						int[] to = new int[]{parseFile(fileD), rankD};

						Move m = new Move(piece, piece.getSquare(), to);
						if (input.length() == 5 && piece instanceof Pawn) {    //promotion
							PieceType type = PieceType.getInstance(input.charAt(4));
							m = new Promotion((Pawn) piece, to, type);
						}
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
			}
			else {    //if not userInput
				//wait for 3 minutes (or 5 minutes max)
//				board.ALGORITHM.
				//use continue to loop until a move is returned from algorithm?
			}
			playerTurn = !playerTurn;
			whiteMove = !whiteMove;	//merge two booleans into one?
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
	 * @param file the char representation of a file, always lower-case
	 * @return the int representation of the input file
	 */
	public static int parseFile(char file) {
		int fileNum = 0;
		switch (file) {
			case 'a':
				fileNum = 1;
				break;
			case 'b':
				fileNum = 2;
				break;
			case 'c':
				fileNum = 3;
				break;
			case 'd':
				fileNum = 4;
				break;
			case 'e':
				fileNum = 5;
				break;
			case 'f':
				fileNum = 6;
				break;
			case 'g':
				fileNum = 7;
				break;
			case 'h':
				fileNum = 8;
				break;
		}
		return fileNum;
	}

	/**
	 * Parse the file represented as an int into a char.
	 *
	 * @param file the int representation of a file
	 * @return the char representation of the input file
	 */
	public static char parseFile(int file) {
		char fileNum = '0';
		switch (file) {
			case 1:
				fileNum = 'a';
				break;
			case 2:
				fileNum = 'b';
				break;
			case 3:
				fileNum = 'c';
				break;
			case 4:
				fileNum = 'd';
				break;
			case 5:
				fileNum = 'e';
				break;
			case 6:
				fileNum = 'f';
				break;
			case 7:
				fileNum = 'g';
				break;
			case 8:
				fileNum = 'h';
				break;
		}
		return fileNum;
	}

//	/**
//	 * Change white's turn to black's turn or vice versa. This method also updates all pieces.
//	 */
//	private void changeTurn() {
//		updatePieces(false);
//		whiteMove = !whiteMove;
//	}

	/**
	 * Returns a list containing the pieces on the board. Note: the returned list will not be updated with
	 * the board, nor will modifying the returned list change the state of the board.
	 *
	 * @return a list containing all the pieces on this board
	 */
	public List<Piece> getPieces() {    //store in a variable before iterating over a loop
//		return new ArrayList<>(pieces);
//		return pieces;
		List<Piece> piecesList = new ArrayList<>();
		for (Piece[] rank : pieces) {
			for (Piece piece : rank) {
				if (piece != null)
					piecesList.add(piece);
			}
		}
		return piecesList;
	}

	/**
	 * Returns all legal moves of all pieces of the specified color.
	 *
	 * @param color the color of the side to collect legal moves from
	 * @return moves of all pieces of the specified color
	 */
	public Set<Move> getLegalMoves(boolean color) {
		Set<Move> moves = new HashSet<>();
		for (Piece p : getPieces()) {
			if (p.getColor() == color)
				moves.addAll(p.getLegalMoves());
		}
		return moves;
	}

	/**
	 * Returns the piece on the specified square.
	 *
	 * @param file the file of the piece represented as char
	 * @param rank the rank of the piece represented as int
	 * @return the piece on the specified square, or null if the specified square has no piece
	 */
	Piece getPieceAt(char file, int rank) {
		return getPieceAt(parseFile(file), rank);
	}

	/**
	 * Returns the piece on the specified square.
	 *
	 * @param file the file of the piece represented as int
	 * @param rank the rank of the piece represented as int
	 * @return the piece on the specified square, or null if the specified square has no piece
	 * or null if the specified square is not on the board.
	 */
	Piece getPieceAt(int file, int rank) {
//		PLACEHOLDER.modifySquare(file, rank);
//		return getPiece(PLACEHOLDER);
		if (file > 8 || rank > 8 || file < 0 || rank < 0)
			return null;
		return pieces[rank][file];
	}

	/**
	 * Returns the piece on the specified square.
	 *
	 * @param square the file and rank as an int array
	 * @return the piece on the specified square, or null if the specified square has no piece
	 */
	Piece getPieceAt(int[] square) {
		return getPieceAt(square[0], square[1]);
	}

	/**
	 * Removes the specified piece from the board.
	 *
	 * @param piece the Piece to remove
	 */
	void removePiece(Piece piece) {
		removePiece(piece.getFile(), piece.getRank());
	}

	/**
	 * Removes the piece at the specified file and rank.
	 *
	 * @param file the file of the piece
	 * @param rank the rank of the piece
	 * @return the removed Piece, or null if no piece is on the specified square
	 */
	Piece removePiece(int file, int rank) {
		Piece piece = pieces[rank][file];
		pieces[rank][file] = null;
		return piece;
	}

//	/**
//	 * Removes the piece at the specified square.
//	 * @param square	the square of the piece
//	 * @return	the removed Piece, or null if no piece is on the specified square
//	 */
//	Piece removePiece(int[] square) {
//		return removePiece(square[0], square[1]);
//	}

	/**
	 * Add a piece to this board at where calling {@code getSquare()} on the specified
	 * piece would return.
	 *
	 * @param piece the Piece to add
	 * @throws IllegalArgumentException if the square returned by {@code getSquare()}
	 *                                  is occupied
	 */
	void addPiece(Piece piece) throws IllegalArgumentException {
		addPiece(piece, piece.getFile(), piece.getRank());
	}

	/**
	 * Add a piece to this board at the specified square. This method also sets the
	 * square stored in the piece to the specified square.
	 *
	 * @param piece the piece to add
	 * @param file  the file to add the piece to
	 * @param rank  the rank to add the piece to
	 * @throws IllegalArgumentException if the square returned by {@code getSquare()}
	 *                                  is occupied
	 */
	void addPiece(Piece piece, int file, int rank) throws IllegalArgumentException {
		if (pieces[rank][file] != null)
			throw new IllegalArgumentException("Specified square (" + file + ", " + rank + ") already occupied");
		piece.setSquare(file, rank);
		pieces[rank][file] = piece;
	}


	//use the cache if correctness can be guaranteed (both for the checkMove algorithm and concurrency)
//	private Position positionCache = null;

	/**
	 * Generate a new Position object representing the current position of this board.
	 * @return	a Position representing this board
	 */
	public Position getPosition() {
//		if (positionCache == null)
//			positionCache = new Position(this);
//		return positionCache;
		return new Position(this);
	}

	/**
	 * Move the piece on {@code fileO, rankO} to square {@code fileD, rankD}.
	 *
	 * @param fileO the original file the piece is on
	 * @param rankO the original rank the piece is on
	 * @param fileD the destination file to move the piece to
	 * @param rankD the destination rank to move the piece to
	 * @throws IllegalArgumentException if the origin square has no piece on it or if the destination
	 *                                  square is occupied
	 */
	void movePiece(int fileO, int rankO, int fileD, int rankD) throws IllegalArgumentException {
		Piece pieceO = removePiece(fileO, rankO);
		if (pieceO == null)
			throw new IllegalArgumentException("No piece on the specified square (" + fileO + ", " + rankO + ")");
		addPiece(pieceO, fileD, rankD);
	}

//	/**
//	 * Move the piece on {@code squareO} to {@code squareD}.
//	 * @param squareO	the original square the piece is on
//	 * @param squareD	the destination square to move the piece to
//	 * @throws IllegalArgumentException if the origin square has no piece on it or if the destination
//	 * square is occupied
//	 */
//	void movePiece(int[] squareO, int[] squareD) throws IllegalArgumentException {
//		movePiece(squareO[0], squareO[1], squareD[0], squareD[1]);
//	}

	/**
	 * Move the specified piece to fileD and rankD.
	 *
	 * @param piece the piece to move
	 * @param fileD the destination file to move the piece to
	 * @param rankD the destination rank to move the piece to
	 * @throws IllegalArgumentException if the origin square has no piece on it or if the destination
	 *                                  square is occupied
	 */
	void movePiece(Piece piece, int fileD, int rankD) throws IllegalArgumentException {
		movePiece(piece.getFile(), piece.getRank(), fileD, rankD);
	}

	/**
	 * Prints a list of played moves to the console.
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
//		int index = 0;
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
				Piece piece = getPieceAt(file, rank);
				if (piece != null) {
					if (white)
						System.out.printf("| %s ", piece.toBriefString());
					else
						System.out.printf("|/%s/", piece.toBriefString());
				}
				else {
					if (white)
						System.out.print(whiteSpace);
					else
						System.out.print(blackSpace);
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
	void setupPieces() {
		//pawns
		for (int y = 2; y < 8; y += 5) {
			for (int x = 1; x < 9; x++) {
				if (y == 2)
					addPiece(new Pawn(this, true, x, y));
				else
					addPiece(new Pawn(this, false, x, y));
			}
		}
		//rooks
		addPiece(new Rook(this, true, 1, 1));
		addPiece(new Rook(this, true, 8, 1));
		addPiece(new Rook(this, false, 1, 8));
		addPiece(new Rook(this, false, 8, 8));
		//knights
		addPiece(new Knight(this, true, 2, 1));
		addPiece(new Knight(this, true, 7, 1));
		addPiece(new Knight(this, false, 2, 8));
		addPiece(new Knight(this, false, 7, 8));
		//bishops
		addPiece(new Bishop(this, true, 3, 1));
		addPiece(new Bishop(this, true, 6, 1));
		addPiece(new Bishop(this, false, 3, 8));
		addPiece(new Bishop(this, false, 6, 8));
		//queens
		addPiece(new Queen(this, true, 4, 1));
		addPiece(new Queen(this, false, 4, 8));
		//kings
		addPiece(whiteKing = new King(this, true, 5, 1));
		addPiece(blackKing = new King(this, false, 5, 8));

//		//test for illegal move check
//		addPiece(new Rook(this, false, 4, 7));
//		addPiece(new Bishop(this, true, 5, 2));

//		//test for en passant
//		addPiece(new Pawn(this, true, 1, 2));
//		addPiece(new Pawn(this, false, 2, 4));
//		addPiece(new Pawn(this, true, 8, 5));
//		addPiece(new Pawn(this, false, 7, 7));

		//test for promotion
//		addPiece(new Pawn(this, true, 1, 6));
//		addPiece(new Pawn(this, false, 5, 3));
//		addPiece(new Knight(this, false, 2, 8));
//		addPiece(new Knight(this, true, 8, 1));

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
	 * Returns the Move object at the specified index in history.
	 * Note: the index starts at 0, and each Move will occupy an index in history,
	 * therefore the index is not the same as the move number in standard transcript.
	 *
	 * @param moveNum the index of the desired Move object
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
		return history.size();    //starts with 0
	}

	/**
	 * Returns true if the specified square is being attacked by the opponent of <code>color</code>, and false otherwise.<br>
	 * <code>isSquareAttacked(true, 1, 1)</code> returns true if square A1 is being attacked by black;
	 * likewise, <code>isSquareAttacked(false, 1, 1)</code> returns true if square A1 is being attacked by white.
	 *
	 * @param color the color of the friendly side
	 * @param file  the file of the square
	 * @param rank  the rank of the square
	 * @return true if the specified square is being attacked by the opponent of <code>color</code>, and false otherwise
	 */
	public boolean isSquareAttacked(boolean color, int file, int rank) {    //TODO: not very efficient
		/*
		place color pieces on the specified square and check if the piece could reach a
		piece of the same type but of different color.
		 */
		for (Piece p : getPieces()) {
			if (p != null &&
					p.getColor() != color &&
					p.isThreatening(new Move(p, p.getSquare(), new int[]{file, rank})))
				return true;
		}
		return false;
	}

	/**
	 * Returns true if the King of the specified color is in check, false otherwise.
	 *
	 * @param color the color of the specified king
	 * @return true if the King of the specified color is in check, false otherwise
	 */
	public boolean isInCheck(boolean color) {
		return color ?
				isSquareAttacked(color, whiteKing.getFile(), whiteKing.getRank())
				: isSquareAttacked(color, blackKing.getFile(), blackKing.getRank());
	}

	/**
	 * Call <code>updatePiece()</code> on all Piece objects currently on the board.
	 *
	 * @param threatsOnly true to update only threats, false to update threats and legal moves
	 */
	void updatePieces(boolean threatsOnly) {
		List<Piece> pieceList = getPieces();
		for (Piece piece : pieceList) {
			if (!(piece instanceof King))
				piece.updatePiece(threatsOnly);
		}
		//kings should be updated last (shouldn't really matter)
		whiteKing.updatePiece(threatsOnly);
		blackKing.updatePiece(threatsOnly);

		//check for checkmate and draw
		if (!threatsOnly) {
			boolean whiteCheckmate, blackCheckmate, isWhiteInCheck, isBlackInCheck, isDrawW, isDrawB;
			whiteCheckmate = blackCheckmate = isDrawW = isDrawB = true;
			isWhiteInCheck = isInCheck(true);    //isInCheck() is expensive
			isBlackInCheck = isInCheck(false);
			for (Piece p : pieceList) {
				boolean noLegalMoves = p.getLegalMoves().isEmpty();
				if (p.getColor()) {
					if (whiteCheckmate)
						whiteCheckmate = noLegalMoves && isWhiteInCheck;
					if (isDrawW)
						isDrawW = noLegalMoves;
				}
				else {
					if (blackCheckmate)
						blackCheckmate = noLegalMoves && isBlackInCheck;
					if (isDrawB)
						isDrawB = noLegalMoves;
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
			if (isDrawW || isDrawB) {
				gameEnded = true;
				gameResult = 0;
			}
		}
	}

	/**
	 * Execute the specified Move object. Returns true if the move described is legal, or false otherwise.
	 *
	 * @param move the Move to execute
	 * @return true if the move described is legal, false otherwise
	 */
	public boolean move(Move move) {
		Piece init = move.getInit();
		if (init.isLegalMove(move)) {
			List<Move> moves = init.getLegalMoves();
			//get the move from legal moves generated from Piece, Move passed in from main have null in subject field
			Move generatedMove = moves.get(moves.indexOf(move));
			if (move instanceof Promotion)
				move.execute(this);
			else
				generatedMove.execute(this);
			history.add(move);
			updatePieces(false);
			return true;
		}
		return false;
	}
}