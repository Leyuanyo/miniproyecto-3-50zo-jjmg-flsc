package com.example.miniproyecto_50zo.view;

public class GameResult {

    private static String winnerName = "";

    private GameResult() {
    }

    public static String getWinnerName() {
        return winnerName;
    }

    public static void setWinnerName(String winnerName) {
        GameResult.winnerName = winnerName;
    }
}