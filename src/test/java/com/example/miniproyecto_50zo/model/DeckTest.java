package com.example.miniproyecto_50zo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Deck} class.
 * Verifies deck initialization, card dealing behavior,
 * pile recycling, and suit distribution.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class DeckTest {

    /** The deck instance used across all tests. */
    private Deck deck;

    /**
     * Initializes a fresh deck before each test.
     */
    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    /**
     * Verifies that a newly created deck contains exactly 52 cards,
     * representing all combinations of 4 suits and 13 ranks.
     */
    @Test
    void newDeckShouldHave52Cards() {
        assertEquals(52, deck.size());
    }

    /**
     * Verifies that dealing one card reduces the deck size by exactly one.
     */
    @Test
    void dealingCardShouldReduceSizeByOne() {
        deck.dealCard();

        assertEquals(51, deck.size());
    }

    /**
     * Verifies that dealing all 52 cards leaves the deck empty.
     */
    @Test
    void dealingAllCardsShouldEmptyTheDeck() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }

        assertTrue(deck.isEmpty());
        assertEquals(0, deck.size());
    }

    /**
     * Verifies that a dealt card is never null.
     */
    @Test
    void dealtCardShouldNotBeNull() {
        Card card = deck.dealCard();

        assertNotNull(card);
    }

    /**
     * Verifies that recycling a pile of cards into an empty deck
     * correctly restores those cards to the deck.
     */
    @Test
    void recyclingPileShouldRefillDeck() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertTrue(deck.isEmpty());

        List<Card> toRecycle = new ArrayList<>();
        toRecycle.add(new Card(Suit.HEARTS, Rank.FIVE));
        toRecycle.add(new Card(Suit.CLUBS, Rank.KING));
        toRecycle.add(new Card(Suit.SPADES, Rank.ACE));

        deck.recyclePile(toRecycle);

        assertEquals(3, deck.size());
        assertFalse(deck.isEmpty());
    }

    /**
     * Verifies that a full deck contains exactly 13 cards of each suit,
     * confirming correct initialization across all four suits.
     */
    @Test
    void deckShouldHaveThirteenCardsPerSuit() {
        int hearts = 0;
        int clubs = 0;
        int diamonds = 0;
        int spades = 0;

        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();

            switch (card.getSuit()) {
                case HEARTS   -> hearts++;
                case CLUBS    -> clubs++;
                case DIAMONDS -> diamonds++;
                case SPADES   -> spades++;
            }
        }

        assertEquals(13, hearts);
        assertEquals(13, clubs);
        assertEquals(13, diamonds);
        assertEquals(13, spades);
    }
}