package model.pieces;

public enum Pieces {
    EMPTY (0),
    RED (1),
    RED_QUEEN (2),
    BLACK (3),
    BLACK_QUEEN (4);

    private final int pieceValue;

    Pieces (int pieceValue){
        this.pieceValue = pieceValue;
    }

    public int getPieceValue(){
        return pieceValue;
    }
}
