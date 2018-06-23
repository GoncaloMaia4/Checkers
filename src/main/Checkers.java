import game.Game;
import gui.BoardCanvas;

import javax.swing.*;
import java.awt.*;

public class Checkers extends JApplet {
    public static void main(String[] args) {

        // create and set up the applet
        Checkers applet = new Checkers();
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(520, 250);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the applet to the frame and show it
        mainFrame.getContentPane().add(applet);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

        applet.init();
    }

    public void init() {

        setLayout(null);

        Game game = new Game();
        BoardCanvas boardCanvas = game.getBoardCanvas();
        add(boardCanvas);

        boardCanvas.getNewGameButton().setBackground(Color.lightGray);
        add(boardCanvas.getNewGameButton());

        boardCanvas.getMessage().setForeground(Color.BLACK);
        boardCanvas.getMessage().setFont(new Font("Calibri", Font.BOLD, 11));

        add(boardCanvas.getMessage());

        boardCanvas.setBounds(20, 20, 164, 164);
        boardCanvas.getMessage().setBounds(210, 150, 300, 30);
        boardCanvas.getNewGameButton().setBounds(210, 50, 100, 30);
    }

}