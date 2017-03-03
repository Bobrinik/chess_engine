package chess_engine;

import java.util.ArrayList;

public class Rook extends Piece {
	
	private boolean movedFromInitialPosition;


	public Rook(Color color, Square location){
		super(color, location, "rook");
	}
	

	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		Square currentLocation = this.getCurrentLocation();
		Board brd = currentLocation.getBoard();
		int row = currentLocation.getRow();
		int column = currentLocation.getColumn();
		
		rookMoves(possibleMoves, brd, row, column, this);
		return possibleMoves;
	}

	public static void rookMoves(ArrayList<Move> possibleMoves, Board brd, int row, int column, Piece present_piece) {
		assert(present_piece.isIt("queen") || present_piece.isIt("rook"));
		for(int i = column+1; i < 8; i++){
			if(brd.getSquare(row, i).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
			}
			else if(!brd.getSquare(row, i).isEmpty()){
				if(brd.getSquare(row, i).getPiece().isPieceEnemy(present_piece)){
					possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
				}
				break;
			}
		}
		
		for(int i = column - 1; i >= 0; i--){
			if(brd.getSquare(row, i).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
			}
			else if(!brd.getSquare(row, i).isEmpty()){
				if(brd.getSquare(row, i).getPiece().isPieceEnemy(present_piece)){
					possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
				}
				break;
			}			
		}
		
		for(int i = row +1; i < 8; i++){
			if(brd.getSquare(i, column).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
			}
			else if(!brd.getSquare(i, column).isEmpty()){
				if(brd.getSquare(i, column).getPiece().isPieceEnemy(present_piece)){
					possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
				}
				break;
			}			
		}
		
		for(int i = row - 1; i >= 0; i--){
			if(brd.getSquare(i, column).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
			}
			else if(!brd.getSquare(i, column).isEmpty()){
				if(brd.getSquare(i, column).getPiece().isPieceEnemy(present_piece)){
					possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
				}
				break;
			}			
		}
	}


	public String toString(){
		return (this.getColor() == Color.White)? "\u2656" : "\u265C";
	}
	
	public void movedFromInitialPosition() {
		this.movedFromInitialPosition = true;		
	}

	public boolean isAbleToPerformCastling(){
		if(this.movedFromInitialPosition){
			return false;
		}
		else{
			if(this.getColor() == Color.White){
				return this.getCurrentLocation().getRow() == 0 && (this.getCurrentLocation().getColumn() == 0 || this.getCurrentLocation().getColumn() == 7);
			}
			else{
				return this.getCurrentLocation().getRow() == 7 && (this.getCurrentLocation().getColumn() == 0 || this.getCurrentLocation().getColumn() == 7);
			}
		}
	}
}
