package com.example.miniproyecto_50zo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Card} class.
 * Verifies the game value logic, playability rules,
 * and image file naming convention for all card types.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class CardTest {

    /**
     * Verifies that numbered cards (2-8 and 10) return their face value
     * as the game contribution regardless of the current table sum.
     */
    @Test
    void numberedCardsShouldAddFaceValue() {
        Card seven = new Card(Suit.HEARTS, Rank.SEVEN);
        Card ten = new Card(Suit.CLUBS, Rank.TEN);

        assertEquals(7, seven.getValue(0));
        assertEquals(10, ten.getValue(0));
    }

    /**
     * Verifies that a NINE card contributes 0 to the table sum,
     * neither adding nor subtracting.
     */
    @Test
    void nineShouldBeNeutral() {
        Card nine = new Card(Suit.DIAMONDS, Rank.NINE);

        assertEquals(0, nine.getValue(30));
    }

    /**
     * Verifies that JACK, QUEEN, and KING each subtract 10 from the table sum.
     */
    @Test
    void faceCardsShouldSubtractTen() {
        Card jack = new Card(Suit.SPADES, Rank.JACK);
        Card queen = new Card(Suit.HEARTS, Rank.QUEEN);
        Card king = new Card(Suit.CLUBS, Rank.KING);

        assertEquals(-10, jack.getValue(20));
        assertEquals(-10, queen.getValue(20));
        assertEquals(-10, king.getValue(20));
    }

    /**
     * Verifies that an ACE returns 10 when adding 10 to the current sum
     * does not exceed 50.
     */
    @Test
    void aceShouldBeWorthTenWhenItFits() {
        Card ace = new Card(Suit.SPADES, Rank.ACE);

        assertEquals(10, ace.getValue(25));
    }

    /**
     * Verifies that an ACE returns 1 when adding 10 to the current sum
     * would exceed 50.
     */
    @Test
    void aceShouldBeWorthOneWhenTenWouldExceedFifty() {
        Card ace = new Card(Suit.SPADES, Rank.ACE);

        assertEquals(1, ace.getValue(45));
    }

    /**
     * Verifies that a card is playable when its value does not cause
     * the table sum to exceed 50.
     */
    @Test
    void cardShouldBePlayableUnderLimit() {
        Card five = new Card(Suit.HEARTS, Rank.FIVE);

        assertTrue(five.isPlayable(40));
    }

    /**
     * Verifies that a card is not playable when its value would cause
     * the table sum to exceed 50.
     */
    @Test
    void cardShouldNotBePlayableOverLimit() {
        Card eight = new Card(Suit.HEARTS, Rank.EIGHT);

        assertFalse(eight.isPlayable(45));
    }

    /**
     * Verifies that face cards (JACK, QUEEN, KING) are always playable
     * since they subtract 10 and can never exceed the limit.
     */
    @Test
    void faceCardsShouldAlwaysBePlayable() {
        Card king = new Card(Suit.SPADES, Rank.KING);

        assertTrue(king.isPlayable(50));
        assertTrue(king.isPlayable(0));
    }

    /**
     * Verifies that the image file name follows the {@code rank_suit.png}
     * naming convention required by {@link com.example.miniproyecto_50zo.util.CardImageLoader}.
     */
    @Test
    void imageFileNameShouldFollowNamingConvention() {
        Card kingOfSpades = new Card(Suit.SPADES, Rank.KING);

        assertEquals("king_spades.png", kingOfSpades.getImageFileName());
    }
}