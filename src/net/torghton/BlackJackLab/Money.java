package net.torghton.BlackJackLab;

import net.torghton.BlackJackLab.AlexsGameEnhancers.Drawable;
import net.torghton.BlackJackLab.AlexsGameEnhancers.Vector;
import net.torghton.BlackJackLab.AlexsGameEnhancers.VisualComponent;

import java.awt.*;

public class Money implements Drawable {

    private int totalMoney = 0;
    private int amountBet = 0;

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

    public boolean spend(int amount) {
        if(amount >= 0) {
            if(totalMoney - amount >= 0) {
                setTotalMoney(totalMoney - amount);
                return true;
            }
        }
        return false;
    }

    public void gain(int amount) {
        setTotalMoney(totalMoney + amount);
    }

    public void calculateNewMoneyFromPoints(boolean playerWon) {
        if(playerWon) {
            gain((int) (amountBet*2*cashMultiplyer));
        }
        amountBet = 0;
    }

    public boolean bet(int amountToBet) {
        if(amountToBet < totalMoney) {
            if(spend(amountToBet)) {
                amountBet += amountToBet;
                return true;
            }
        } else {
            amountBet += totalMoney;
            spend(totalMoney);
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
        g.setFont(new Font("Amount Bet", 0, size/2));
        g.drawString("Amount Bet = " + amountBet, (int) location.getXDirection(), (int) location.getYDirection() + size);
    }
}
