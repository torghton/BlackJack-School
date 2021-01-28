package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.*;

import javax.swing.JPanel;

import java.awt.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Panel extends JPanel implements KeyListener, MouseListener {

    private final Dimension SCREENSIZE;

    private ImageLoader imageLoader;

    private Cards cards;

    private Scene scene;

    ManagerArray managerArray;

    public Panel(Dimension SCREENSIZE) {
        setFocusable(true);

        setBackground(Color.WHITE);

        addKeyListener(this);
        addMouseListener(this);

        this.SCREENSIZE = SCREENSIZE;

        imageLoader = new ImageLoader();

        imageLoader.loadImage("Club", "./assets/CardSuits/ClubSuit.png");
        imageLoader.loadImage("Spade", "./assets/CardSuits/DiamondSuit.png");
        imageLoader.loadImage("Heart", "./assets/CardSuits/HeartSuit.png");
        imageLoader.loadImage("Diamond", "./assets/CardSuits/SpadeSuit.png");
        imageLoader.loadImage("BackgroundT", "./assets/Backgrounds/TitleBackgroundImage.jpg");
        imageLoader.loadImage("BoardBackground", "./assets/Backgrounds/CasinoBoardBackground.jpg");
        imageLoader.loadImage("ShopBackground", "./assets/Backgrounds/ShopBackground.jpg");
        imageLoader.loadImage("TempButton", "./assets/Buttons/TempButton.jpg");
        imageLoader.loadImage("ExitHand", "./assets/Buttons/ExitHand.gif");
        imageLoader.loadImage("GoldRing", "./assets/ShopImages/GoldRing.png");

        DrawableManager drawableManagerT = new DrawableManager(5);

        KeyInteractor keyInteractorT = new KeyInteractor();

        Updater updaterT = new Updater();

        ClickManager clickManagerT = new ClickManager();

        DrawableManager drawableManager1 = new DrawableManager(5);

        KeyInteractor keyInteractor1 = new KeyInteractor();

        Updater updater1 = new Updater();

        ClickManager clickManager1 = new ClickManager();

        DrawableManager drawableManager2 = new DrawableManager(5);

        KeyInteractor keyInteractor2 = new KeyInteractor();

        Updater updater2 = new Updater();

        ClickManager clickManager2 = new ClickManager();

        managerArray = new ManagerArray();
        managerArray.addManagers(clickManagerT, drawableManagerT, keyInteractorT, updaterT);
        managerArray.addManagers(clickManager1, drawableManager1, keyInteractor1, updater1);
        managerArray.addManagers(clickManager2, drawableManager2, keyInteractor2, updater2);

        scene = new Scene(0);

        Image[] suitImages = {imageLoader.getImage("Spade"),imageLoader.getImage("Heart"),
                imageLoader.getImage("Club"),imageLoader.getImage("Diamond")};

        int[] cardValues = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
        Image[] cardImages = new Image[13];
        cardImages[0] = imageLoader.loadImage("Two", "./assets/CardValues/Two.png");
        cardImages[1] = imageLoader.loadImage("Three", "./assets/CardValues/Three.png");
        cardImages[2] = imageLoader.loadImage("Four", "./assets/CardValues/Four.jpg");
        cardImages[3] = imageLoader.loadImage("Five", "./assets/CardValues/Five.jpg");
        cardImages[4] = imageLoader.loadImage("Six", "./assets/CardValues/Six.jpg");
        cardImages[5] = imageLoader.loadImage("Seven", "./assets/CardValues/Seven.jpg");
        cardImages[6] = imageLoader.loadImage("Eight", "./assets/CardValues/Eight.jpg");
        cardImages[7] = imageLoader.loadImage("Nine", "./assets/CardValues/Nine.png");
        cardImages[8] = imageLoader.loadImage("Ten", "./assets/CardValues/Ten.png");
        cardImages[9] = imageLoader.loadImage("Jack", "./assets/CardValues/Jack.png");
        cardImages[10] = imageLoader.loadImage("Queen", "./assets/CardValues/Queen.png");
        cardImages[11] = imageLoader.loadImage("King", "./assets/CardValues/King.png");
        cardImages[12] = imageLoader.loadImage("Ace", "./assets/CardValues/Ace.png");

        Money money = new Money(100, new Vector(500, 50), 50);
        addManagers(money, 1, 4);

        Background backgroundT = new Background(imageLoader.getImage("BackgroundT"), SCREENSIZE);
        addManagers(backgroundT, 0, 0);

        Background casinoBoardBackground = new Background(imageLoader.getImage("BoardBackground"), SCREENSIZE);
        addManagers(casinoBoardBackground, 1, 0);

        Background shopBackground = new Background(imageLoader.getImage("ShopBackground"), SCREENSIZE);
        addManagers(shopBackground, 2, 0);

        cards = new Cards(suitImages, cardImages, cardValues);
        addManagers(cards, 1, 2);

        Button playButton = new Button(imageLoader.getImage("TempButton"), new Vector(SCREENSIZE.width/2 - 100, 100), new Dimension(200, 80), () -> scene.setScene(1));
        addManagers(playButton, 0, 2);

        Button shopButton = new Button(imageLoader.getImage("TempButton"), new Vector(10, 10), new Dimension(100, 100), () -> scene.setScene(2));
        addManagers(shopButton, 1, 2);

        Button leaveButton = new Button(imageLoader.getImage("ExitHand"), new Vector(0, 0), new Dimension(200, 200), () -> scene.setScene(1));
        addManagers(leaveButton, 2, 2);

        // Shop Items
        Button ring = new Button(imageLoader.getImage("GoldRing"), new Vector(100, 310), new Dimension(150, 150), () -> System.out.println("RING"));
        addManagers(ring, 2, 2);



    }

    private <T>void addManagers(T obj, int sceneNumber, int drawableLayer) {
        if(obj instanceof Updateable) {
            managerArray.getUpdater(sceneNumber).addUpdateable((Updateable) obj);
        }

        if(obj instanceof Drawable) {
            managerArray.getDrawableManager(sceneNumber).addDrawable((Drawable) obj, drawableLayer);
        }

        if(obj instanceof KeyInteractable) {
            managerArray.getKeyInteractor(sceneNumber).addKeyInteractable((KeyInteractable) obj);
        }

        if(obj instanceof Clickable) {
            managerArray.getClickManager(sceneNumber).addClickable((Clickable) obj);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREENSIZE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        managerArray.getDrawableManager(scene.getScene()).drawAll(g);


        repaint();
    }

    public void gameLoop(int delay) {
        while(true) {
            try {
                Thread.sleep(delay);
            } catch(Exception e) {
                e.printStackTrace();
            }

            managerArray.getUpdater(scene.getScene()).updateAll();

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        managerArray.getKeyInteractor(scene.getScene()).keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        managerArray.getKeyInteractor(scene.getScene()).keyReleased(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        managerArray.getClickManager(scene.getScene()).clickAll(new Vector(e.getX(), e.getY()), e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        managerArray.getClickManager(scene.getScene()).pressAll(new Vector(e.getX(), e.getY()), e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        managerArray.getClickManager(scene.getScene()).releasesAll(new Vector(e.getX(), e.getY()), e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}