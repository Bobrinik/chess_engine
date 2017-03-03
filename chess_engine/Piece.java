package chess_engine;

import java.util.ArrayList;

public abstract class Piece {
	private Color color;
	private Square currentLocation;
	private String name;

	public Piece(Color color, Square location, String name){
		this.color = color;
		this.currentLocation = location;
		this.name = name;
	}
	
	public abstract ArrayList<Move> getMoves();
	public abstract String toString();
	
	public Color getColor(){
		return this.color;
	}
	public void updateLocation(Square location){
		this.currentLocation = location;
	}
	public boolean isPieceEnemy(Piece piece){
		return this.color != piece.getColor();
	}
	
	public String getName(){
		return this.name;
	}
	public boolean isIt(String pieceName){
		return this.name.compareTo(pieceName) == 0;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public Square getCurrentLocation() {
		return currentLocation;
	}
}
