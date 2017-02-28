package chess_engine;

public class Square {
	private int row;
	private int column;
	private Board board;
	private Piece piece;

	public Square(int x, int y){
		this.row = x;
		this.column = y;
	}

	public void put(Piece piece) throws Exception{
		if(this.isEmpty()){
			this.piece = piece;
			this.piece.updateLocation(this);
		}
		else{
			if(this.piece.isPieceEnemy(piece)){
				this.piece = piece;
				this.piece.updateLocation(this);
			}
			else{
				throw new Exception("There already exists a piece of the same color at x"+this.row+" y"+this.column);
			}
		}		
	}


	public boolean equals(Square sqr){
		return this.row == sqr.getRow() && this.column == sqr.getColumn();
	}

	public Board getBoard(){
		return board;
	}

	public int getRow(){
		return row;
	}

	public int getColumn(){
		return column;
	}

	public Piece getPiece(){
		return this.piece;
	}

	/**
	 * Returns true if there is no piece on the Square
	 * @return
	 */
	public boolean isEmpty() {
		return this.piece == null;
	}
	
	/*This method is guaranteed to receive correct moves
	 */
	public void movePieceTo(Square square) throws Exception {
		if(this.isEmpty()){
			throw new Exception("No piece present at the square you want to move from");
		}else{
			if(this.piece.getName().compareTo("pawn") == 0){
				if(didPawnJumpTwoSquares(square)){
					Pawn pwn = (Pawn) this.piece;
					pwn.setJumpOccuredOnTurn(board.getTurnCount());
				}
			}
			square.put(this.piece);
			this.piece = null;
		}
	}

	private boolean isEnpassant(Square square) {
		
		return false;
	}

	private boolean didPawnJumpTwoSquares(Square square) {
		return Math.abs((this.getRow() - square.getRow())) ==2;
	}

	public void setBoard(Board board) {
		this.board = board;		
	}
	
	public void deletePiece(){
		this.piece = null;
	}
	public String toString(){
		if(this.isEmpty()){
			return "| |";
		}
		else{
			return "|"+this.piece.toString()+"|";
		}
	}
}
