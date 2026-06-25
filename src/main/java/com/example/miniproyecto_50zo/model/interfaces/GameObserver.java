package com.example.miniproyecto_50zo.model.interfaces;

import com.example.miniproyecto_50zo.model.Card;
import com.example.miniproyecto_50zo.model.Player;

/**
 * Observer interface for reacting to game state changes in the Cincuentazo game.
 * Implements the Observer design pattern to decouple the {@code GameModel}
 * from the controller and view layers.
 * The {@code GameModel} notifies all registered observers whenever
 * a significant game event occurs.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface GameObserver {

    /**
     * Called when a player successfully plays a card onto the table.
     *
     * @param player the player who played the card
     * @param card   the card that was played
     * @param newSum the new table sum after the card was played
     */
    void onCardPlayed(Player player, Card card, int newSum);

    /**
     * Called when a player draws a card from the deck.
     *
     * @param player the player who drew the card
     * @param card   the card that was drawn
     */
    void onCardDrawn(Player player, Card card);

    /**
     * Called when a player is eliminated from the game
     * because they have no playable card in their hand.
     *
     * @param player the player who was eliminated
     */
    void onPlayerEliminated(Player player);

    /**
     * Called when the game ends and a winner is declared.
     *
     * @param winner the last remaining player who won the game
     */
    void onGameOver(Player winner);

    /**
     * Called when the table pile is recycled back into the deck
     * because the deck ran out of cards.
     */
    void onDeckRecycled();
}