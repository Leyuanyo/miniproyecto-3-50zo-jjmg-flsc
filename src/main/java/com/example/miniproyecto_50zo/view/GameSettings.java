package com.example.miniproyecto_50zo.view;

/**
 * Utility class that stores the number of machine players selected
 * in the main menu for use by the game controller.
 * Acts as a simple data bridge between scenes.
 * This class cannot be instantiated.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameSettings {

    /** The number of machine players for the current game session. */
    private static int numberOfMachines = 1;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameSettings() { }

    /**
     * Returns the number of machine players selected by the user.
     *
     * @return the number of machine players (1, 2, or 3)
     */
    public static int getNumberOfMachines() { return numberOfMachines; }

    /**
     * Sets the number of machine players for the next game.
     *
     * @param numberOfMachines the number of machine players to set
     * @throws IllegalArgumentException if the value is not between 1 and 3
     */
    public static void setNumberOfMachines(int numberOfMachines) {
        if (numberOfMachines < 1 || numberOfMachines > 3) {
            throw new IllegalArgumentException("Number of machines must be 1, 2 or 3.");
        }
        GameSettings.numberOfMachines = numberOfMachines;
    }
}