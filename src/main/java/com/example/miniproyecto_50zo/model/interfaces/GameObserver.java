package com.example.miniproyecto_50zo.model.interfaces;

import com.example.miniproyecto_50zo.model.Card;
import com.example.miniproyecto_50zo.model.Player;

public interface GameObserver {
    void onCardPlayed(Player player, Card card, int newSum);
    void onCardDrawn(Player player, Card card);
    void onPlayerEliminated(Player player);
    void onGameOver(Player winner);
    void onDeckRecycled();
}