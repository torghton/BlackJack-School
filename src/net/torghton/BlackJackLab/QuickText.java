package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Drawable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import java.awt.*;

public class QuickText implements Drawable {

    private Vector location;
    private Color color;
    private int size;

    private String text;

    private boolean visible = false;

    public QuickText(Color color, String text, Vector location, int size, boolean visible) {
        this.size = size;
        this.location = location;

        this.color = color;
        this.text = text;

        this.visible = visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void drawSelf(Graphics g) {
        if(visible) {
            g.setFont(new Font("MeaninglessTextThatHasNoSoulSadFace", 0, size));
            g.drawString(text, (int) location.getXDirection(), (int) location.getYDirection());
        }
    }
}
