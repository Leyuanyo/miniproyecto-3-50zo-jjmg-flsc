package com.example.miniproyecto_50zo.model;

public class Card {

    private final Suit suit;
    private final Rank rank;
    private boolean faceUp;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.faceUp = false;
    }

    public int getValue(int currentSum) {
        if (rank == Rank.ACE) {
            return (currentSum + 10 <= 50) ? 10 : 1;
        }
        return rank.getBaseValue();
    }

    public boolean isPlayable(int currentSum) {
        return currentSum + getValue(currentSum) <= 50;
    }

    public String getImageFileName() {
        return rank.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png";
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    public boolean isFaceUp() { return faceUp; }
    public void setFaceUp(boolean faceUp) { this.faceUp = faceUp; }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}