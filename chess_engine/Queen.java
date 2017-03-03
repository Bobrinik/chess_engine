package chess_engine;

import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(Color color, Square location) {
		super(color, location, "queen");
	}

	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		Square currentLocation = this.getCurrentLocation();
		Board brd = currentLocation.getBoard();
		int row = currentLocation.getRow();
		int column = currentLocation.getColumn();
		
		Rook.rookMoves(possibleMoves, brd, row, column, this);
		Bishop.bishopMoves(possibleMoves, brd, row, column, this);
		return possibleMoves;
	}

	@Override
	public String toString() {
		return (this.getColor() == Color.White)? "\u2655":"\u265B";
	}
}
