package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import chess_engine.Board;
import chess_engine.Color;
import chess_engine.Piece;

public class KingTest {

	@Test
	public void testKingMoves() throws Exception{
		Board brd = new Board();
		brd.init();
		Piece king = brd.placePieceOnTheBoard("king", Color.White, 1, 2);
		brd.placePieceOnTheBoard("pawn", Color.White, 1, 1);
		brd.placePieceOnTheBoard("pawn", Color.White, 2, 3);
		brd.placePieceOnTheBoard("pawn", Color.Black, 3, 0);
		brd.placePieceOnTheBoard("pawn", Color.Black, 1, 3);
		brd.move(1, 2, 0, 3);
		brd.move(0, 3, 1, 3);
		brd.move(1, 3, 1, 4);
		brd.placePieceOnTheBoard("king", Color.Black, 3, 5);
		brd.placePieceOnTheBoard("pawn", Color.Black, 1, 5);
		brd.placePieceOnTheBoard("pawn", Color.Black, 2, 6);
		System.out.println(brd);
	}
}
