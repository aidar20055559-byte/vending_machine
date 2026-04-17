package model;

public class CardAcceptor {
    private double cardBalance;

    public CardAcceptor(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }
}