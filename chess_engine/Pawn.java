package chess_engine;

import java.util.ArrayList;

/**
 * Pawn implementation.
 * After Pawn is moved to a new square its inside location should be updated.
 * @author bobrin
 *
 */
public class Pawn extends Piece{
	private int jumpedTwoOnTurn;

	public Pawn(Color color, Square location){
		super(color, location, "pawn");
	}


	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		int column = getCurrentLocation().getColumn();
		Board brd = getCurrentLocation().getBoard();
		addEnpassantMoves(possibleMoves, brd, getCurrentLocation().getRow(), column);
		addJumpTwoSquaresMove(possibleMoves,brd, getCurrentLocation().getRow(),column);
		if(this.getColor() == Color.White){
			int next_row =  getCurrentLocation().getRow()+1;
			generatePawnMoves(possibleMoves, column, brd, next_row);
		}
		else{
			int next_row =  getCurrentLocation().getRow()-1;
			generatePawnMoves(possibleMoves, column, brd, next_row);			
		}
		return possibleMoves;
	}


	//verify logic step by step
	private void addEnpassantMoves(ArrayList<Move> possibleMoves, Board brd, int row, int column) {
		int row_of_possible_enemy = this.getCurrentLocation().getRow();
		int column_of_possible_enemy = this.getCurrentLocation().getColumn();
		if(this.getCurrentLocation().getColumn() != 0 && this.getCurrentLocation().getColumn() != 7){
			addEnpassantMove(possibleMoves, brd, row_of_possible_enemy, column_of_possible_enemy-1);
			addEnpassantMove(possibleMoves, brd, row_of_possible_enemy, column_of_possible_enemy+1);
		}
		else if(this.getCurrentLocation().getColumn() == 0){
			addEnpassantMove(possibleMoves, brd, this.getCurrentLocation().getRow(), this.getCurrentLocation().getColumn()+1);
		}
		else{
			addEnpassantMove(possibleMoves, brd, this.getCurrentLocation().getRow(), this.getCurrentLocation().getColumn()-1);
		}
	}

	private void addEnpassantMove(ArrayList<Move> possibleMoves, Board brd, int row_enemy, int column_enemy) {
		if(this.getColor() == Color.White && this.getCurrentLocation().getRow() == 4){
			if(isEnemyPawnPresent(row_enemy,column_enemy)){
				Pawn enemy_pawn = (Pawn) brd.getSquare(row_enemy, column_enemy).getPiece();
				if(enemyPawnJumpedTwoOneTurnAgo(brd, enemy_pawn)){
					possibleMoves.add(new Move(brd.getSquare(row_enemy + 1, column_enemy),MoveType.Enpassant));
				}
			}	
		}
		else if(this.getColor() == Color.Black && this.getCurrentLocation().getRow() == 3){
			if(isEnemyPawnPresent(row_enemy,column_enemy)){
				Pawn enemy_pawn = (Pawn) brd.getSquare(row_enemy, column_enemy).getPiece();
				if(enemyPawnJumpedTwoOneTurnAgo(brd, enemy_pawn)){
					possibleMoves.add(new Move(brd.getSquare(row_enemy - 1, column_enemy), MoveType.Enpassant));
				}
			}			
		}
	}


	private boolean enemyPawnJumpedTwoOneTurnAgo(Board brd, Pawn enemy_pawn) {
		return enemy_pawn.getjumpedTwoOnTurn() + 1 == brd.getTurnCount();
	}


	private boolean isEnemyPawnPresent(int row, int column) {
		Board brd = this.getCurrentLocation().getBoard();
		Square sqr = brd.getSquare(row, column);
		if(sqr.isEmpty()){
			return false;
		}
		else{
			return sqr.getPiece().isIt("pawn") && sqr.getPiece().isPieceEnemy(this);
		}
	}


	private void generatePawnMoves(ArrayList<Move> possibleMoves, int column, Board brd, int row) {
		if( column != 0 && column != 7){
			addForwardMove(possibleMoves, column, brd, row);				
			addDiagonalMoveInCaseOfEnemy(possibleMoves, column-1, brd, row);
			addDiagonalMoveInCaseOfEnemy(possibleMoves, column+1, brd, row);
		}
		else if(column == 0){
			addForwardMove(possibleMoves, column, brd, row);
			addDiagonalMoveInCaseOfEnemy(possibleMoves, column+1, brd, row);
		}
		else{
			addForwardMove(possibleMoves, column, brd, row);
			addDiagonalMoveInCaseOfEnemy(possibleMoves, column-1, brd, row);				
		}
	}

	private void addJumpTwoSquaresMove(ArrayList<Move> possibleMoves, Board brd, int row, int column) {
		if(row == 1 && this.getColor() == Color.White){
			if(!isPiecePresent(brd, row+1, column) && !isPiecePresent(brd, row+2, column)){
				possibleMoves.add(new Move(brd.getSquare(row+2, column),MoveType.Regular));
			}
		}
		else if(row == 6 && this.getColor() == Color.Black){
			if(!isPiecePresent(brd, row-1, column) && !isPiecePresent(brd, row-2, column)){
				possibleMoves.add(new Move(brd.getSquare(row-2, column), MoveType.Regular));
			}
		}
	}


	private void addForwardMove(ArrayList<Move> possibleMoves, int column, Board brd, int row) {
		if(!isPiecePresent(brd, row, column)){
			possibleMoves.add(new Move(brd.getSquare(row, column), MoveType.Regular));
		}
	}



	private void addDiagonalMoveInCaseOfEnemy(ArrayList<Move> possibleMoves, int column, Board brd, int row) {
		if(isPiecePresent(brd,row, column)){
			Piece piece = brd.getSquare(row, column).getPiece();
			if(isPieceEnemy(piece)){
				possibleMoves.add(new Move(brd.getSquare(row, column), MoveType.Regular));
			}
		}
	}

	public boolean isPieceEnemy(Piece piece) {
		return piece.getColor() != this.getColor();
	}

	private boolean isPiecePresent(Board brd, int row, int column) {
		return !brd.getSquare(row, column).isEmpty();
	}

	public String toString(){
		return (this.getColor() == Color.White)? "\u2659":"\u265F";
	}


	public void setJumpOccuredOnTurn(int turn){
		this.jumpedTwoOnTurn = turn;
	}

	public int getjumpedTwoOnTurn(){
		return this.jumpedTwoOnTurn;
	}
}
