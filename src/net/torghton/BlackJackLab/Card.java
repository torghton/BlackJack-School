package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import java.awt.*;

public class Card {

    private int value;

    private Image cardValue;

    public Card(int value, Image cardValue) {
        this.value = value;
        this.cardValue = cardValue;
    }

    public int getValue() {
        return value;
    }

    public void drawSelf(Graphics g, Vector location, Dimension size) {
        g.drawImage(cardValue, (int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight(), null);
    }
}
