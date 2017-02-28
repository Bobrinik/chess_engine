package chess_engine;

import java.util.ArrayList;

public interface Piece {
	public ArrayList<Move> getMoves();
	public Color getColor();
	public void updateLocation(Square location);
	public boolean isPieceEnemy(Piece piece);
	public String getName();
	public boolean isIt(String pieceName);
}
