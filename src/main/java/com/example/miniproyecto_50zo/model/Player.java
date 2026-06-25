package com.example.miniproyecto_50zo.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a player in the Cincuentazo game, either human or machine.
 * The {@code isMachine} flag distinguishes between the two types
 * without requiring subclasses.
 * The player's hand is stored in a {@link LinkedList} for efficient
 * insertion and removal at any position.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Player {

    /** The display name of this player. */
    private final String name;

    /** Whether this player is controlled by the machine. */
    private final boolean isMachine;

    /** The cards currently held in this player's hand. */
    private final LinkedList<Card> hand;

    /** Whether this player has been eliminated from the game. */
    private boolean eliminated;

    /**
     * Constructs a player with the given name and type.
     *
     * @param name      the display name of the player
     * @param isMachine {@code true} if this player is controlled by the machine
     */
    public Player(String name, boolean isMachine) {
        this.name = name;
        this.isMachine = isMachine;
        this.hand = new LinkedList<>();
        this.eliminated = false;
    }

    /**
     * Selects a card to play from this player's hand based on the current table sum.
     * If this player is human, returns {@code null} because card selection
     * is handled by the GUI via click events.
     * If this player is a machine, applies a greedy strategy: among all playable cards,
     * selects the one that brings the table sum as close to 50 as possible
     * without exceeding it.
     *
     * @param currentSum the current sum on the table
     * @return the selected card, or {@code null} if human or no playable card exists
     */
    public Card selectCard(int currentSum) {
        if (!isMachine) return null;

        List<Card> playable = hand.stream()
                .filter(card -> card.isPlayable(currentSum))
                .collect(Collectors.toList());

        if (playable.isEmpty()) return null;

        Card best = null;
        int bestSum = Integer.MIN_VALUE;

        for (Card card : playable) {
            int result = currentSum + card.getValue(currentSum);
            if (result > bestSum) {
                bestSum = result;
                best = card;
            }
        }

        return best;
    }

    /**
     * Returns {@code true} if this player has at least one playable card
     * given the current table sum.
     *
     * @param currentSum the current sum on the table
     * @return {@code true} if a valid play exists
     */
    public boolean canPlay(int currentSum) {
        for (Card card : hand) {
            if (card.isPlayable(currentSum)) return true;
        }
        return false;
    }

    /**
     * Adds the given card to the end of this player's hand.
     *
     * @param card the card to add
     */
    public void drawCard(Card card) { hand.add(card); }

    /**
     * Returns {@code true} if this player has been eliminated from the game.
     *
     * @return {@code true} if eliminated
     */
    public boolean isEliminated() { return eliminated; }

    /**
     * Marks this player as eliminated.
     */
    public void eliminate() { this.eliminated = true; }

    /**
     * Removes the specified card from this player's hand.
     *
     * @param card the card to remove
     */
    public void removeCard(Card card) { hand.remove(card); }

    /**
     * Removes and returns all cards currently in this player's hand.
     * Used when a player is eliminated to return their cards to the deck.
     *
     * @return a list containing all cards that were in the hand
     */
    public List<Card> clearHand() {
        List<Card> remaining = new LinkedList<>(hand);
        hand.clear();
        return remaining;
    }

    /**
     * Returns {@code true} if this player is machine-controlled.
     *
     * @return {@code true} if machine
     */
    public boolean isMachine() { return isMachine; }

    /**
     * Returns the display name of this player.
     *
     * @return the player's name
     */
    public String getName() { return name; }

    /**
     * Returns the player's current hand.
     *
     * @return the {@link LinkedList} of cards in hand
     */
    public LinkedList<Card> getHand() { return hand; }

    /**
     * Returns the player's name as the string representation.
     *
     * @return the player's name
     */
    @Override
    public String toString() { return name; }
}