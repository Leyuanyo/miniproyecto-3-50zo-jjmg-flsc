package com.example.miniproyecto_50zo.model.interfaces;

import com.example.miniproyecto_50zo.model.Player;

public interface TurnListener {
    void onTurnStarted(Player player);
    void onTurnEnded(Player player);
}