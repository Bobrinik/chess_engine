package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chess_engine.Board;
import chess_engine.Color;
import chess_engine.Piece;

public class PawnTest {

	/*You may print board in a user friendly format by doing:
	 *System.out.println(board.toString());
	 *System.out.println(board.showPiecesPossibleMoves(5, 1)); 
	 */
	@Test
	public void testPawnBasicMoves() throws Exception {
		Board board = new Board();
		board.init();
		
		board.placePieceOnTheBoard("king", Color.White, 0, 0);
		board.placePieceOnTheBoard("king", Color.Black, 7, 7);
		
		Piece pwn1 = board.placePieceOnTheBoard("pawn",Color.White, 1,1);
		Piece pwn2_black = board.placePieceOnTheBoard("pawn",Color.Black, 3,2);
		board.move(1,1,2,1);

		assertTrue(board.isPiecePresent(2, 1, pwn1));
		//Pawn inside location is not changed
		board.move(2, 1, 3, 2);
		assertTrue(board.isPiecePresent(3, 2, pwn1));

		Piece pwn3_black = board.placePieceOnTheBoard("pawn",Color.Black, 4,1);
		board.move(3, 2, 4, 1);
		assertTrue(board.isPiecePresent(4, 1, pwn1));

		Piece pwn4_black = board.placePieceOnTheBoard("pawn",Color.Black, 5,0);
		Piece pwn5_black = board.placePieceOnTheBoard("pawn",Color.Black, 5,2);
		board.move(4, 1, 5, 1);
		assertTrue(board.isPiecePresent(5, 1, pwn1));
	}

	/*Make showPiecePossibleMoves return array of possible moves and check against this instead
	 * of making a move and see if the piece has actually done the move. 
	 */
	@Test
	public void testTwoSquareMove() throws Exception{
		Board board = new Board();
		board.init();
		
		board.placePieceOnTheBoard("king", Color.White, 0, 0);
		board.placePieceOnTheBoard("king", Color.Black, 7, 7);
		
		Piece pwn1 = board.placePieceOnTheBoard("pawn",Color.White, 1,1);
		
		board.move(1,1,3,1);
		assertTrue(board.isPiecePresent(3, 1, pwn1));

		Piece pwn2_black = board.placePieceOnTheBoard("pawn",Color.Black, 6,1);
		System.out.println(board.showPiecesPossibleMoves(6, 1));
		board.move(6,1,5,1);
		assertTrue(board.isPiecePresent(5, 1, pwn2_black));
	}

	@Test
	public void testEnPassant() throws Exception{
		Board board = new Board();
		board.init();
		
		board.placePieceOnTheBoard("king", Color.White, 0, 0);
		board.placePieceOnTheBoard("king", Color.Black, 7, 7);
		
		Piece pwn1_white = board.placePieceOnTheBoard("pawn",Color.White, 4,1);
		Piece pwn1_black = board.placePieceOnTheBoard("pawn",Color.Black, 6,0);
		board.move(6,0,4,0);
		board.move(4,1,5,0);
		assertTrue(board.isPiecePresent(5, 0, pwn1_white));
		assertFalse(board.isPiecePresent(4, 0, pwn1_black));
		
		Piece pwn2_white = board.placePieceOnTheBoard("pawn",Color.White, 4,6);
		Piece pwn2_black = board.placePieceOnTheBoard("pawn",Color.Black, 6,5);
		Piece pwn3_black = board.placePieceOnTheBoard("pawn",Color.Black, 6,7);
		Piece pwn4_black = board.placePieceOnTheBoard("pawn",Color.Black, 6,3);
		Piece pwn5_white = board.placePieceOnTheBoard("pawn",Color.White, 4,4);
		
		board.move(6,5,4,5);
		board.move(6,7,4,7);
		System.out.println(board);
		board.move(4,6,5,7);
		board.move(6,3,4,3);
		board.move(4,4,5,3);
		System.out.println(board);
		assertTrue(board.isPiecePresent(5, 3, pwn5_white));
		assertTrue(board.isPiecePresent(5, 7, pwn2_white));
	}

}
