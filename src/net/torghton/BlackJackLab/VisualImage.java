package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;
import net.torghton.BlackJackLab.AlexsGameEnhancers.VisualComponent;

import java.awt.*;

public class VisualImage extends VisualComponent {

    private Image image;

    public VisualImage(Image image, Vector location, Dimension size) {
        super(location, size);

        this.image = image;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(image, (int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight(), null);
    }
}
