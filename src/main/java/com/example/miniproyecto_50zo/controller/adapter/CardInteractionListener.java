package com.example.miniproyecto_50zo.controller.adapter;

import com.example.miniproyecto_50zo.model.Card;

public interface CardInteractionListener {
    void onCardHoverEnter(Card card);
    void onCardHoverExit(Card card);
    void onCardClicked(Card card);
}