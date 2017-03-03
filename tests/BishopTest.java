package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chess_engine.Board;
import chess_engine.Color;
import chess_engine.Piece;

public class BishopTest {

	@Test
	public void testBishopMoves() throws Exception{
		Board brd = new Board();
		brd.init();
		
		brd.placePieceOnTheBoard("king", Color.White, 0, 0);
		Piece bishop = brd.placePieceOnTheBoard("bishop", Color.White, 1, 1);
		brd.move(1,1,2,0);
		brd.move(2,0,7,5);
		brd.move(7,5,5,7);
		brd.move(5, 7, 0, 2);
	}
}
