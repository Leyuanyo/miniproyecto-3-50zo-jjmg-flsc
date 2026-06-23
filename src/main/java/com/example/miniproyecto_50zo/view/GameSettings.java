package com.example.miniproyecto_50zo.view;

public class GameSettings {

    private static int numberOfMachines = 1;

    private GameSettings() {
    }

    public static int getNumberOfMachines() {
        return numberOfMachines;
    }

    public static void setNumberOfMachines(int numberOfMachines) {
        if (numberOfMachines < 1 || numberOfMachines > 3) {
            throw new IllegalArgumentException("Number of machines must be 1, 2 or 3.");
        }
        GameSettings.numberOfMachines = numberOfMachines;
    }
}