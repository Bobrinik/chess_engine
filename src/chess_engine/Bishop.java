package chess_engine;

import java.util.ArrayList;

public class Bishop extends Piece{

	public Bishop(Color color, Square location) {
		super(color, location, "bishop");
	}

	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		Square currentLocation = this.getCurrentLocation();
		Board brd = currentLocation.getBoard();
		int row = currentLocation.getRow();
		int column = currentLocation.getColumn();
		
		bishopMoves(possibleMoves, brd, row, column, this);
		return possibleMoves;
	}

	public static void bishopMoves(ArrayList<Move> possibleMoves, Board brd, int row, int column, Piece present_piece) {
		assert(present_piece.isIt("bishop") || present_piece.isIt("queen"));
		for(int i = row+1; i  < 8;i++){
			for(int j = column+1; j < 8; j++){
				if(!brd.isWithinABoard(i, j))
					break;
				if(brd.getSquare(i, j).isEmpty()){
					possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
				}else{
					if(brd.getSquare(i, j).getPiece().isPieceEnemy(present_piece)){
						possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
					}
					break;					
				}
			}			
		}
		
		for(int i = row-1; i  >= 0;i--){
			for(int j = column-1; j >= 0; j--){
				if(!brd.isWithinABoard(i, j))
					break;
				if(brd.getSquare(i, j).isEmpty()){
					possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
				}else{
					if(brd.getSquare(i, j).getPiece().isPieceEnemy(present_piece)){
						possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
					}
					break;					
				}				
			}			
		}
		
		for(int i = row+1; i  < 8;i++){
			for(int j = column-1; j >= 0; j--){
				if(!brd.isWithinABoard(i, j))
					break;
				if(brd.getSquare(i, j).isEmpty()){
					possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
				}else{
					if(brd.getSquare(i, j).getPiece().isPieceEnemy(present_piece)){
						possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
					}
					break;					
				}				
			}			
		}
		
		for(int i = row-1; i >= 0;i--){
			for(int j = column+1; j < 8; j++){
				if(!brd.isWithinABoard(i, j))
					break;
				if(brd.getSquare(i, j).isEmpty()){
					possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
				}else{
					if(brd.getSquare(i, j).getPiece().isPieceEnemy(present_piece)){
						possibleMoves.add(new Move(brd.getSquare(i, j), MoveType.Regular));
					}
					break;					
				}				
			}			
		}
	}

	@Override
	public String toString() {
		return (this.getColor() == Color.White)?"\u2657":"\u265D";
	}

}
