package chess_engine;

import java.util.ArrayList;

public class King extends Piece {

	private boolean movedFromInitialPosition = false;

	public King(Color color, Square location) {
		super(color,location, "king");
	}

	/*
	 * TODO: 1. Create all possible moves [X] 2. Filter out moves that cannot be
	 * made because squares are under attack [X] 3. Need to implement Castling
	 * [] 4. Need to handle capturing [] (non-Javadoc)
	 * 
	 * @see chess_engine.Piece#getMoves()
	 */
	@Override
	public ArrayList<Move> getMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<>();
		Board brd = getCurrentLocation().getBoard();
		int row = getCurrentLocation().getRow();
		int column = getCurrentLocation().getColumn();

		try {
			addCastlingIfPossile(possibleMoves);
		} catch (Exception e) {
			System.err.println("Some exception in computation of castling moves in King");
			e.printStackTrace();
		}

		addSquareToMovesIfPossible(possibleMoves, brd, row + 1, column);
		addSquareToMovesIfPossible(possibleMoves, brd, row + 1, column - 1);
		addSquareToMovesIfPossible(possibleMoves, brd, row + 1, column + 1);
		addSquareToMovesIfPossible(possibleMoves, brd, row, column - 1);
		addSquareToMovesIfPossible(possibleMoves, brd, row, column + 1);
		addSquareToMovesIfPossible(possibleMoves, brd, row - 1, column - 1);
		addSquareToMovesIfPossible(possibleMoves, brd, row - 1, column);
		addSquareToMovesIfPossible(possibleMoves, brd, row - 1, column + 1);
		return possibleMoves;
	}

	/*
	 * We need to check after each move if king is under attack
	 * 
	 */

	public boolean isKingUnderAttack() {
		return this.getCurrentLocation().getBoard().isSquareUnderAttack(this.getCurrentLocation(), this.getColor());
	}

	private void addSquareToMovesIfPossible(ArrayList<Move> possibleMoves, Board brd, int row, int column) {
		if (brd.isWithinABoard(row, column)) {
			if (brd.getSquare(row, column).isEmpty()) {
				if (!brd.isSquareUnderAttack(brd.getSquare(row, column), this.getColor())) {
					possibleMoves.add(new Move(brd.getSquare(row, column), MoveType.Regular));
				}
			} else {
				if (brd.getSquare(row, column).getPiece().isPieceEnemy(this)) { 
					if (!brd.isSquareUnderAttack(brd.getSquare(row, column), this.getColor())) {
						possibleMoves.add(new Move(brd.getSquare(row, column), MoveType.Regular));
					}
				}
			}
		}
	}

	// One may not castle out of, through, or into check
	private void addCastlingIfPossile(ArrayList<Move> possibleMoves) throws Exception {
		Board brd = this.getCurrentLocation().getBoard();
		int row = this.getCurrentLocation().getRow();
		int column = this.getCurrentLocation().getColumn();

		if (this.movedFromInitialPosition) {
			return;
		}
		addCastlingMoveOnLeftSide(possibleMoves, brd, row, column);
		addCastlingMoveOnRightSide(possibleMoves, brd, row, column);
	}

	private void addCastlingMoveOnRightSide(ArrayList<Move> possibleMoves, Board brd, int row, int column) {
		if(this.rookIsPresentAndHaveNeverMovedAt(row, 7)){
			for (int c = column; c < 8; c++) {
				if (brd.getSquare(row, c).isEmpty()) {
					if (c <= column + 2) {
						if (brd.isSquareUnderAttack(brd.getSquare(row, c), this.getColor())) {
							return;
						}
					}
				}
			}
			possibleMoves.add(new Move(brd.getSquare(row, column + 2), MoveType.Castling));
		}
	}

	private void addCastlingMoveOnLeftSide(ArrayList<Move> possibleMoves, Board brd, int row, int column) {
		if (this.rookIsPresentAndHaveNeverMovedAt(row, 0)) {
			for (int c = column; c >= 0; c--) {
				if (brd.getSquare(row, c).isEmpty()) {
					if (c >= column - 2) {
						if (brd.isSquareUnderAttack(brd.getSquare(row, c), this.getColor())) {
							return;
						}
					}
				}
			}
			possibleMoves.add(new Move(brd.getSquare(row, column - 2), MoveType.Castling));
		}
	}

	public boolean rookIsPresentAndHaveNeverMovedAt(int row, int column) {
		Board brd = this.getCurrentLocation().getBoard();
		Square sqr_rook_left = brd.getSquare(row, column);

		if (!sqr_rook_left.isEmpty()) {
			if (!sqr_rook_left.getPiece().isPieceEnemy(this)) {
				if (sqr_rook_left.getPiece().isIt("rook")) {
					Rook rook = (Rook) sqr_rook_left.getPiece();
					if (rook.isAbleToPerformCastling()) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public String toString() {
		return (this.getColor() == Color.White)? "\u2654":"\u265A";
	}

	public void movedFromInitialPosition() {
		this.movedFromInitialPosition = true;
	}

	public boolean didItMoveFromInitialPosition() {
		return this.movedFromInitialPosition;
	}

}
