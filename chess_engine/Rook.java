package chess_engine;

import java.util.ArrayList;

public class Rook implements Piece {

	private Color color;
	private Square currentLocation;
	private String name="rook";
	private boolean movedFromInitialPosition;


	public Rook(Color color, Square location){
		this.color = color;
		this.currentLocation = location;
	}
	
	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		Board brd = this.currentLocation.getBoard();
		int row = currentLocation.getRow();
		int column = currentLocation.getColumn();
		
		
		for(int i = column+1; i < 8; i++){
			if(brd.getSquare(row, i).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
			}
			else if(!brd.getSquare(row, i).isEmpty()){
				if(brd.getSquare(row, i).getPiece().isPieceEnemy(this)){
					possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
				}
				else{
					break;
				}
			}
		}
		
		for(int i = column - 1; i >= 0; i--){
			if(brd.getSquare(row, i).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
			}
			else if(!brd.getSquare(row, i).isEmpty()){
				if(brd.getSquare(row, i).getPiece().isPieceEnemy(this)){
					possibleMoves.add(new Move(brd.getSquare(row, i), MoveType.Regular));
				}
				else{
					break;
				}
			}			
		}
		
		for(int i = row +1; i < 8; i++){
			if(brd.getSquare(i, column).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
			}
			else if(!brd.getSquare(i, column).isEmpty()){
				if(brd.getSquare(i, column).getPiece().isPieceEnemy(this)){
					possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
				}
				else{
					break;
				}
			}			
		}
		
		for(int i = row - 1; i >= 0; i--){
			if(brd.getSquare(i, column).isEmpty()){
				possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
			}
			else if(!brd.getSquare(i, column).isEmpty()){
				if(brd.getSquare(i, column).getPiece().isPieceEnemy(this)){
					possibleMoves.add(new Move(brd.getSquare(i, column), MoveType.Regular));
				}
				else{
					break;
				}
			}			
		}
		return possibleMoves;
	}

	

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void updateLocation(Square location) {
		this.currentLocation = location;
	}

	@Override
	public boolean isPieceEnemy(Piece piece) {
		return piece.getColor() != this.color;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isIt(String pieceName) {
		return this.name.compareTo(pieceName) == 0;
	}

	public String toString(){
		return (this.color == Color.White)? "\u2656" : "\u265C";
	}
	
	public void movedFromInitialPosition() {
		this.movedFromInitialPosition = true;		
	}

	public boolean isAbleToPerformCastling(){
		if(this.movedFromInitialPosition){
			return false;
		}
		else{
			if(this.color == Color.White){
				return this.currentLocation.getRow() == 0 && (this.currentLocation.getColumn() == 0 || this.currentLocation.getColumn() == 7);
			}
			else{
				return this.currentLocation.getRow() == 7 && (this.currentLocation.getColumn() == 0 || this.currentLocation.getColumn() == 7);
			}
		}
	}
}
