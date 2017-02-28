package chess_engine;

public class Move {
	private Square destination;
	private MoveType type;
	
	public Move(Square dest, MoveType type){
		this.destination = dest;
		this.type = type;
	}
	
	public Square getDestinationSquare(){
		return this.destination;
	}
	
	public MoveType getMoveType(){
		return type;
	}
	
	public boolean equals(Move move){
		return this.destination.equals(move.getDestinationSquare());
	}
	
	public String toString(){
		return this.destination.getRow()+"-"+this.destination.getColumn();
	}
}
