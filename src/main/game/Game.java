package game;

import gui.BoardCanvas;
import model.Board;
import model.pieces.CheckersMoves;
import model.pieces.Pieces;

public class Game {

    private static final int RED_PLAYER = Pieces.RED.getPieceValue();
    private static final int BLACK_PLAYER = Pieces.BLACK.getPieceValue();
    private Board board;
    private BoardCanvas boardCanvas;
    private int currentPlayer;
    private int rowSelected, colSelected;
    private CheckersMoves[] legalMoves;

    public Game() {
        boardCanvas = new BoardCanvas(this);
        board = new Board();
        newGame();
    }

    public void newGame() {
        board.setUp();
        currentPlayer = RED_PLAYER;
        legalMoves = board.getMoves(RED_PLAYER);
        //Force row selection
        rowSelected = -1;
        boardCanvas.newGame();
    }

    //Multiple end game messages
    private void gameOver(String s) {
        boardCanvas.gameOver(s);
    }

    public void clickTile(int row, int col) {
        String playerString;
        if (currentPlayer == RED_PLAYER)
            playerString = "RED";
        else
            playerString = "BLACK";

        //Check if player selected one of their pieces
        for (CheckersMoves move : legalMoves) {
            if (move.fromRow == row && move.fromCol == col) {
                rowSelected = row;
                colSelected = col;
                boardCanvas.setMessageText(playerString + "'s turn.");
                boardCanvas.repaint();
                return;
            }

        }

        //Player did not selected owned piece
        if (rowSelected < 0) {
            boardCanvas.setMessageText("Click on one of your pieces, " + playerString);
            return;
        }

        //Check if player can make selected move
        for (CheckersMoves move : legalMoves) {
            if (move.fromRow == rowSelected
                    && move.fromCol == colSelected
                    && move.toRow == row && move.toCol == col) {
                makeMove(move);
                return;
            }
        }

        boardCanvas.setMessageText("Click the square you want to move the piece to");

    }

    public void makeMove(CheckersMoves move) {

        board.makeMove(move);

        if (move.isJump()) {
            legalMoves = board.getJumpsFrom(currentPlayer, move.toRow,
                    move.toCol);
            //Force jump
            if (legalMoves != null) {
                boardCanvas.setMessageText("You must continue your play");
                //Selected tile is now the tile checker moved to
                rowSelected = move.toRow;
                colSelected = move.toCol;
                boardCanvas.repaint();
                return;
            }
        }

        playMessage();

        //Reset row selected for next play
        rowSelected = -1;
        boardCanvas.repaint();
    }

    private void playMessage() {
        if (currentPlayer == RED_PLAYER) {
            currentPlayer = BLACK_PLAYER;
            legalMoves = board.getMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("RED wins.");
            else if (legalMoves[0].isJump())
                boardCanvas.setMessageText("BLACK must jump.");
            else
                boardCanvas.setMessageText("BLACK's move.");
        } else {
            currentPlayer = RED_PLAYER;
            legalMoves = board.getMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("BLACK wins.");
            else if (legalMoves[0].isJump())
                boardCanvas.setMessageText("RED must jump.");
            else
                boardCanvas.setMessageText("RED's move.");
        }
    }

    public int getPieceAt(int row, int col) {
        return board.getPieceAt(row, col);
    }

    public CheckersMoves[] getLegalMoves() {
        return legalMoves;
    }

    public int getRowSelected() {
        return rowSelected;
    }

    public int getColSelected() {
        return colSelected;
    }

    public BoardCanvas getBoardCanvas() {
        return boardCanvas;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }

    public void setColSelected(int colSelected) {
        this.colSelected = colSelected;
    }

    public void setLegalMoves(CheckersMoves[] legalMoves) {
        this.legalMoves = legalMoves;
    }
}
