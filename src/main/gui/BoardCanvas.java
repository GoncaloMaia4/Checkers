package main.gui;

import main.model.Board;
import main.model.CheckersMoves;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardCanvas extends JComponent implements ActionListener, MouseListener {
    public Label message;
    public Button newGameButton;
    private Board board;
    private int currentPlayer;
    private int rowSelected, colSelected;

    private CheckersMoves[] legalMoves;

    public BoardCanvas() {
        newGameButton = new Button("New Game");
        newGameButton.addActionListener(this);
        addMouseListener(this);
        message = new Label("", Label.CENTER);
        board = new Board();
        newGame();
    }

    public void newGame() {
        board.setUp();
        currentPlayer = 1;
        message.setText("Red starts");
        legalMoves = board.getMoves(Board.RED);
        rowSelected = -1;
        repaint();
    }

    //Multiple end game messages
    public void gameOver(String s) {
        message.setText(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            newGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / 20;
        int row = e.getY() / 20;
        clickTile(row, col);
    }

    private void clickTile(int row, int col) {
        String playerString;
        if (currentPlayer == Board.RED)
            playerString = "RED";
        else
            playerString = "BLACK";

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
                rowSelected = row;
                colSelected = col;
                message.setText(playerString + "'s turn.");
                repaint();
                return;
            }

        if (rowSelected < 0) {
            message.setText("Click on one of your pieces, " + playerString);
            return;
        }

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == rowSelected
                    && legalMoves[i].fromCol == colSelected
                    && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
                makeMove(legalMoves[i]);
                return;
            }

        message.setText("Click the square you want to move the piece to");

    }

    private void makeMove(CheckersMoves move) {

        board.makeMove(move);

        if (move.isJump()) {
            legalMoves = board.getJumpsFrom(currentPlayer, move.toRow,
                    move.toCol);
            //Force jump
            if (legalMoves != null) {
                message.setText("You must continue your play");
                //Selected tile is now the tile checker moved to
                rowSelected = move.toRow;
                colSelected = move.toCol;
                repaint();
                return;
            }
        }

        if (currentPlayer == Board.RED) {
            currentPlayer = Board.BLACK;
            legalMoves = board.getMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("RED wins.");
            else if (legalMoves[0].isJump())
                message.setText("BLACK must jump.");
            else
                message.setText("BLACK's move.");
        } else {
            currentPlayer = Board.RED;
            legalMoves = board.getMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("BLACK wins.");
            else if (legalMoves[0].isJump())
                message.setText("RED must jump.");
            else
                message.setText("RED's move.");
        }

        //Force user to select piece
        rowSelected = -1;
        repaint();

    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2)
                    g.setColor(Color.lightGray);
                else
                    g.setColor(Color.gray);
                g.fillRect(2 + col * 20, 2 + row * 20, 20, 20);
                switch (board.getPieceAt(row, col)) {
                    case Board.RED:
                        g.setColor(Color.red);
                        g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);
                        break;
                    case Board.BLACK:
                        g.setColor(Color.black);
                        g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);
                        break;
                    case Board.RED_QUEEN:
                        g.setColor(Color.red);
                        g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);
                        g.setColor(Color.white);
                        g.drawString("Q", 7 + col * 20, 16 + row * 20);
                        break;
                    case Board.BLACK_QUEEN:
                        g.setColor(Color.black);
                        g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);
                        g.setColor(Color.white);
                        g.drawString("Q", 7 + col * 20, 16 + row * 20);
                        break;
                }
            }
        }

        if (rowSelected >= 0) {
            g.setColor(Color.white);
            g.drawRect(2 + colSelected * 20, 2 + rowSelected * 20, 19, 19);
            g.drawRect(3 + colSelected * 20, 3 + rowSelected * 20, 17, 17);
            g.setColor(Color.green);
            for (int i = 0; i < legalMoves.length; i++) {
                if (legalMoves[i].fromCol == colSelected
                        && legalMoves[i].fromRow == rowSelected) {
                    g.drawRect(3 + legalMoves[i].toCol * 20,
                            3 + legalMoves[i].toRow * 20, 19, 19);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
