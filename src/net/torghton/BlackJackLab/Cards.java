package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Drawable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.KeyInteractable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

interface GameStateEvent {
    void onGameEnd(boolean playerWon);
}

public class Cards implements Drawable, KeyInteractable {
    private Card[] cardsInDeck;

    private Hashtable<Integer, Runnable> keys;

    private int[] CardPoints;

    private Player player;
    private Dealer dealer;

    private boolean gameOver;

    private GameStateEvent gameStateEvent;

    private Image[] suitImages;
    private Image[] cardImages;

    public Cards(Image[] suitImages, Image[] cardImages, int[] CardPoints, GameStateEvent gameStateEvent) {
        this.suitImages = suitImages;
        this.cardImages = cardImages;

        this.gameStateEvent = gameStateEvent;

        this.CardPoints = CardPoints;

        player = new Player();
        dealer = new Dealer();

        keys = new Hashtable<>();
        keys.put(72, () -> {
            if(!gameOver) {
                player.draw();
                if(player.checkForBust()) {
                    endGame(true);
                }
            }
        });
        keys.put(83, () -> {
            if(!gameOver) {
                player.stand();
                if(player.checkForBust()) {
                    endGame(true);
                }
            }
        });

        cardsInDeck = new Card[CardPoints.length*4];

        restart(suitImages, cardImages);
    }

    private void restart(Image[] suitImages, Image[] cardImages) {
        player.getCardsInHand().clear();
        dealer.getCardsInHand().clear();

        cardsInDeck = newCards(cardsInDeck, suitImages, cardImages);
        shuffleCards(cardsInDeck);

        player.draw();
        player.draw();

        dealer.draw();
        dealer.draw();
    }

    private Card[] newCards(Card[] _cards, Image[] _suitImages, Image[] cardImages) {
        Card[] _cardsTemp = new Card[_cards.length];

        int currentIter = 0;

        // 4 card suits
        for(int i = 0; i < 4; i++) {
            // All of the card values
            for(int j = 0; j < CardPoints.length; j++) {
                _cardsTemp[currentIter] = new Card(CardPoints[j], _suitImages[i], cardImages[j]);
                currentIter++;
            }
        }

        return _cardsTemp;
    }

    private void endGame(boolean busted) {
        gameOver = true;

        if(!busted) {
            dealer.autoPlay(player);

            if(!dealer.checkForBust()) {
                if(dealer.getTotalPoints() < player.getTotalPoints()) {
                    System.out.println("YOU WIN");
                    gameStateEvent.onGameEnd(true);
                } else {
                    System.out.println("You Lost!");
                    gameStateEvent.onGameEnd(false);
                }
            } else {
                System.out.println("DEALER BUSTED");
                System.out.println("YOU WIN");
                gameStateEvent.onGameEnd(true);
            }
        } else {
            System.out.println("You Lost!");
            gameStateEvent.onGameEnd(false);
        }

        System.out.println("Dealer Points: " + dealer.getTotalPoints());
        System.out.println("Player Points: " + player.getTotalPoints());

        restart(suitImages, cardImages);

        gameOver = false;

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
