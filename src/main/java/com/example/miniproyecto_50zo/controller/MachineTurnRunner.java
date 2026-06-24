package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.Card;
import com.example.miniproyecto_50zo.model.GameModel;
import com.example.miniproyecto_50zo.model.Player;
import javafx.application.Platform;

import java.util.Random;

/**
 * Handles the full turn sequence for a machine player in the Cincuentazo game
 * using two background threads to simulate thinking and drawing delays.
 * All model and UI updates are dispatched to the JavaFX Application Thread
 * via {@link Platform#runLater}.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class MachineTurnRunner {

    /** Minimum delay in milliseconds before the machine plays a card. */
    private static final int PLAY_DELAY_MIN_MS = 2000;

    /** Random range in milliseconds added to the play delay. */
    private static final int PLAY_DELAY_RANGE_MS = 2000;

    /** Minimum delay in milliseconds before the machine draws a card. */
    private static final int DRAW_DELAY_MIN_MS = 1000;

    /** Random range in milliseconds added to the draw delay. */
    private static final int DRAW_DELAY_RANGE_MS = 1000;

    /** The game model this runner acts upon. */
    private final GameModel gameModel;

    /** Random number generator for computing turn delays. */
    private final Random random;

    /**
     * Functional interface used to notify the controller when a machine turn ends.
     */
    public interface OnTurnFinished {

        /**
         * Called when the machine player has completed its full turn
         * (played a card and drawn a replacement).
         */
        void run();
    }

    /**
     * Constructs a MachineTurnRunner for the given game model.
     *
     * @param gameModel the game model this runner will act upon
     */
    public MachineTurnRunner(GameModel gameModel) {
        this.gameModel = gameModel;
        this.random = new Random();
    }

    /**
     * Starts the machine player's turn asynchronously.
     * Waits between 2 and 4 seconds before playing a card,
     * then proceeds to the draw phase.
     *
     * @param machine        the machine player whose turn it is
     * @param onTurnFinished callback invoked when the full turn is complete
     */
    public void runTurn(Player machine, OnTurnFinished onTurnFinished) {
        Thread playThread = new Thread(() -> {
            sleepFor(PLAY_DELAY_MIN_MS, PLAY_DELAY_RANGE_MS);
            Card chosen = machine.selectCard(gameModel.getTableSum());
            Platform.runLater(() -> {
                if (chosen != null) {
                    try {
                        gameModel.playCard(machine, chosen);
                    } catch (InvalidCardPlayException e) {
                        System.err.println("Machine attempted an invalid play: " + e.getMessage());
                    }
                }
                runDrawPhase(machine, onTurnFinished);
            });
        });
        playThread.setDaemon(true);
        playThread.start();
    }

    /**
     * Starts the draw phase of the machine's turn asynchronously.
     * Waits between 1 and 2 seconds before drawing a card,
     * then invokes the turn-finished callback.
     *
     * @param machine        the machine player drawing a card
     * @param onTurnFinished callback invoked after the card is drawn
     */
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

    /**
     * Pauses the current thread for a random duration between
     * {@code minMs} and {@code minMs + rangeMs} milliseconds.
     *
     * @param minMs   the minimum sleep duration in milliseconds
     * @param rangeMs the additional random range in milliseconds
     */
    private void sleepFor(int minMs, int rangeMs) {
        try {
            Thread.sleep(minMs + random.nextInt(rangeMs));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}