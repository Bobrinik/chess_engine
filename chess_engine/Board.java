package chess_engine;

import java.util.ArrayList;

/**
 * You need to create and then initialize board before using it.
 * @author bobrin
 *
 */
public class Board {
	private int turn = 0;
	private Square boardSquares[][];
	private King kings[] = new King[2];

	public Board(){
		boardSquares = new Square[8][8];
		for(int row = 0; row < 8; row++){
			for(int column = 0; column < 8; column++){
				boardSquares[row][column] = new Square(row, column);
			}
		}
	}

	public void init(){
		for(int row = 0; row < 8; row++){
			for(int column = 0; column < 8; column++){
				boardSquares[row][column].setBoard(this);
			}
		}
	}

	public Square getSquare(int row, int column){
		return boardSquares[row][column];
	}

	//note that this way we can only have two kings on the board
	public Piece placePieceOnTheBoard(String pieceName, Color color, int row, int column) throws Exception {
		switch(pieceName){
		case "pawn":
			Piece pwn = new Pawn(color,boardSquares[row][column]);
			boardSquares[row][column].put(pwn);
			return pwn;
		case "king":
			Piece king = new King(color, boardSquares[row][column]);
			if(Color.White == color){
				this.kings[0] = (King) king;
			}
			else{
				this.kings[1] = (King) king;
			}
			boardSquares[row][column].put(king);
			return king;
		case "rook":
			Piece rook = new Rook(color, boardSquares[row][column]);
			boardSquares[row][column].put(rook);
			return rook;
		default:
			System.out.println("The piece that you asked is not implemented");
			return null;
		}		
	}

	public void move(int row_init, int column_init, int row_dest, int column_dest) throws Exception {

		if(boardSquares[row_init][column_init].isEmpty()){
			throw new Exception("The square you want to move piece from is empty x:"+row_init+" y:"+column_init);
		}
		
		
		//if we remove piece from its current position and king of the same color is exposed we throw an exception	
		Piece tmp = boardSquares[row_init][column_init].getPiece();
		if (!tmp.isIt("king")) {
			boardSquares[row_init][column_init].deletePiece();
			if (tmp.getColor() == Color.White) {
				if (kings[0].isKingUnderAttack()) {
					boardSquares[row_init][column_init].put(tmp);
					throw new Exception(
							"You cannot move this piece from its current position because it is going to expose White king");
				} else {
					boardSquares[row_init][column_init].put(tmp);
				}
			} else {
				if (kings[1].isKingUnderAttack()) {
					boardSquares[row_init][column_init].put(tmp);
					throw new Exception(
							"You cannot move this piece from its current position because it is going to expose Black King");
				} else {
					boardSquares[row_init][column_init].put(tmp);
				}
			}
		}
		

		ArrayList<Move> possibleMoves = getPermittedMovesOfPieceAt(row_init, column_init); 
		Move destination = getDesiredMove(row_dest,column_dest, possibleMoves);
		makeMove(row_init, column_init, destination);
		//logic for preventing castling in case we move rook or king from its initial position
		//we are going to do every time; although it might not be efficient
		if(tmp.isIt("rook")){
			((Rook) tmp).movedFromInitialPosition();
		}
		else if(tmp.isIt("king")){
			((King) tmp).movedFromInitialPosition();
		}
	}

	private ArrayList<Move> getPermittedMovesOfPieceAt(int row_init, int column_init) {
		return boardSquares[row_init][column_init].getPiece().getMoves();
	}

	private void makeMove(int row_init, int column_init, Move destination) throws Exception {
		if(destination.getMoveType() == MoveType.Regular){
			boardSquares[row_init][column_init].movePieceTo(destination.getDestinationSquare());
			incrementCount();
		}
		else if(destination.getMoveType() == MoveType.Enpassant){
			boardSquares[row_init][column_init].movePieceTo(destination.getDestinationSquare());
			int row_dst = destination.getDestinationSquare().getRow();
			int column_dst = destination.getDestinationSquare().getColumn();

			if(destination.getDestinationSquare().getPiece().getColor() == Color.White){
				boardSquares[row_dst - 1][column_dst].deletePiece();
			}
			else{
				boardSquares[row_dst - 1][column_dst].deletePiece();
			}
			incrementCount();			
		}
		else if(destination.getMoveType() == MoveType.Castling){
			boardSquares[row_init][column_init].movePieceTo(destination.getDestinationSquare());
			int column = destination.getDestinationSquare().getColumn();
			if(column_init > column){
				boardSquares[row_init][0].movePieceTo(boardSquares[row_init][column_init-1]);//move rook
			}
			else{
				boardSquares[row_init][7].movePieceTo(boardSquares[row_init][column_init+1]);	
			}
			incrementCount();
		}
	}

	//It should be impossible to move if the move would expose a king
	private Move getDesiredMove(int row_dest, int column_dest, ArrayList<Move> possibleMoves) throws Exception {
		Square destination = boardSquares[row_dest][column_dest];
		for(Move mv: possibleMoves){
			if(mv.getDestinationSquare().equals(destination)){
				return mv;
			}
		}
		throw new Exception("It is impossible to move to the destination square x_dest:"+row_dest+" y_dest:"+column_dest);
	}

	private void incrementCount(){
		this.turn++;
	}
	/**
	 * It is used for testing.
	 * @param row
	 * @param column
	 * @param piece
	 * @return
	 */
	public boolean isPiecePresent(int row, int column,Piece piece){
		return boardSquares[row][column].getPiece() == piece; //should work since we compare objects
	}

	public String showPiecesPossibleMoves(int row, int column){
		StringBuilder bldr = new StringBuilder();
		ArrayList<Move> possibleMoves = getPermittedMovesOfPieceAt(row, column);
		for(Move mv : possibleMoves){
			bldr.append(mv.getDestinationSquare().getRow()+"-"+mv.getDestinationSquare().getColumn());
			bldr.append("\n");
		}
		return bldr.toString();		
	}

	public String toString(){
		StringBuilder str = new StringBuilder();
		for(int r = 7; r >= 0; r--){
			for(int c = 0; c < 8; c++){
				str.append(boardSquares[r][c].toString());
			}
			str.append('\n');
		}
		return str.toString();		
	}

	public int getTurnCount(){
		return this.turn;
	}

	//can be modified to return a square with attacking piece to highlight
	public boolean isSquareUnderAttack(Square sqr, Color color){
		if(isThereAKingAttacking(sqr, color)){
			return true;
		}
		else if(isThereABishopAttack(sqr, color)){
			return true;
		}		
		else if(isThereARookOrQueenOrKingAttacking(sqr,color)){
			return true;
		}
		else if(isThereAKnightAttack(sqr, color)){
			return true;
		}
		else if(isPawnAttacking(sqr,color)){
			return true;
		}
		else{
			return false;
		}
	}

	private boolean isThereAKingAttacking(Square sqr, Color color) {
		int row = sqr.getRow();
		int column = sqr.getColumn();
		
		if(isThereAking(row+1,column-1,color)){
			return true;
		}
		else if(isThereAking(row+1,column,color)){
			return true;
		}
		else if(isThereAking(row+1,column+1,color)){
			return true;
		}
		else if(isThereAking(row,column-1,color)){
			return true;
		}
		else if(isThereAking(row,column+1,color)){
			return true;
		}
		else if(isThereAking(row-1,column-1,color)){
			return true;
		}
		else if(isThereAking(row-1,column,color)){
			return true;
		}
		else if(isThereAking(row+1,column+1,color)){
			return true;
		}
		return false;
	}

	private boolean isThereAking(int row, int column, Color color) {
		if(this.isWithinABoard(row,column)){
			if(!this.getSquare(row, column).isEmpty()){
				if(this.getSquare(row, column).getPiece().isIt("king")){
					if(this.getSquare(row, column).getPiece().getColor() != color){
						return true;
					}					
				}
			}
		}
		return false;
	}

	private boolean isPawnAttacking(Square sqr, Color color) {
		int row = sqr.getRow();
		int column = sqr.getColumn();	

		if(color == Color.White ){
			if(isWithinABoard(row+1, column-1)){
				if(!this.getSquare(row+1, column-1).isEmpty()){
					if(this.getSquare(row+1, column-1).getPiece().getColor() != color){
						if(this.getSquare(row+1, column-1).getPiece().isIt("pawn")){
							return true;
						}
					}
				}		
			}

			if(isWithinABoard(row+1, column)){
				if(!this.getSquare(row+1, column+1).isEmpty()){
					if(this.getSquare(row+1, column+1).getPiece().getColor() != color){
						if(this.getSquare(row+1, column+1).getPiece().isIt("pawn")){
							return true;
						}
					}
				}
			}
			return false;
		}
		else{
			if(isWithinABoard(row-1, column-1)){
				if(!this.getSquare(row-1, column-1).isEmpty()){
					if(this.getSquare(row-1, column-1).getPiece().getColor() != color){
						if(this.getSquare(row-1, column-1).getPiece().isIt("pawn")){
							return true;
						}
					}
				}		
			}

			if(isWithinABoard(row-1, column)){
				if(!this.getSquare(row-1, column+1).isEmpty()){
					if(this.getSquare(row-1, column+1).getPiece().getColor() != color){
						if(this.getSquare(row-1, column+1).getPiece().isIt("pawn")){
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	public boolean isWithinABoard(int row, int column){
		return row >= 0 && row < 8 && column >= 0 && column < 8;
	}

	private boolean isThereAKnightAttack(Square sqr, Color color) {
		Board brd = sqr.getBoard();
		int row = sqr.getRow();
		int column = sqr.getColumn();

		if(isWithinABoard(row+2, column+1)){
			if(!brd.getSquare(row+2, column+1).isEmpty()){
				if(brd.getSquare(row+2, column+1).getPiece().getColor() != color){
					return true;
				}
			}	
		}

		if(isWithinABoard(row+2, column-1)){
			if(!brd.getSquare(row+2, column-1).isEmpty()){
				if(brd.getSquare(row+2, column-1).getPiece().getColor() != color){
					return true;
				}			
			}
		}

		if(isWithinABoard(row-2, column+1)){
			if(!brd.getSquare(row-2, column+1).isEmpty()){
				if(brd.getSquare(row-2, column+1).getPiece().getColor() != color){
					return true;
				}	

			}
		}

		if(isWithinABoard(row-2, column-1)){
			if(!brd.getSquare(row-2, column-1).isEmpty()){
				if(brd.getSquare(row-2, column-1).getPiece().getColor() != color){
					return true;
				}	
			}
		}
		return false;
	}

	//I need to check for Rook, Queen
	private boolean isThereARookOrQueenOrKingAttacking(Square sqr, Color color) {
		Board brd = sqr.getBoard();
		int column = sqr.getColumn();
		int row = sqr.getRow();

		for(int r = row+1; r < 8; r++){
			if(!brd.getSquare(r, column).isEmpty()){
				if(brd.getSquare(r, column).getPiece().getColor() != color){
					if((brd.getSquare(r, column).getPiece().isIt("rook"))){
						return true;	
					}
				}
				else{
					break;
				}
			}		
		}

		for(int r = row-1; r >= 0; r--){
			if(!brd.getSquare(r, column).isEmpty()){
				if(brd.getSquare(r, column).getPiece().getColor() != color){
					if((brd.getSquare(r, column).getPiece().isIt("rook"))){
						return true;	
					}
				}
				else{
					break;
				}
			}
		}			

		for(int c = column-1; c >= 0; c--){
			if(!brd.getSquare(row, c).isEmpty()){
				if(brd.getSquare(row, c).getPiece().getColor() != color){
					if((brd.getSquare(row, c).getPiece().isIt("rook"))){
						return true;	
					}
				}
				else{
					break;
				}
			}
		}

		for(int c = column+1; c < 8; c++){
			if(!brd.getSquare(row, c).isEmpty()){
				if(brd.getSquare(row, c).getPiece().getColor() != color){
					if((brd.getSquare(row, c).getPiece().isIt("rook"))){
						return true;	
					}
				}
				else{
					break;
				}
			}
		}
		return false;
	}

	//it should check for Bishop or Queen attack
	private boolean isThereABishopAttack(Square sqr, Color color) {
		Board brd = sqr.getBoard();
		boolean isUnderAttack;

		A:for(int row = sqr.getRow()+1; row < 8; row++){//we add +1 because we don't count square where our piece is located
			for(int column = sqr.getColumn()+1; column < 8; column++){
				if(!brd.getSquare(row, column).isEmpty()){
					if(brd.getSquare(row, column).getPiece().getColor() != color){
						if(brd.getSquare(row, column).getPiece().isIt("bishop")){
							return true;
						}
						else{
							return false;
						}						
					}
					else{
						break A;
					}
				}
			}			
		}

		C:for(int row = sqr.getRow()+1; row < 8; row++){
			for(int column = sqr.getColumn()-1; column >= 0; column--){
				if(!brd.getSquare(row, column).isEmpty()){
					if(brd.getSquare(row, column).getPiece().getColor() != color){
						if(brd.getSquare(row, column).getPiece().isIt("bishop")){
							return true;
						}
						else{
							return false;
						}	
					}
					else{
						break C;
					}
				}
			}			
		}

		D:for(int row = sqr.getRow()+1; row < 8; row++){
			for(int column = sqr.getColumn()-1; column >= 0; column--){
				if(!brd.getSquare(row, column).isEmpty()){
					if(brd.getSquare(row, column).getPiece().getColor() != color){
						if(brd.getSquare(row, column).getPiece().isIt("bishop")){
							return true;
						}
						else{
							return false;
						}	
					}
					else{
						break D;
					}
				}
			}			
		}

		E:for(int row = sqr.getRow()-1; row >= 0; row--){
			for(int column = sqr.getColumn()+1; column < 8; column++){
				if(!brd.getSquare(row, column).isEmpty()){
					if(brd.getSquare(row, column).getPiece().getColor() != color){
						if(brd.getSquare(row, column).getPiece().isIt("bishop")){
							return true;
						}
						else{
							return false;
						}	
					}
					else{
						break E;
					}
				}
			}			
		}

		return false;
	}

}
