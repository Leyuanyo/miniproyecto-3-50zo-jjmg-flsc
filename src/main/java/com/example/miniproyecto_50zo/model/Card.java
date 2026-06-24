package com.example.miniproyecto_50zo.model;

/**
 * Represents a single playing card in the Cincuentazo game.
 * Each card has a {@link Suit}, a {@link Rank}, and a face state
 * indicating whether it is visible (face-up) or hidden (face-down).
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Card {

    /** The suit of this card. */
    private final Suit suit;

    /** The rank of this card. */
    private final Rank rank;

    /** Whether this card is currently face-up (visible). */
    private boolean faceUp;

    /**
     * Constructs a card with the given suit and rank.
     * The card is face-down by default.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.faceUp = false;
    }

    /**
     * Returns the effective game value of this card given the current table sum.
     * For {@link Rank#ACE}: returns 10 if adding 10 does not exceed 50,
     * otherwise returns 1.
     * For all other ranks: returns the fixed base value from {@link Rank#getBaseValue()}.
     *
     * @param currentSum the current sum on the table before playing this card
     * @return the integer value this card contributes to the table sum
     */
    public int getValue(int currentSum) {
        if (rank == Rank.ACE) {
            return (currentSum + 10 <= 50) ? 10 : 1;
        }
        return rank.getBaseValue();
    }

    /**
     * Returns {@code true} if playing this card keeps the table sum at or below 50.
     *
     * @param currentSum the current sum on the table before playing this card
     * @return {@code true} if the card is legally playable
     */
    public boolean isPlayable(int currentSum) {
        return currentSum + getValue(currentSum) <= 50;
    }

    /**
     * Returns the image file name corresponding to this card,
     * following the naming convention {@code rank_suit.png}.
     * For example, the king of spades returns {@code "king_spades.png"}.
     *
     * @return the image file name for this card
     */
    public String getImageFileName() {
        return rank.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png";
    }

    /**
     * Returns the suit of this card.
     *
     * @return the {@link Suit} of this card
     */
    public Suit getSuit() { return suit; }

    /**
     * Returns the rank of this card.
     *
     * @return the {@link Rank} of this card
     */
    public Rank getRank() { return rank; }

    /**
     * Returns {@code true} if this card is face-up (visible).
     *
     * @return {@code true} if the card is face-up
     */
    public boolean isFaceUp() { return faceUp; }

    /**
     * Sets the face state of this card.
     *
     * @param faceUp {@code true} to make the card face-up, {@code false} for face-down
     */
    public void setFaceUp(boolean faceUp) { this.faceUp = faceUp; }

    /**
     * Returns a string representation of this card in the format
     * {@code "RANK of SUIT"}, for example {@code "KING of SPADES"}.
     *
     * @return string representation of this card
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}