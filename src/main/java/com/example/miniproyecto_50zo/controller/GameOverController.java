package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.view.GameResult;
import com.example.miniproyecto_50zo.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Controller for the game-over screen of the Cincuentazo game.
 * Displays the name of the winning player and provides a button
 * to return to the main menu.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameOverController {

    /** Label displaying the winner's name. */
    @FXML
    private Label winnerLabel;

    /**
     * Initializes the controller after the FXML is loaded.
     * Reads the winner's name from {@link GameResult} and displays it.
     */
    @FXML
    public void initialize() {
        winnerLabel.setText(buildWinnerMessage(GameResult.getWinnerName()));
    }

    /**
     * Builds a human-readable winner message from the internal player name.
     *
     * @param winnerName the internal name of the winning player
     * @return a localized display message for the winner
     */
    private String buildWinnerMessage(String winnerName) {
        switch (winnerName) {
            case "Machine 1": return "Maquina 1 gano";
            case "Machine 2": return "Maquina 2 gano";
            case "Machine 3": return "Maquina 3 gano";
            case "Player":    return "Jugador gano";
            default:          return winnerName + " gano";
        }
    }

    /**
     * Handles the click event on the menu button.
     * Navigates back to the main menu screen.
     *
     * @param event the mouse event triggered by the click
     */
    @FXML
    void handleMenu(MouseEvent event) {
        GameStage.loadScene("main-menu-view.fxml");
    }
}