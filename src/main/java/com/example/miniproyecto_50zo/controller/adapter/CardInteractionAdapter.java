package com.example.miniproyecto_50zo.controller.adapter;

import com.example.miniproyecto_50zo.model.Card;

/**
 * Adapter class for {@link CardInteractionListener} that provides
 * empty default implementations for all card interaction events.
 * Extend this class and override only the events you need,
 * instead of implementing all methods of {@link CardInteractionListener}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public abstract class CardInteractionAdapter implements CardInteractionListener {

    /**
     * Default empty implementation for hover enter events.
     *
     * @param card the card being hovered
     */
    @Override
    public void onCardHoverEnter(Card card) {
    }

    /**
     * Default empty implementation for hover exit events.
     *
     * @param card the card no longer being hovered
     */
    @Override
    public void onCardHoverExit(Card card) {
    }

    /**
     * Default empty implementation for click events.
     *
     * @param card the card that was clicked
     */
    @Override
    public void onCardClicked(Card card) {
    }
}