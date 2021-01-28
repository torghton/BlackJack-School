package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import javax.swing.*;
import java.awt.*;

public class Prompt extends JTextField{

    private String placeholderText;

    public Prompt(String placeholderText, Vector location, Dimension size) {
        this.placeholderText = placeholderText;
        setVisible(false);

        setBounds((int) location.getXDirection(), (int) location.getXDirection(),(int) size.getWidth(),(int) size.getHeight());
        setEditable(true);
        setFont(new Font("Arial", Font.ITALIC, 30));
        setText(placeholderText);
    }

    public int getIntValue() {
        try {
            return Integer.parseInt(getText());
        } catch(Exception e) {}

        return 0;
    }
}
