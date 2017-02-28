package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chess_engine.*;

public class RookTest {

	@Test
	public void testRook() throws Exception{
		Board brd = new Board();
		brd.init();
		
		Piece rook = brd.placePieceOnTheBoard("rook", Color.White, 1, 1);
		brd.placePieceOnTheBoard("pawn", Color.Black, 3, 1);
		brd.placePieceOnTheBoard("king", Color.White, 7, 7);
		brd.move(1, 1, 3, 1);
		assertTrue(brd.getSquare(3, 1).getPiece() == rook);
		brd.placePieceOnTheBoard("rook", Color.Black, 3, 6);
		brd.move(3, 1, 3, 6);
		assertTrue(brd.getSquare(3, 6).getPiece() == rook);
	}

}
