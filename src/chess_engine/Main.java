package chess_engine;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws Exception {
		Board brd = new Board();
		brd.init();
		initializeStandardBoard(brd);

		
		Scanner sc = new Scanner(System.in);

		boolean done = false;
		System.out.println("Enter your moves: 1 - 1 -> 1 - 2 // two first numbers indicate intial position and two numbers after -> indicate last position");
		// Implement a regex so that input only in a certain format can be provided
		while (!done) {
			System.out.println(brd);
			System.out.println("Enter your moves:");
			String line = sc.nextLine();
			line = line.replaceAll(" ", "");
			if(line.equals("exit"))
				break;
			
			String input[]  = line.split("->");
			String from []= input[0].split("-");
			String to []= input[1].split("-");

			brd.move(Integer.parseInt(from[0]), Integer.parseInt(from[1]), Integer.parseInt(to[0]), Integer.parseInt(to[1]));
		}
	}

	public static void initializeStandardBoard(Board brd) throws Exception {
		placePawns(brd, Color.Black);
		placePawns(brd, Color.White);
		placeOthePieces(brd, Color.Black);
		placeOthePieces(brd, Color.White);
	}

	public static void placeOthePieces(Board brd, Color color) throws Exception {
		int row = (Color.Black == color) ? 7 : 0;
		brd.placePieceOnTheBoard("rook", color, row, 0);
		brd.placePieceOnTheBoard("rook", color, row, 7);
		brd.placePieceOnTheBoard("knight", color, row, 1);
		brd.placePieceOnTheBoard("knight", color, row, 6);
		brd.placePieceOnTheBoard("bishop", color, row, 2);
		brd.placePieceOnTheBoard("bishop", color, row, 5);
		brd.placePieceOnTheBoard("queen", color, row, 3);
		brd.placePieceOnTheBoard("king", color, row, 4);
	}

	public static void placePawns(Board brd, Color color) throws Exception {
		int row = (Color.Black == color) ? 6 : 1;
		for (int i = 0; i < 8; i++)
			brd.placePieceOnTheBoard("pawn", color, row, i);
	}
}
