package game;

import model.pieces.CheckersMoves;
import model.pieces.Pieces;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        assertEquals(game.getLegalMoves().length, game.getBoard().getPossiblePlays(Pieces.RED.getPieceValue()).length);
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

    @Test
    public void testPrepareNextRound_1(){
        game.setCurrentPlayer(Pieces.RED.getPieceValue());
        emptyBoard();
        game.getBoard().getBoard()[7][7] = Pieces.BLACK.getPieceValue();

        game.prepareNextRound();

        assertEquals(game.getBoardCanvas().getMessage().getText(), "RED wins.");
        assertEquals(game.getCurrentPlayer(), Pieces.BLACK.getPieceValue());

    }

    @Test
    public void testPrepareNextRound_2(){
        game.setCurrentPlayer(Pieces.RED.getPieceValue());
        emptyBoard();
        game.getBoard().getBoard()[5][5] = Pieces.BLACK.getPieceValue();
        game.getBoard().getBoard()[6][6] = Pieces.RED.getPieceValue();


        game.prepareNextRound();

        assertEquals(game.getBoardCanvas().getMessage().getText(), "BLACK must jump.");
        assertEquals(game.getCurrentPlayer(), Pieces.BLACK.getPieceValue());

    }

    @Test
    public void testPrepareNextRound_3(){
        game.setCurrentPlayer(Pieces.RED.getPieceValue());
        emptyBoard();
        game.getBoard().getBoard()[5][5] = Pieces.BLACK.getPieceValue();

        game.prepareNextRound();

        assertEquals(game.getBoardCanvas().getMessage().getText(), "BLACK's move.");
        assertEquals(game.getCurrentPlayer(), Pieces.BLACK.getPieceValue());

    }

    private void emptyBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                game.getBoard().getBoard()[row][col] = Pieces.EMPTY.getPieceValue();
            }
        }
    }
    //TODO add tests for when you are forced to jump and playMessage() method tests

}
