package game;

import model.pieces.CheckersMoves;
import model.pieces.Pieces;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTests {

    private Game game;

    @Before
    public void setUp(){
        game = new Game();
        game.newGame();
    }

    @Test
    public void testNewGame(){
        game.newGame();
        assertEquals(game.getCurrentPlayer(), Pieces.RED.getPieceValue());
        assertEquals(game.getRowSelected(), -1);
        assertEquals(game.getLegalMoves().length, game.getBoard().getMoves(Pieces.RED.getPieceValue()).length);
    }

    @Test
    public void testClickTile_1(){
        CheckersMoves[] movesArray = new CheckersMoves[1];
        movesArray[0] = new CheckersMoves(1,1,2,2);
        game.setLegalMoves(movesArray);

        game.clickTile(1,1);
        assertEquals(game.getRowSelected(), 1);
        assertEquals(game.getColSelected(), 1);
    }

    @Test
    public void testClickTile_2(){
        CheckersMoves[] movesArray = new CheckersMoves[1];
        movesArray[0] = new CheckersMoves(1,1,2,2);
        game.setLegalMoves(movesArray);

        game.clickTile(3,3);
        assertEquals(game.getBoardCanvas().getMessage().getText(),"Click on one of your pieces, RED");
    }

    @Test
    public void testClickTile_3(){
        CheckersMoves[] movesArray = new CheckersMoves[1];
        movesArray[0] = new CheckersMoves(1,1,2,2);
        game.setLegalMoves(movesArray);
        game.setRowSelected(1);
        game.setColSelected(1);

        game.clickTile(3,3);
        assertEquals(game.getBoardCanvas().getMessage().getText(),"Click the square you want to move the piece to");
    }

    @Test
    public void testMakeMove_1(){
        CheckersMoves move = new CheckersMoves(1,1,2,2);

        game.makeMove(move);
        assertEquals(game.getRowSelected(), -1);

    }

    @Test
    public void testMakeMove_2(){
        CheckersMoves move = new CheckersMoves(1,1,3,3);
        CheckersMoves[] movesArray = new CheckersMoves[1];
        movesArray[0] = new CheckersMoves(1,1,2,2);
        game.setLegalMoves(movesArray);

        game.makeMove(move);
        assertEquals(move.isJump(),true);
        assertEquals(game.getRowSelected(), -1);
    }

    //TODO add tests for when you are forced to jump and playMessage() method tests

}
