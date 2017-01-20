package org._7hills.liueri19;

import java.util.ArrayList;
import java.util.List;

public class Board {
	protected List<Piece> pieces = new ArrayList<Piece>();
	
	public Board() {
		setUpPieces();
	}
	
	public void setUpPieces() {
		pieces.add(new Castle(0));
	}
}
