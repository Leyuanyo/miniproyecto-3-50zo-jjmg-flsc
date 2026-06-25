package com.example.miniproyecto_50zo.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Represents the deck of cards used in the Cincuentazo game.
 * Internally uses a {@link Deque} backed by an {@link ArrayDeque}
 * for efficient removal from the front and addition to the back,
 * simulating the behavior of a real card deck.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Deck {

    /** The internal deque holding all cards in the deck. */
    private final Deque<Card> cards;

    /**
     * Constructs a full 52-card deck and shuffles it.
     */
    public Deck() {
        this.cards = new ArrayDeque<>();
        buildFullDeck();
        shuffle();
    }

    /**
     * Fills the deck with all 52 cards by iterating all combinations
     * of {@link Suit} and {@link Rank}.
     */
    private void buildFullDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Shuffles the deck randomly by converting it to a temporary list,
     * applying {@link Collections#shuffle}, and restoring the order.
     */
    public void shuffle() {
        List<Card> temp = new ArrayList<>(cards);
        Collections.shuffle(temp);
        cards.clear();
        cards.addAll(temp);
    }

    /**
     * Removes and returns the top card of the deck.
     *
     * @return the top card, or {@code null} if the deck is empty
     */
    public Card dealCard() {
        return cards.poll();
    }

    /**
     * Adds the given card to the bottom of the deck, face-down.
     * Used when an eliminated player's cards are returned to the deck.
     *
     * @param card the card to add to the bottom
     */
    public void addToBottom(Card card) {
        card.setFaceUp(false);
        cards.addLast(card);
    }

    /**
     * Shuffles the given list of cards and adds them all to the bottom
     * of the deck, face-down.
     * Used to recycle the table pile back into the deck when it runs out.
     * The current table sum is not modified by this operation.
     *
     * @param pile the list of cards to recycle into the deck
     */
    public void recyclePile(List<Card> pile) {
        Collections.shuffle(pile);
        for (Card card : pile) {
            card.setFaceUp(false);
            cards.addLast(card);
        }
    }

    /**
     * Returns {@code true} if the deck contains no cards.
     *
     * @return {@code true} if empty
     */
    public boolean isEmpty() { return cards.isEmpty(); }

    /**
     * Returns the number of cards currently in the deck.
     *
     * @return the deck size
     */
    public int size() { return cards.size(); }
}