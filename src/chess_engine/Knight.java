package chess_engine;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(Color color, Square location) {
		super(color,location,"knight");
	}
	
	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		Square currentLocation = this.getCurrentLocation();
		Board brd = currentLocation.getBoard();
		int row = currentLocation.getRow();
		int column = currentLocation.getColumn();
		
		addMoveIfPossible(row+2,column+1,possibleMoves);
		addMoveIfPossible(row+1,column+2,possibleMoves);
		
		addMoveIfPossible(row+2,column-1,possibleMoves);
		addMoveIfPossible(row+1,column-2,possibleMoves);
		
		addMoveIfPossible(row-2,column+1,possibleMoves);
		addMoveIfPossible(row-1,column+2,possibleMoves);
		
		addMoveIfPossible(row-2,column-1,possibleMoves);
		addMoveIfPossible(row-1,column-2,possibleMoves);
		
		return possibleMoves;
	}

	private void addMoveIfPossible(int row, int column, ArrayList<Move> possibleMoves) {
		Board brd = this.getCurrentLocation().getBoard();
		
		if(!brd.isWithinABoard(row, column))
			return;
		
		Square destination = brd.getSquare(row, column);
		if(destination.isEmpty()){
			possibleMoves.add(new Move(destination,MoveType.Regular));
		}
		else{
			if(destination.getPiece().isPieceEnemy(this)){
				possibleMoves.add(new Move(destination,MoveType.Regular));
			}
		}
	}

	@Override
	public String toString() {
		return (this.getColor() == Color.White)? "\u2658":"\u265E";
	}
}
