package model;

import helper.CheckersTestHelper;
import model.pieces.CheckersMoves;
import model.pieces.Pieces;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BoardTests extends CheckersTestHelper {

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
                if (board.getBoard()[row][col] == Pieces.RED.getPieceValue()) {
                    redCount++;
                }
                if (board.getBoard()[row][col] == Pieces.BLACK.getPieceValue()) {
                    blackCount++;
                }
                if (board.getBoard()[row][col] == Pieces.EMPTY.getPieceValue()) {
                    emptyCount++;
                }
                if (row > 2 && row < 5 && board.getBoard()[row][col] != Pieces.EMPTY.getPieceValue()) {
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
    public void testMakeMove_1() {
        CheckersMoves move = new CheckersMoves(1, 1, 4, 4);
        assertEquals(board.getBoard()[1][1], Pieces.BLACK.getPieceValue());
        assertEquals(board.getBoard()[4][4], Pieces.EMPTY.getPieceValue());

        board.makeMove(move);

        assertEquals(board.getBoard()[1][1], Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[4][4], Pieces.BLACK.getPieceValue());

    }

    @Test
    public void testMakeMove_2() {
        CheckersMoves move = new CheckersMoves(4, 4, 6, 6);
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();
        assertEquals(board.getBoard()[4][4], Pieces.BLACK.getPieceValue());
        assertEquals(board.getBoard()[5][5], Pieces.RED.getPieceValue());

        board.makeMove(move);

        assertEquals(board.getBoard()[4][4], Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[5][5], Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[6][6], Pieces.BLACK.getPieceValue());
    }

    @Test
    public void testMakeMove_3() {
        CheckersMoves move = new CheckersMoves(4, 4, 7, 7);
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();

        board.makeMove(move);

        assertEquals(board.getBoard()[4][4], Pieces.EMPTY.getPieceValue());
        assertEquals(board.getBoard()[7][7], Pieces.BLACK_QUEEN.getPieceValue());
    }

    @Test
    public void testGetJumps_1() {
        ArrayList<CheckersMoves> jumps;

        jumps = board.getJumps(Pieces.BLACK.getPieceValue(), 7, 7);
        assertEquals(jumps.size(), 0);
    }

    @Test
    public void testGetJumps_2() {
        ArrayList<CheckersMoves> jumps;
        jumps = board.getJumps(Pieces.BLACK.getPieceValue(), 0, 0);
        assertEquals(jumps.size(), 0);
    }

    @Test
    public void testGetJumps_3() {
        ArrayList<CheckersMoves> jumps;

        board.getBoard()[7][7] = Pieces.BLACK.getPieceValue();
        board.getBoard()[3][7] = Pieces.BLACK.getPieceValue();
        board.getBoard()[7][3] = Pieces.BLACK.getPieceValue();
        board.getBoard()[3][3] = Pieces.BLACK.getPieceValue();

        jumps = board.getJumps(Pieces.BLACK.getPieceValue(), 5, 5);

        assertEquals(jumps.size(), 0);
    }

    @Test
    public void testGetJumps_4() {
        ArrayList<CheckersMoves> jumps;
        board.getBoard()[7][7] = Pieces.BLACK.getPieceValue();
        board.getBoard()[3][7] = Pieces.BLACK.getPieceValue();
        board.getBoard()[7][3] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();
        board.getBoard()[3][3] = Pieces.EMPTY.getPieceValue();

        jumps = board.getJumps(Pieces.RED.getPieceValue(), 5, 5);

        assertEquals(jumps.size(), 1);
        assertEquals(jumps.get(0).fromRow, 5);
        assertEquals(jumps.get(0).fromCol, 5);
        assertEquals(jumps.get(0).toRow, 3);
        assertEquals(jumps.get(0).toCol, 3);
    }

    @Test
    public void testGetJumps_5() {
        ArrayList<CheckersMoves> jumps;
        board.getBoard()[7][7] = Pieces.BLACK.getPieceValue();
        board.getBoard()[3][7] = Pieces.BLACK.getPieceValue();
        board.getBoard()[7][3] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][4] = Pieces.EMPTY.getPieceValue();
        board.getBoard()[3][3] = Pieces.EMPTY.getPieceValue();

        jumps = board.getJumps(Pieces.RED.getPieceValue(), 5, 5);

        assertEquals(jumps.size(), 0);
    }

    @Test
    public void testGetMoves_1() {
        ArrayList<CheckersMoves> moves;
        emptyBoard(board);
        board.getBoard()[5][5] = Pieces.RED.getPieceValue();
        board.getBoard()[6][6] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][6] = Pieces.BLACK.getPieceValue();
        board.getBoard()[6][4] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();

        moves = board.getMoves(Pieces.RED.getPieceValue(), Pieces.RED_QUEEN.getPieceValue());

        assertEquals(moves.size(), 0);
    }

    @Test
    public void testGetMoves_2() {
        ArrayList<CheckersMoves> moves;
        emptyBoard(board);
        board.getBoard()[5][5] = Pieces.RED.getPieceValue();
        board.getBoard()[6][6] = Pieces.EMPTY.getPieceValue();
        board.getBoard()[4][6] = Pieces.BLACK.getPieceValue();
        board.getBoard()[6][4] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();

        moves = board.getMoves(Pieces.RED.getPieceValue(), Pieces.RED_QUEEN.getPieceValue());

        assertEquals(moves.size(), 0);

    }

    @Test
    public void testGetMoves_3() {
        ArrayList<CheckersMoves> moves;
        emptyBoard(board);
        board.getBoard()[5][5] = Pieces.RED.getPieceValue();
        board.getBoard()[6][6] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][6] = Pieces.EMPTY.getPieceValue();
        board.getBoard()[6][4] = Pieces.BLACK.getPieceValue();
        board.getBoard()[4][4] = Pieces.BLACK.getPieceValue();

        moves = board.getMoves(Pieces.RED.getPieceValue(), Pieces.RED_QUEEN.getPieceValue());

        assertEquals(moves.size(), 1);
        assertEquals(moves.get(0).fromRow, 5);
        assertEquals(moves.get(0).fromCol, 5);
        assertEquals(moves.get(0).toRow, 4);
        assertEquals(moves.get(0).toCol, 6);

    }

}
