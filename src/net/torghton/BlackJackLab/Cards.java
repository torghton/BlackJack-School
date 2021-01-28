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

    public Cards(Image[] cardImages, int[] CardPoints, GameStateEvent gameStateEvent) {
        this.cardImages = cardImages;

        playerWon = false;

        this.gameStateEvent = gameStateEvent;

        this.CardPoints = CardPoints;

        player = new Player();
        dealer = new Dealer();

        keys = new Hashtable<>();
        keys.put(72, () -> {
            if(!gameOver && !previewMode) {
                player.draw();
                if(player.checkForBust()) {
                    endGame(true);
                }
            }
        });
        keys.put(83, () -> {
            if(!gameOver && !previewMode) {
                player.stand();
                if(player.checkForBust()) {
                    endGame(true);
                }
            }
        });

        keys.put(82, () -> {
            if(previewMode) {
                gameOver = true;
                gameStateEvent.onGameEnd(playerWon, player.getTotalPoints());
                player.getCardsInHand().clear();
                dealer.getCardsInHand().clear();
            }
        });

        cardsInDeck = new Card[CardPoints.length*4];
    }

    public void restart(Image[] cardImages) {
        player.getCardsInHand().clear();
        dealer.getCardsInHand().clear();

        cardsInDeck = newCards(cardsInDeck, cardImages);
        shuffleCards(cardsInDeck);

        player.draw();
        player.draw();

        dealer.draw();
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

    private Card[] newCards(Card[] _cards, Image[] cardImages) {
        Card[] _cardsTemp = new Card[_cards.length];

        // 4 card suits
        for(int i = 0; i < 4; i++) {
            // All of the card values
            for(int j = 0; j < 13; j++) {
                _cardsTemp[(i*13)+j] = new Card(CardPoints[j], cardImages[j]);
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
                    System.out.println("YOU WIN");
                    playerWon = true;
                } else {
                    System.out.println("You Lost!");
                    playerWon = false;
                }
            } else {
                System.out.println("DEALER BUSTED");
                System.out.println("YOU WIN");
                playerWon = true;
            }
        } else {
            System.out.println("You Lost!");
            playerWon = false;
        }

        System.out.println("Dealer Points: " + dealer.getTotalPoints());
        System.out.println("Player Points: " + player.getTotalPoints());

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
        for(int i = 0; i < player.getCardsInHand().size(); i++) {
            player.getCardsInHand().get(i).drawSelf(g, new Vector(100 + i*90, 100 +i*10), new Dimension(160, 200));
        }

        for(int i = 0; i < dealer.getCardsInHand().size(); i++) {
            dealer.getCardsInHand().get(i).drawSelf(g, new Vector(100 + i*90, 300 +i*10), new Dimension(160, 200));
        }
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
        public void draw() {
            int rnd = new Random().nextInt(cardsInDeck.length);
            cardsInHand.add(cardsInDeck[rnd]);
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
            while(getTotalPoints() < 16 && player.getTotalPoints() > getTotalPoints()) {
                draw();
            }
        }

    }
}
