package main.model;

import java.util.ArrayList;

public class Board {

    //Possible contents on tile
    public static final int EMPTY = 0, RED = 1, RED_QUEEN = 2, BLACK = 3,
            BLACK_QUEEN = 4;

    private int[][] board;

    public Board() {
        board = new int[8][8];
    }

    public void setUp() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    if (row < 3)
                        board[row][col] = BLACK;
                    else if (row > 4)
                        board[row][col] = RED;
                    else
                        board[row][col] = EMPTY;
                } else {
                    board[row][col] = EMPTY;
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
        board[fromRow][fromCol] = EMPTY;
        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            //Obtain jumped piece coordinates and make it empty
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;
            board[jumpRow][jumpCol] = EMPTY;
        }
        //Check if Queen
        if (toRow == 0 && board[toRow][toCol] == RED)
            board[toRow][toCol] = RED_QUEEN;
        if (toRow == 7 && board[toRow][toCol] == BLACK)
            board[toRow][toCol] = BLACK_QUEEN;
    }

    public CheckersMoves[] getMoves(int player) {
        int playerQueen;
        if (player == BLACK) {
            playerQueen = BLACK_QUEEN;
        } else {
            playerQueen = RED_QUEEN;
        }

        ArrayList<CheckersMoves> moves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == playerQueen) {
                    getJumps(player, playerQueen, row, col, moves);
                }
            }
        }

        if (moves.size() == 0) {
            moves = getMoves(player, playerQueen);
        }

        if (moves.size() == 0)
            return null;
        else {
            CheckersMoves[] movesArray = new CheckersMoves[moves.size()];
            int i = 0;
            for (CheckersMoves move : moves) {
                movesArray[i] = move;
                i++;
            }
            return movesArray;
        }
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
        int playerQueen = player;
        if (player == BLACK) {
            playerQueen = BLACK_QUEEN;
        } else if (player == RED) {
            playerQueen = RED_QUEEN;
        }

        ArrayList<CheckersMoves> jumps = new ArrayList<>();

        if (board[row][col] == player || board[row][col] == playerQueen) {
            getJumps(player, playerQueen, row, col, jumps);
        }

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
        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8 || board[r3][c3] != EMPTY)
            return false;

        if (player == RED) {
            if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_QUEEN)
                return false;
            if (board[r1][c1] == RED && r3 > r1)
                return false;
            return true;
        } else {
            if (board[r2][c2] != RED && board[r2][c2] != RED_QUEEN)
                return false;
            if (board[r1][c1] == BLACK && r3 < r1)
                return false;
            return true;
        }

    }

    private boolean checkMove(int player, int r1, int c1, int r2, int c2) {

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8 || board[r2][c2] != EMPTY)
            return false;

        if (player == RED) {
            if (board[r1][c1] == RED && r2 > r1)
                return false;
            return true;
        } else {
            if (board[r1][c1] == BLACK && r2 < r1)
                return false;
            return true;
        }

    }

}