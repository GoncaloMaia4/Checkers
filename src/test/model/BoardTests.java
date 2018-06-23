package model;

import model.pieces.CheckersMoves;
import model.pieces.Pieces;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTests {
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
        board.setUp();
    }

    @Test
    public void testSetUp() {
        int redCount = 0;
        int blackCount = 0;
        int emptyCount = 0;
        int wrongCount = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(board.getBoard()[row][col] == Pieces.RED.getPieceValue()){
                    redCount++;
                }
                if(board.getBoard()[row][col] == Pieces.BLACK.getPieceValue()){
                    blackCount++;
                }
                if(board.getBoard()[row][col] == Pieces.EMPTY.getPieceValue()){
                    emptyCount++;
                }
                if(row > 2 && row < 5 && board.getBoard()[row][col] != Pieces.EMPTY.getPieceValue()){
                    wrongCount++;
                }
            }
        }
        assertEquals(redCount, 12);
        assertEquals(blackCount, 12);
        assertEquals(emptyCount, 40);
        assertEquals(wrongCount, 0);
    }

    @Test
    public void testMakeMove_1(){
        CheckersMoves move = new CheckersMoves(1,1,4,4);
        assertEquals(board.getBoard()[1][1],Pieces.BLACK.getPieceValue());
        assertEquals(board.getBoard()[4][4],Pieces.EMPTY.getPieceValue());

        board.makeMove(move);

        assertEquals(board.getBoard()[1][1],Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[4][4],Pieces.BLACK.getPieceValue());

    }

    @Test
    public void testMakeMove_2(){
        CheckersMoves move = new CheckersMoves(4,4,6,6);
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();
        assertEquals(board.getBoard()[4][4],Pieces.BLACK.getPieceValue());
        assertEquals(board.getBoard()[5][5],Pieces.RED.getPieceValue());

        board.makeMove(move);

        assertEquals(board.getBoard()[4][4],Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[5][5],Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[6][6],Pieces.BLACK.getPieceValue());
    }

    @Test
    public void testMakeMove_3(){
        CheckersMoves move = new CheckersMoves(4,4,7,7);
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();

        board.makeMove(move);

        assertEquals(board.getBoard()[4][4],Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[7][7],Pieces.BLACK_QUEEN.getPieceValue());
    }


    //TODO More testing on board methods
}
