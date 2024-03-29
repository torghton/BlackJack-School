package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.*;

import javax.swing.JPanel;

import java.awt.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


// TODO: DISPLAY TOTAL POINTS AND POINTS EARNED
public class Table extends JPanel implements KeyListener, MouseListener {

    private final Dimension SCREENSIZE;

    private ImageLoader imageLoader;

    private Cards cards;

    private Scene scene;

    ManagerArray managerArray;

    public Table(Dimension SCREENSIZE) {
        setFocusable(true);

        setBackground(Color.WHITE);

        addKeyListener(this);
        addMouseListener(this);

        this.SCREENSIZE = SCREENSIZE;

        imageLoader = new ImageLoader();

        imageLoader.loadImage("BackgroundT", "./assets/Backgrounds/TitleBackgroundImage.jpg");
        imageLoader.loadImage("BoardBackground", "./assets/Backgrounds/CasinoBoardBackground.jpg");
        imageLoader.loadImage("ShopBackground", "./assets/Backgrounds/ShopBackground.jpg");
        imageLoader.loadImage("TempButton", "./assets/Buttons/TempButton.jpg");
        imageLoader.loadImage("ExitHand", "./assets/Buttons/ExitHand.gif");
        imageLoader.loadImage("GoldRing", "./assets/ShopImages/GoldRing.png");
        imageLoader.loadImage("GoldRingToolTip", "./assets/ShopImages/GoldRingToolTip.png");
        imageLoader.loadImage("StuffedBunny", "./assets/ShopImages/StuffedBunny.png");
        imageLoader.loadImage("ShoppingCartIcon", "./assets/Buttons/ShoppingCartIcon.png");
        imageLoader.loadImage("ShoppingCartHoverIcon", "./assets/Buttons/ShoppingCartHoverIcon.png");
        imageLoader.loadImage("CardBack", "./assets/ExtraPlayingCards/PlayingCardBack.png");
        imageLoader.loadImage("LuckySticks", "./assets/ShopImages/LuckySticks.png");
        imageLoader.loadImage("LuckySticksToolTip", "./assets/ShopImages/LuckySticksToolTip.png");
        imageLoader.loadImage("StuffedBunnyToolTip", "./assets/ShopImages/StuffedBunnyToolTip.png");
        imageLoader.loadImage("ToyTruck", "./assets/ShopImages/ToyTruck.png");
        imageLoader.loadImage("ToyTruckToolTip", "./assets/ShopImages/ToyTruckToolTip.png");
        imageLoader.loadImage("Dreidel", "./assets/ShopImages/Dreidel.png");
        imageLoader.loadImage("DreidelToolTip", "./assets/ShopImages/DreidelToolTip.png");
        imageLoader.loadImage("Logo", "./assets/Logo/Logo.png");
        imageLoader.loadImage("HitImage", "./assets/ExtraPlayingCards/HitImage.png");
        imageLoader.loadImage("HitImageHovered", "./assets/ExtraPlayingCards/HitImageHovered.png");
        imageLoader.loadImage("StandImage", "./assets/ExtraPlayingCards/StandImage.png");
        imageLoader.loadImage("StandImageHovered", "./assets/ExtraPlayingCards/StandImageHovered.png");
        imageLoader.loadImage("PlayButton", "./assets/Buttons/PlayButton.png");
        imageLoader.loadImage("PlayButtonHovered", "./assets/Buttons/PlayButtonHovered.png");
        imageLoader.loadImage("HelpButton", "./assets/Buttons/HelpButton.png");
        imageLoader.loadImage("HelpButtonHovered", "./assets/Buttons/HelpButtonHovered.png");
        imageLoader.loadImage("HelpScreen", "./assets/Backgrounds/HelpScreen.png");

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

        DrawableManager BdrawableManager1 = new DrawableManager(5);

        KeyInteractor BkeyInteractor1 = new KeyInteractor();

        Updater Bupdater1 = new Updater();

        ClickManager BclickManager1 = new ClickManager();

        managerArray = new ManagerArray();
        managerArray.addManagers(clickManagerT, drawableManagerT, keyInteractorT, updaterT);
        managerArray.addManagers(clickManager1, drawableManager1, keyInteractor1, updater1);
        managerArray.addManagers(clickManager2, drawableManager2, keyInteractor2, updater2);
        managerArray.addManagers(BclickManager1, BdrawableManager1, BkeyInteractor1, Bupdater1);

        scene = new Scene(0);

        int[] cardValues = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        String[] cardValueNames = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        Image[] cardImages = new Image[52];
        File file = new File("./assets/CardValues");

        String[] pathNames = file.list();
        ArrayList<String> pathNamesAL = new ArrayList<>();
        Collections.addAll(pathNamesAL, pathNames);

        for(int i = 0; i < pathNamesAL.size(); i++) {
            System.out.println(i + ": " + pathNamesAL.get(i));
        }

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++) {
                int index = getIndexOfStartStringInArrayList(pathNamesAL, cardValueNames[j]);

                cardImages[(i*13)+j] = imageLoader.loadImage(pathNamesAL.get(index), "./assets/CardValues/" + pathNamesAL.get(index));

                pathNamesAL.remove(index);
            }
        }

        // Real BlackJack
        Money money = new Money(100, new Vector(500, 50), 50);
        addManagers(money, 1, 4);

        QuickText betAmountText = new QuickText(new Color(255, 222, 36), "Amount To Bet: ", new Vector(390, 630), 30, true);
        addManagers(betAmountText, 1, 4);

        Prompt prompt = new Prompt("0", new Vector(600,700),  new Dimension(100, 40));
        prompt.addActionListener((event) -> {
            int moneyBetting = prompt.getIntValue();

            if(moneyBetting >= 0) {
                if(money.bet(moneyBetting)) {
                    prompt.setText("0");
                    prompt.setFocusable(false);
                    prompt.setVisible(false);
                    betAmountText.setVisible(false);
                    cards.restart();
                    cards.setGameOver(false);
                    cards.setPreviewMode(false);
                }
            }

            prompt.setCurrentlyHidden(true);
        });
        add(prompt);

        scene.onSceneChange((scene) -> {
            if(scene == 1) {
                prompt.setVisible(!prompt.getCurrentlyHidden());
            } else {
                prompt.setVisible(false);
            }
        });

        Background backgroundT = new Background(imageLoader.getImage("BackgroundT"), SCREENSIZE);
        addManagers(backgroundT, 0, 0);

        Background casinoBoardBackground = new Background(imageLoader.getImage("BoardBackground"), SCREENSIZE);
        addManagers(casinoBoardBackground, 1, 0);

        Background shopBackground = new Background(imageLoader.getImage("ShopBackground"), SCREENSIZE);
        addManagers(shopBackground, 2, 0);

        Background helpScreen = new Background(imageLoader.getImage("HelpScreen"), SCREENSIZE);
        addManagers(helpScreen, 3, 0);

        cards = new Cards(cardImages, imageLoader.getImage("CardBack"), cardValues, new GameStateEvent() {
            @Override
            public void onGameEnd(boolean playerWon, int points) {
                money.calculateNewMoneyFromWinner(playerWon);
                money.setTotalPoints(money.getTotalPoints() + money.calculateNewPointsFramGamePoints(points));
                prompt.setCurrentlyHidden(false);
                prompt.setVisible(true);
                prompt.setFocusable(true);
                betAmountText.setVisible(true);
            }

            @Override
            public void onGamePreview(int points) {
                cards.setOutCome(cards.getOutCome() + " + " + money.calculateNewPointsFramGamePoints(points) + "pts");
            }
        });
        addManagers(cards, 1, 2);

        MouseData getMousePos = () -> {
            Point mousePos = getMousePosition();

            if(mousePos != null) {
                return new Vector(mousePos);
            }

            return new Vector(-100, -100);
        };

        VisualImage logo = new VisualImage(imageLoader.getImage("Logo"), new Vector((SCREENSIZE.width/2)-300, 50), new Dimension(600, 300));
        addManagers(logo, 0, 3);

        Button realBlackJackPlayButton = new Button(imageLoader.getImage("PlayButton"), imageLoader.getImage("PlayButtonHovered"), new Vector(SCREENSIZE.width/2 - 150, 450), new Dimension(300, 80), () -> scene.setScene(1), getMousePos);
        addManagers(realBlackJackPlayButton, 0, 2);

        Button helpButton =  new Button(imageLoader.getImage("HelpButton"), imageLoader.getImage("HelpButtonHovered"), new Vector(SCREENSIZE.width/2 - 150, 600), new Dimension(300, 80), () -> scene.setScene(3), getMousePos);
        addManagers(helpButton, 0, 2);

        Button shopButton = new Button(imageLoader.getImage("ShoppingCartIcon"), imageLoader.getImage("ShoppingCartHoverIcon"), new Vector(10, 10), new Dimension(130, 130), () -> scene.setScene(2), getMousePos);
        addManagers(shopButton, 1, 2);

        Button HitButton = new Button(imageLoader.getImage("HitImage"), imageLoader.getImage("HitImageHovered"), new Vector(30, 600), new Dimension(130, 150), () -> cards.hit(), getMousePos);
        addManagers(HitButton, 1, 2);

        Button StandButton = new Button(imageLoader.getImage("StandImage"), imageLoader.getImage("StandImageHovered"), new Vector(200, 600), new Dimension(130, 150), () -> cards.stand(), getMousePos);
        addManagers(StandButton, 1, 2);

        Button leaveButton = new Button(imageLoader.getImage("ExitHand"), new Vector(0, 0), new Dimension(200, 200), () -> scene.setScene(1), getMousePos);
        addManagers(leaveButton, 2, 2);

        Button leaveButton2 = new Button(imageLoader.getImage("ExitHand"), new Vector(-30, 640), new Dimension(200, 200), () -> scene.setScene(0), getMousePos);
        addManagers(leaveButton2, 3, 2);

        // Shop Items
        Button luckySticks = new Button(imageLoader.getImage("LuckySticks"), imageLoader.getImage("LuckySticksToolTip"), new Vector(50, 220), new Dimension(200, 200), () -> {
            if(money.spendMoney(100)) {
                money.increaseMultiplyer(.015);
            }
        }, getMousePos);
        addManagers(luckySticks, 2, 2);

        // Shop Items
        Button ring = new Button(imageLoader.getImage("GoldRing"), imageLoader.getImage("GoldRingToolTip"), new Vector(260, 300), new Dimension(150, 150), () -> {
            if(money.spendMoney(1000)) {
                money.increaseMultiplyer(.2);
            }
        }, getMousePos);
        addManagers(ring, 2, 2);

        Button StuffedBunny = new Button(imageLoader.getImage("StuffedBunny"), imageLoader.getImage("StuffedBunnyToolTip"), new Vector(500, 130), new Dimension(200, 300), () -> {
            if(money.spendMoney(10000)) {
                money.increaseMultiplyer(2);
            }
        }, getMousePos);
        addManagers(StuffedBunny, 2, 2);

        Button ToyTruck = new Button(imageLoader.getImage("ToyTruck"), imageLoader.getImage("ToyTruckToolTip"), new Vector(40, 480), new Dimension(720, 220), () -> {
            if(money.spendMoney(100000)) {
                money.increaseMultiplyer(40);
            }
        }, getMousePos);
        addManagers(ToyTruck, 2, 2);

        Button Dreidel = new Button(imageLoader.getImage("Dreidel"), imageLoader.getImage("DreidelToolTip"), new Vector(400, 15), new Dimension(100, 100), () -> {
            if(money.spendPoints(100)) {
                money.gain(10000);
            }
        }, getMousePos);
        addManagers(Dreidel, 2, 2);








        // Fake Blackjack



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

    private int getIndexOfStartStringInArrayList(ArrayList<String> strings, String startStringToFind) {
        for(int i = 0; i < strings.size(); i++) {
            if(strings.get(i).substring(0, 1).equals(startStringToFind)) {
                return i;
            } else if(strings.get(i).substring(0, 2).equals(startStringToFind)) {
                return i;
            }
        }

        return -1;
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