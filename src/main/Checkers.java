package main;


import main.gui.BoardCanvas;

import javax.swing.*;
import java.awt.*;

public class Checkers extends JApplet {
    public static void main(String[] args) {

        // create and set up the applet
        Checkers applet = new Checkers();
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the applet to the frame and show it
        mainFrame.getContentPane().add(applet);
        mainFrame.setVisible(true);

        applet.init();
    }

    public void init() {

        setLayout(null);

        BoardCanvas boardCanvas = new BoardCanvas();
        this.setSize(500, 200);
        add(boardCanvas);

        boardCanvas.newGameButton.setBackground(Color.lightGray);
        add(boardCanvas.newGameButton);

        boardCanvas.message.setForeground(Color.BLACK);
        boardCanvas.message.setFont(new Font("Calibri", Font.BOLD, 11));

        add(boardCanvas.message);

        boardCanvas.setBounds(20, 20, 164, 164);
        boardCanvas.message.setBounds(210, 150, 300, 30);
        boardCanvas.newGameButton.setBounds(210, 50, 100, 30);
    }

}