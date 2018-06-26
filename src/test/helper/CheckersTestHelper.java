package helper;

import model.Board;
import model.pieces.Pieces;

public abstract class CheckersTestHelper {

    public void emptyBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board.getBoard()[row][col] = Pieces.EMPTY.getPieceValue();
            }
        }
    }
}
