package net.torghton.BlackJackLab;

import javax.swing.JFrame;
import java.awt.Dimension;

public class Runner {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Panel panel = new Panel(new Dimension(800, 800));

        // Adds the panels to the frame
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        // Sets frame settings
        frame.setTitle("BackJack");
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.setSize(800,800);

        panel.gameLoop(10);

    }
}