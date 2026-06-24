package com.example.miniproyecto_50zo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void newDeckShouldHave52Cards() {
        assertEquals(52, deck.size());
    }

    @Test
    void dealingCardShouldReduceSizeByOne() {
        deck.dealCard();

        assertEquals(51, deck.size());
    }

    @Test
    void dealingAllCardsShouldEmptyTheDeck() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }

        assertTrue(deck.isEmpty());
        assertEquals(0, deck.size());
    }

    @Test
    void dealtCardShouldNotBeNull() {
        Card card = deck.dealCard();

        assertNotNull(card);
    }

    @Test
    void recyclingPileShouldRefillDeck() {
        // Empty the deck completely first
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

    @Test
    void deckShouldHaveThirteenCardsPerSuit() {

        int hearts = 0;
        int clubs = 0;
        int diamonds = 0;
        int spades = 0;

        for (int i = 0; i < 52; i++) {

            Card card = deck.dealCard();

            switch (card.getSuit()) {
                case HEARTS -> hearts++;
                case CLUBS -> clubs++;
                case DIAMONDS -> diamonds++;
                case SPADES -> spades++;
            }
        }

        assertEquals(13, hearts);
        assertEquals(13, clubs);
        assertEquals(13, diamonds);
        assertEquals(13, spades);
    }
}