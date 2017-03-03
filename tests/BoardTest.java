package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chess_engine.Board;
import chess_engine.Color;

public class BoardTest {

	@Test
	public void testPawnAttackDetection() throws Exception {
		Board brd = new Board();
		brd.init();
		brd.placePieceOnTheBoard("pawn", Color.White, 1, 2);
		brd.placePieceOnTheBoard("pawn", Color.Black, 2, 1);
		assertTrue(brd.isSquareUnderAttack(brd.getSquare(1, 2), Color.White));

		brd.placePieceOnTheBoard("pawn", Color.White, 1, 4);
		brd.placePieceOnTheBoard("pawn", Color.Black, 2, 5);
		assertTrue(brd.isSquareUnderAttack(brd.getSquare(2, 5), Color.Black));

		brd.placePieceOnTheBoard("pawn", Color.White, 2, 0);
		assertFalse(brd.isSquareUnderAttack(brd.getSquare(2, 0), Color.White));
	}

	@Test
	public void testImpossibiltiyOfMovesThatExposeKing() throws Exception {
		Board brd = new Board();
		brd.init();

		brd.placePieceOnTheBoard("rook", Color.Black, 1, 2);
		brd.placePieceOnTheBoard("rook", Color.White, 1, 5);
		brd.placePieceOnTheBoard("king", Color.Black, 1, 1);

		try {
			brd.move(1, 2, 2, 2);// we try to expose Black King to white rook
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCastlingMove1() throws Exception{
		Board brd = new Board();
		brd.init();
		
		brd.placePieceOnTheBoard("rook", Color.White, 0, 0);
		brd.placePieceOnTheBoard("king", Color.White, 0, 4);
		brd.placePieceOnTheBoard("rook", Color.White, 0, 7);
		
		brd.move(0, 4, 0, 2);
	}
	
	@Test
	public void testCastlingMove2() throws Exception{
		Board brd = new Board();
		brd.init();
		
		brd.placePieceOnTheBoard("rook", Color.White, 0, 0);
		brd.placePieceOnTheBoard("king", Color.White, 0, 4);
		brd.placePieceOnTheBoard("rook", Color.White, 0, 7);
		
		brd.move(0, 4, 0, 6);
	}
	@Test
	public void testCastlingMove3() throws Exception{
		Board brd = new Board();
		brd.init();
		
		brd.placePieceOnTheBoard("rook", Color.White, 0, 0);
		brd.placePieceOnTheBoard("king", Color.White, 0, 4);
		
		brd.move(0, 4, 0, 2);
	}
	
	@Test
	public void testCastlingMove4() throws Exception{
		Board brd = new Board();
		brd.init();
		
		brd.placePieceOnTheBoard("rook", Color.White, 0, 0);
		brd.placePieceOnTheBoard("king", Color.White, 0, 4);
		brd.placePieceOnTheBoard("rook", Color.Black, 2, 3);
		brd.placePieceOnTheBoard("rook", Color.White, 0, 7);
		try{
			brd.move(0, 4, 0, 2);
		}catch(Exception e){
			
		}
		brd.move(0, 4, 0, 6);
	}
	
	@Test
	public void testCastlingMove5() throws Exception{
		Board brd = new Board();
		brd.init();
		
		brd.placePieceOnTheBoard("rook", Color.White, 0, 0);
		brd.placePieceOnTheBoard("king", Color.White, 0, 4);
		brd.placePieceOnTheBoard("rook", Color.Black, 2, 4);
		try{
			brd.move(0, 4, 0, 2);
		}catch(Exception e){
			
		}
	}
}
