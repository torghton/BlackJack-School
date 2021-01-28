package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import javax.swing.*;
import java.awt.*;

public class Prompt extends JTextField {

    private String placeholderText;
    private boolean currentlyHidden;

    public Prompt(String placeholderText, Vector location, Dimension size) {
        currentlyHidden = false;

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

    public void setCurrentlyHidden(boolean currentlyHidden) {
        this.currentlyHidden = currentlyHidden;
    }

    public boolean getCurrentlyHidden() {
        return currentlyHidden;
    }
}
