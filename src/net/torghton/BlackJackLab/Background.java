package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;
import net.torghton.BlackJackLab.AlexsGameEnhancers.VisualComponent;

import java.awt.*;

public class Background extends VisualComponent {

    private Image backgroundImage;

    public Background(Image backgroundImage, Dimension SCREENSIZE) {
        super(new Vector(0, 0), SCREENSIZE);

        this.backgroundImage = backgroundImage;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(backgroundImage, (int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight(), null);
    }
}
