package com.example.miniproyecto_50zo.controller.adapter;

import com.example.miniproyecto_50zo.model.Card;

/**
 * Listener interface for card interaction events in the Cincuentazo GUI.
 * Defines the full set of interaction events a card can receive.
 * Implement this interface directly when all events are needed,
 * or extend {@link CardInteractionAdapter} to override only the relevant ones.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public interface CardInteractionListener {

    /**
     * Called when the mouse cursor enters the card's visual area.
     *
     * @param card the card being hovered
     */
    void onCardHoverEnter(Card card);

    /**
     * Called when the mouse cursor exits the card's visual area.
     *
     * @param card the card no longer being hovered
     */
    void onCardHoverExit(Card card);

    /**
     * Called when the user clicks on the card.
     *
     * @param card the card that was clicked
     */
    void onCardClicked(Card card);
}