package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Drawable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;
import net.torghton.BlackJackLab.AlexsGameEnhancers.VisualComponent;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money implements Drawable {

    private int totalMoney = 0;
    private int amountBet = 0;

    private int totalPoints = 0;

    private double cashMultiplyer = 1;

    private Vector location;
    private int size;

    public Money(int startMoney, Vector location, int size) {
        this.size = size;
        this.location = location;

        totalMoney = startMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public boolean spendMoney(int amount) {
        if(amount >= 0) {
            if(totalMoney - amount >= 0) {
                setTotalMoney(totalMoney - amount);
                return true;
            }
        }
        return false;
    }

    public boolean spendPoints(int amount) {
        if(amount >= 0) {
            if(totalPoints - amount >= 0) {
                setTotalPoints(totalPoints - amount);
                return true;
            }
        }
        return false;
    }

    public void gain(int amount) {
        setTotalMoney(totalMoney + amount);
    }

    public void calculateNewMoneyFromWinner(boolean playerWon) {
        if(playerWon) {
            gain((int) (amountBet*2*cashMultiplyer));
        }
        amountBet = 0;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int calculateNewPointsFramGamePoints(int points) {
        switch(points) {
            case 16:
            case 17:
                return 1;
            case 18:
            case 19:
                return 2;
            case 20:
                return 3;
            case 21:
                return 5;

        }

        return 0;
    }

    public boolean bet(int amountToBet) {
        if(amountToBet < totalMoney) {
            if(spendMoney(amountToBet)) {
                amountBet += amountToBet;
                return true;
            }
        } else {
            amountBet += totalMoney;
            spendMoney(totalMoney);
            return true;
        }
        return false;
    }

    public void increaseMultiplyer(double amount) {
        cashMultiplyer += amount;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Money", 0, size));
        g.drawString("$$$ = " + totalMoney, (int) location.getXDirection(), (int) location.getYDirection());
        g.drawString("Points = " + totalPoints, (int) location.getXDirection(), (int) location.getYDirection()+size*2);
        g.drawString("Mult = " + String.valueOf(new BigDecimal(cashMultiplyer).setScale(2, RoundingMode.DOWN)) + "x", 150, 100);

        g.setFont(new Font("Amount Bet", 0, size/2));
        g.drawString("Amount Bet = " + amountBet, (int) location.getXDirection(), (int) location.getYDirection() + size);
    }
}
