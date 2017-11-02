package chess_engine;

public class Main {
	public static void main(String args[]) throws Exception{
		Board brd = new Board();
		brd.init();
		brd.placePieceOnTheBoard("pawn", Color.Black, 2, 2);
		System.out.println(brd);
	}
	
	public static void initializeStandardBoard(Board brd) {
		// I need to place 
		
	}
}
