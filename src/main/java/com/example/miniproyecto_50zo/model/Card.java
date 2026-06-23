package com.example.miniproyecto_50zo.model;

public class Card {

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String getImageFileName() {
        return rank.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png";
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}