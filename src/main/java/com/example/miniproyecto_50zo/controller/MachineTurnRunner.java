package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.Card;
import com.example.miniproyecto_50zo.model.GameModel;
import com.example.miniproyecto_50zo.model.Player;
import javafx.application.Platform;

import java.util.Random;

public class MachineTurnRunner {

    private static final int PLAY_DELAY_MIN_MS = 2000;
    private static final int PLAY_DELAY_RANGE_MS = 2000; // 2 to 4 seconds total
    private static final int DRAW_DELAY_MIN_MS = 1000;
    private static final int DRAW_DELAY_RANGE_MS = 1000; // 1 to 2 seconds total

    private final GameModel gameModel;
    private final Random random;

    public interface OnTurnFinished {
        void run();
    }

    public MachineTurnRunner(GameModel gameModel) {
        this.gameModel = gameModel;
        this.random = new Random();
    }

    public void runTurn(Player machine, OnTurnFinished onTurnFinished) {
        Thread playThread = new Thread(() -> {
            sleepFor(PLAY_DELAY_MIN_MS, PLAY_DELAY_RANGE_MS);

            Card chosen = machine.selectCard(gameModel.getTableSum());
            Platform.runLater(() -> {
                if (chosen != null) {
                    try {
                        gameModel.playCard(machine, chosen);
                    } catch (InvalidCardPlayException e) {
                        // The machine's own selectCard() already filters
                        // playable cards, so this should not normally happen.
                        System.err.println("Machine attempted an invalid play: " + e.getMessage());
                    }
                }
                runDrawPhase(machine, onTurnFinished);
            });
        });
        playThread.setDaemon(true);
        playThread.start();
    }

    private void runDrawPhase(Player machine, OnTurnFinished onTurnFinished) {
        Thread drawThread = new Thread(() -> {
            sleepFor(DRAW_DELAY_MIN_MS, DRAW_DELAY_RANGE_MS);

            Platform.runLater(() -> {
                try {
                    gameModel.drawCard(machine);
                } catch (EmptyDeckException e) {
                    System.err.println("Machine could not draw: " + e.getMessage());
                }
                onTurnFinished.run();
            });
        });
        drawThread.setDaemon(true);
        drawThread.start();
    }

    private void sleepFor(int minMs, int rangeMs) {
        try {
            Thread.sleep(minMs + random.nextInt(rangeMs));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}