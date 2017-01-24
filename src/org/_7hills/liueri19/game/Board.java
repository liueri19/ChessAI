package org._7hills.liueri19.game;

import java.util.ArrayList;
import java.util.List;

public class Board {
	//protected Map<int[], Piece> pieces = new HashMap<int[], Piece>();
	protected List<Piece> pieces = new ArrayList<Piece>();
	
	public Board() {
		setUpPieces();
	}
	
	public static void main(String[] args) {
		Board board = new Board();
		board.printBoard();
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
					//pieces.put(new int[] {x, y}, new Pawn(Color.WHITE, x, y));
					pieces.add(new Pawn(Color.WHITE, x, y));
				else
					pieces.add(new Pawn(Color.BLACK, x, y));
			}
		}
		//rooks
		pieces.add(new Rook(Color.WHITE, 1, 1));
		pieces.add(new Rook(Color.WHITE, 8, 1));
		pieces.add(new Rook(Color.BLACK, 1, 8));
		pieces.add(new Rook(Color.BLACK, 8, 8));
		//knights
		pieces.add(new Knight(Color.WHITE, 2, 1));
		pieces.add(new Knight(Color.WHITE, 7, 1));
		pieces.add(new Knight(Color.BLACK, 2, 8));
		pieces.add(new Knight(Color.BLACK, 7, 8));
		//bishops
		pieces.add(new Bishop(Color.WHITE, 3, 1));
		pieces.add(new Bishop(Color.WHITE, 6, 1));
		pieces.add(new Bishop(Color.WHITE, 3, 8));
		pieces.add(new Bishop(Color.WHITE, 6, 8));
		//queens
		pieces.add(new Queen(Color.WHITE, 4, 1));
		pieces.add(new Queen(Color.BLACK, 4, 8));
		//kings
		pieces.add(new King(Color.WHITE, 5, 1));
		pieces.add(new King(Color.BLACK, 5, 8));
		
		pieces.sort(null);
	}
}
