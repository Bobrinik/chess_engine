package tests;

import org.junit.Test;

import chess_engine.Board;
import chess_engine.Color;
import chess_engine.Piece;

public class QueenTest {

	/*Because movement logic for Chess has been implemented before we don't test it here. 
	 * 
	 */
	@Test
	public void testQueenMoves() throws Exception{
		Board brd = new Board();
		brd.init();
		brd.placePieceOnTheBoard("king", Color.White, 0, 0);
		brd.placePieceOnTheBoard("queen", Color.White, 1, 1);
		brd.move(1, 1, 5, 1);
		brd.move(5, 1, 4, 2);
		System.out.print(brd);
	}
}
