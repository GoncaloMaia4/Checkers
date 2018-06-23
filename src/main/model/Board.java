package model;

import model.pieces.CheckersMoves;
import model.pieces.Pieces;

import java.util.ArrayList;

public class Board {

    private static final int RED_PLAYER = Pieces.RED.getPieceValue();
    private static final int BLACK_PLAYER = Pieces.BLACK.getPieceValue();

    private int[][] board;

    public Board() {
        board = new int[8][8];
    }

    public void setUp() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    if (row < 3) {
                        board[row][col] = BLACK_PLAYER;
                    } else if (row > 4) {
                        board[row][col] = RED_PLAYER;
                    }
                }
            }
        }
    }

    public int getPieceAt(int row, int col) {
        return board[row][col];
    }

    public void makeMove(CheckersMoves move) {
        int toRow = move.toRow;
        int toCol = move.toCol;
        int fromRow = move.fromRow;
        int fromCol = move.fromCol;

        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = Pieces.EMPTY.getPieceValue();

        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            //Obtain jumped piece coordinates and make it empty
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;
            board[jumpRow][jumpCol] = Pieces.EMPTY.getPieceValue();
        }

        checkIfQueen(toRow, toCol);

    }

    private void checkIfQueen(int toRow, int toCol) {
        if (toRow == 0 && board[toRow][toCol] == RED_PLAYER)
            board[toRow][toCol] = Pieces.RED_QUEEN.getPieceValue();
        if (toRow == 7 && board[toRow][toCol] == BLACK_PLAYER)
            board[toRow][toCol] = Pieces.BLACK_QUEEN.getPieceValue();
    }

    public CheckersMoves[] getMoves(int player) {
        int playerQueen = getPlayerQueen(player);

        //Check if there is jumps
        ArrayList<CheckersMoves> moves = getPossibleJumps(player, playerQueen);

        //If there is no jumps, check possible moves
        if (moves.size() == 0) {
            moves = getMoves(player, playerQueen);
        }

        //No moves, game over else return possible moves
        if (moves.size() == 0)
            return null;
        else {
            //Transformation into array
            CheckersMoves[] movesArray = new CheckersMoves[moves.size()];
            int i = 0;
            for (CheckersMoves move : moves) {
                movesArray[i] = move;
                i++;
            }
            return movesArray;
        }
    }

    private int getPlayerQueen(int player) {
        int playerQueen;
        if (player == BLACK_PLAYER) {
            playerQueen = Pieces.BLACK_QUEEN.getPieceValue();
        } else {
            playerQueen = Pieces.RED_QUEEN.getPieceValue();
        }
        return playerQueen;
    }

    private ArrayList<CheckersMoves> getPossibleJumps(int player, int playerQueen) {
        ArrayList<CheckersMoves> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == playerQueen) {
                    getJumps(player, playerQueen, row, col, moves);
                }
            }
        }
        return moves;
    }

    private ArrayList<CheckersMoves> getMoves(int player, int playerQueen) {
        ArrayList<CheckersMoves> moves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == playerQueen) {
                    if (checkMove(player, row, col, row + 1, col + 1)) {
                        moves.add(new CheckersMoves(row, col, row + 1, col + 1));
                    }
                    if (checkMove(player, row, col, row - 1, col + 1)) {
                        moves.add(new CheckersMoves(row, col, row - 1, col + 1));
                    }
                    if (checkMove(player, row, col, row + 1, col - 1)) {
                        moves.add(new CheckersMoves(row, col, row + 1, col - 1));
                    }
                    if (checkMove(player, row, col, row - 1, col - 1)) {
                        moves.add(new CheckersMoves(row, col, row - 1, col - 1));
                    }
                }
            }
        }
        return moves;
    }


    private void getJumps(int player, int playerQueen, int row, int col, ArrayList<CheckersMoves> jumps) {

        if (checkJump(player, row, col, row + 1, col + 1, row + 2, col + 2))
            jumps.add(new CheckersMoves(row, col, row + 2, col + 2));
        if (checkJump(player, row, col, row - 1, col + 1, row - 2, col + 2))
            jumps.add(new CheckersMoves(row, col, row - 2, col + 2));
        if (checkJump(player, row, col, row + 1, col - 1, row + 2, col - 2))
            jumps.add(new CheckersMoves(row, col, row + 2, col - 2));
        if (checkJump(player, row, col, row - 1, col - 1, row - 2, col - 2))
            jumps.add(new CheckersMoves(row, col, row - 2, col - 2));
    }

    public CheckersMoves[] getJumpsFrom(int player, int row, int col) {
        int playerQueen = getPlayerQueen(player);

        ArrayList<CheckersMoves> jumps = new ArrayList<>();

        getJumps(player, playerQueen, row, col, jumps);

        if (jumps.size() == 0) {
            return null;
        } else {
            CheckersMoves[] jumpsArray = new CheckersMoves[jumps.size()];
            int i = 0;
            for (CheckersMoves jump : jumps) {
                jumpsArray[i] = jump;
                i++;
            }
            return jumpsArray;
        }
    }

    private boolean checkJump(int player, int r1, int c1, int r2, int c2, int r3,
                              int c3) {
        //Check limits and contents
        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8 || board[r3][c3] != Pieces.EMPTY.getPieceValue())
            return false;

        if (player == RED_PLAYER) {
            if (board[r2][c2] != BLACK_PLAYER && board[r2][c2] != Pieces.BLACK_QUEEN.getPieceValue())
                return false;
            if (board[r1][c1] == RED_PLAYER && r3 > r1)
                return false;
            return true;
        } else {
            if (board[r2][c2] != RED_PLAYER && board[r2][c2] != Pieces.RED_QUEEN.getPieceValue())
                return false;
            if (board[r1][c1] == BLACK_PLAYER && r3 < r1)
                return false;
            return true;
        }

    }

    private boolean checkMove(int player, int r1, int c1, int r2, int c2) {

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8 || board[r2][c2] != Pieces.EMPTY.getPieceValue())
            return false;

        if (player == RED_PLAYER) {
            if (board[r1][c1] == RED_PLAYER && r2 > r1)
                return false;
            return true;
        } else {
            if (board[r1][c1] == BLACK_PLAYER && r2 < r1)
                return false;
            return true;
        }

    }

    public int[][] getBoard() {
        return board;
    }

}
