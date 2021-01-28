package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Drawable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.KeyInteractable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

interface GameStateEvent {
    void onGameEnd(boolean playerWon, int points);
    void onGamePreview(int points);
}

public class Cards implements Drawable, KeyInteractable {
    private Card[] cardsInDeck;

    private Hashtable<Integer, Runnable> keys;

    private int[] CardPoints;

    private Player player;
    private Dealer dealer;

    private boolean gameOver;
    private boolean previewMode;
    private boolean playerWon;

    private GameStateEvent gameStateEvent;

    private Image[] cardImages;

    private Image hiddenCard;

    private String outCome;

    private int CardToTake;

    public Cards(Image[] cardImages, Image hiddenCard, int[] CardPoints, GameStateEvent gameStateEvent) {
        this.hiddenCard = hiddenCard;

        this.cardImages = cardImages;

        outCome = "";

        playerWon = false;
        gameOver = false;

        CardToTake = 0;

        this.gameStateEvent = gameStateEvent;

        this.CardPoints = CardPoints;

        player = new Player();
        dealer = new Dealer();

        keys = new Hashtable<>();
        keys.put(72, () -> {
            hit();
        });
        keys.put(83, () -> {
            stand();
        });

        keys.put(82, () -> {
            if(previewMode) {
                gameOver = true;
                gameStateEvent.onGameEnd(playerWon, player.getTotalPoints());
                player.getCardsInHand().clear();
                dealer.getCardsInHand().clear();
                outCome = "";
            }
        });

        cardsInDeck = new Card[CardPoints.length*4];
    }

    public void restart(Image[] cardImages) {
        CardToTake = 0;

        for(Card card: player.getCardsInHand()) {
            card.setHidden(false);
        }

        for(Card card: dealer.getCardsInHand()) {
            card.setHidden(false);
        }

        player.getCardsInHand().clear();
        dealer.getCardsInHand().clear();

        cardsInDeck = newCards(cardsInDeck, cardImages);
        shuffleCards(cardsInDeck);

        player.draw();
        player.draw();

        dealer.draw().setHidden(true);
        dealer.draw();
    }

    public void restart() {
        restart(cardImages);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setPreviewMode(boolean previewMode) {
        this.previewMode = previewMode;
    }

    public void setOutCome(String outCome) {
        this.outCome = outCome;
    }

    public String getOutCome() {
        return outCome;
    }

    public Player getPlayer() {
        return player;
    }

    public void hit() {
        if (!gameOver && !previewMode && getPlayer().getCardsInHand().size() > 0) {
            player.draw();
            if (player.checkForBust()) {
                endGame(true);
            }
        }
    }

    public void stand() {
        if(!gameOver && !previewMode && getPlayer().getCardsInHand().size() > 0) {
            player.stand();
            if(player.checkForBust()) {
                endGame(true);
            }
        }
    }

    private Card[] newCards(Card[] _cards, Image[] cardImages) {
        Card[] _cardsTemp = new Card[_cards.length];

        // 4 card suits
        for(int i = 0; i < 4; i++) {
            // All of the card values
            for(int j = 0; j < 13; j++) {
                _cardsTemp[(i*13)+j] = new Card(CardPoints[j], cardImages[j], hiddenCard);
            }
        }

        return _cardsTemp;
    }

    private void endGame(boolean busted) {
        previewMode = true;

        if(!busted) {
            dealer.autoPlay(player);

            if(!dealer.checkForBust()) {
                if(dealer.getTotalPoints() < player.getTotalPoints()) {
                    outCome = "YOU WIN";
                    playerWon = true;
                } else {
                    outCome = "You Lost!";
                    playerWon = false;
                }
            } else {
                outCome = "YOU WIN";
                playerWon = true;
            }
        } else {
            outCome = "You Lost!";
            playerWon = false;
        }

        gameStateEvent.onGamePreview(player.getTotalPoints());

    }

    private void shuffleCards(Card[] _cards) {

        for(int i = 0; i < _cards.length; i++) {
            int randomIndex = (int) (Math.random()*(_cards.length-1)) + 1;

            Card card1 = _cards[i];
            Card card2 = _cards[randomIndex];

            _cards[i] = card2;
            _cards[randomIndex] = card1;
        }
    }

    @Override
    public void drawSelf(Graphics g) {
        g.setFont(new Font("None", 3, 60));

        for(int i = 0; i < player.getCardsInHand().size(); i++) {
            player.getCardsInHand().get(i).drawSelf(g, new Vector(100 + i*90, 100 +i*10), new Dimension(160, 200));
        }

        g.setColor(Color.BLUE);
        if(player.getCardsInHand().size() > 0) {
            g.drawString("You", 200 + player.getCardsInHand().size()*90, 200 + player.getCardsInHand().size()*10);
        }


        for(int i = 0; i < dealer.getCardsInHand().size(); i++) {
            dealer.getCardsInHand().get(i).drawSelf(g, new Vector(100 + i*90, 400 +i*10), new Dimension(160, 200));
        }

        g.setColor(Color.RED);
        if(player.getCardsInHand().size() > 0) {
            g.drawString("Dealer", 200 + dealer.getCardsInHand().size()*90, 500 + dealer.getCardsInHand().size()*10);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("None", 1, 80));
        g.drawString(outCome, 0, 600);
    }

    @Override
    public void keyPressed(Integer keycode) {
        if(keys.containsKey(keycode)) {
            keys.get(keycode).run();
        }
    }

    @Override
    public void keyReleased(Integer keycode) {

    }



    private class Player {
        private ArrayList<Card> cardsInHand;

        public Player() {
            cardsInHand = new ArrayList<>();
        }

        // TODO: SWITCH THIS TO TAKE FROM TOP OF THE DECK, AND NOT A RANDOM INDEX
        public Card draw() {
            cardsInHand.add(cardsInDeck[++CardToTake]);

            return cardsInHand.get(cardsInHand.size()-1);
        }

        public boolean checkForBust() {
            int totalPoints = getTotalPoints();

            if(totalPoints > 21) {
                return true;
            }

            return false;
        }

        public int getTotalPoints() {
            int totalPoints = 0;
            for(Card card: cardsInHand) {
                totalPoints += card.getValue();
            }

            return totalPoints;
        }

        public void stand() {
            endGame(false);
        }

        public ArrayList<Card> getCardsInHand() {
            return cardsInHand;
        }
    }

    private class Dealer extends Player {
        public Dealer() {
            super();
        }

        public void autoPlay(Player player) {
            getCardsInHand().get(0).setHidden(false);

            while(getTotalPoints() < 16 && player.getTotalPoints() > getTotalPoints()) {
                draw();
            }
        }

    }
}
