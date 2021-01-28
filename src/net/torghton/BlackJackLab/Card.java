package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import java.awt.*;

public class Card {

    private int value;

    private Image suit;
    private Image cardValue;

    public Card(int value, Image suit, Image cardValue) {
        this.value = value;
        this.cardValue = cardValue;
        this.suit = suit;
    }

    public Image getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public void drawSelf(Graphics g, Vector location, Dimension size) {
        g.setColor(new Color(100, 100, 100));
        g.fillRect((int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight());

        g.drawImage(cardValue, (int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight(), null);
        g.drawImage(suit, (int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth()/5, (int) size.getHeight()/5, null);

    }
}
