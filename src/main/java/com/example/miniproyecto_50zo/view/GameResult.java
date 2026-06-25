package com.example.miniproyecto_50zo.view;

/**
 * Utility class that stores the name of the winning player
 * to be displayed on the game-over screen.
 * Acts as a simple data bridge between the game screen and the result screen.
 * This class cannot be instantiated.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameResult {

    /** The name of the winning player. */
    private static String winnerName = "";

    /**
     * Private constructor to prevent instantiation.
     */
    private GameResult() { }

    /**
     * Returns the name of the winning player.
     *
     * @return the winner's name
     */
    public static String getWinnerName() { return winnerName; }

    /**
     * Sets the name of the winning player.
     *
     * @param winnerName the name to store as the winner
     */
    public static void setWinnerName(String winnerName) {
        GameResult.winnerName = winnerName;
    }
}