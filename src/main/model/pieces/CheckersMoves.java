package model.pieces;

public class CheckersMoves {
    // Original position.
    public int fromRow, fromCol;
    // Future Position.
    public int toRow, toCol;

    public CheckersMoves(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    // Check if move is jump. (More than 1 square/tile)
    public boolean isJump() {
        return (fromRow - toRow == 2 || fromRow - toRow == -2);
    }
}
