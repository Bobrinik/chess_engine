package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import chess_engine.Board;
import chess_engine.Color;

public class KnightTest {

	@Test
	public void testKnightMoves() throws Exception{
		Board brd = new Board();
		brd.init();
		brd.placePieceOnTheBoard("king", Color.White, 0, 0);
		brd.placePieceOnTheBoard("knight", Color.White, 1, 1);
		brd.move(1, 1, 2, 3);
		brd.move(2,3,0,2);
		brd.move(0,2,2,1);
		brd.move(2,1,4,2);
	}
	
}
