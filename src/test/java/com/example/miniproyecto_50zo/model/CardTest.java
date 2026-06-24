package com.example.miniproyecto_50zo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void numberedCardsShouldAddFaceValue() {
        Card seven = new Card(Suit.HEARTS, Rank.SEVEN);
        Card ten = new Card(Suit.CLUBS, Rank.TEN);

        assertEquals(7, seven.getValue(0));
        assertEquals(10, ten.getValue(0));
    }

    @Test
    void nineShouldBeNeutral() {
        Card nine = new Card(Suit.DIAMONDS, Rank.NINE);

        assertEquals(0, nine.getValue(30));
    }

    @Test
    void faceCardsShouldSubtractTen() {
        Card jack = new Card(Suit.SPADES, Rank.JACK);
        Card queen = new Card(Suit.HEARTS, Rank.QUEEN);
        Card king = new Card(Suit.CLUBS, Rank.KING);

        assertEquals(-10, jack.getValue(20));
        assertEquals(-10, queen.getValue(20));
        assertEquals(-10, king.getValue(20));
    }

    @Test
    void aceShouldBeWorthTenWhenItFits() {
        Card ace = new Card(Suit.SPADES, Rank.ACE);

        assertEquals(10, ace.getValue(25));
    }

    @Test
    void aceShouldBeWorthOneWhenTenWouldExceedFifty() {
        Card ace = new Card(Suit.SPADES, Rank.ACE);

        assertEquals(1, ace.getValue(45));
    }

    @Test
    void cardShouldBePlayableUnderLimit() {
        Card five = new Card(Suit.HEARTS, Rank.FIVE);

        assertTrue(five.isPlayable(40));
    }

    @Test
    void cardShouldNotBePlayableOverLimit() {
        Card eight = new Card(Suit.HEARTS, Rank.EIGHT);

        assertFalse(eight.isPlayable(45));
    }

    @Test
    void faceCardsShouldAlwaysBePlayable() {
        Card king = new Card(Suit.SPADES, Rank.KING);

        assertTrue(king.isPlayable(50));
        assertTrue(king.isPlayable(0));
    }

    @Test
    void imageFileNameShouldFollowNamingConvention() {
        Card kingOfSpades = new Card(Suit.SPADES, Rank.KING);

        assertEquals("king_spades.png", kingOfSpades.getImageFileName());
    }
}