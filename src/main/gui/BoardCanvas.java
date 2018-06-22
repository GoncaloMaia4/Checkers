package gui;

import game.Game;
import model.pieces.Pieces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardCanvas extends JComponent implements ActionListener, MouseListener {
    private Label message;
    private Button newGameButton;
    private Game game;

    public BoardCanvas(Game game) {
        this.game = game;
        newGameButton = new Button("New Game");
        newGameButton.addActionListener(this);
        addMouseListener(this);
        message = new Label("", Label.CENTER);
    }

    public void newGame() {
        message.setText("Red starts");
        repaint();
    }

    public void setMessageText(String messageString) {
        message.setText(messageString);
    }

    //Multiple end game messages
    public void gameOver(String s) {
        message.setText(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            game.newGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / 20;
        int row = e.getY() / 20;
        game.clickTile(row, col);
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

                //Changed from switch case to if else, switch needed constant expression, getPieceValue() is not
                int i = game.getPieceAt(row, col);
                if (i == Pieces.RED.getPieceValue()) {
                    g.setColor(Color.red);
                    g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);

                } else if (i == Pieces.BLACK.getPieceValue()) {
                    g.setColor(Color.black);
                    g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);

                } else if (i == Pieces.RED_QUEEN.getPieceValue()) {
                    g.setColor(Color.red);
                    g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);
                    g.setColor(Color.white);
                    g.drawString("Q", 7 + col * 20, 16 + row * 20);

                } else if (i == Pieces.BLACK_QUEEN.getPieceValue()) {
                    g.setColor(Color.black);
                    g.fillOval(4 + col * 20, 4 + row * 20, 16, 16);
                    g.setColor(Color.white);
                    g.drawString("Q", 7 + col * 20, 16 + row * 20);

                }
            }
        }

        if (game.getRowSelected() >= 0) {
            g.setColor(Color.white);
            g.drawRect(2 + game.getColSelected() * 20, 2 + game.getRowSelected() * 20, 19, 19);
            g.drawRect(3 + game.getColSelected() * 20, 3 + game.getRowSelected() * 20, 17, 17);
            g.setColor(Color.green);
            for (int i = 0; i < game.getLegalMoves().length; i++) {
                if (game.getLegalMoves()[i].fromCol == game.getColSelected()
                        && game.getLegalMoves()[i].fromRow == game.getRowSelected()) {
                    g.drawRect(3 + game.getLegalMoves()[i].toCol * 20,
                            3 + game.getLegalMoves()[i].toRow * 20, 19, 19);
                }
            }
        }
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Label getMessage() {
        return message;
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
