package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.view.GameResult;
import com.example.miniproyecto_50zo.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameOverController {

    @FXML
    private Label winnerLabel;

    @FXML
    public void initialize() {
        winnerLabel.setText(buildWinnerMessage(GameResult.getWinnerName()));
    }

    private String buildWinnerMessage(String winnerName) {
        switch (winnerName) {
            case "Machine 1":
                return "Maquina 1 gano";
            case "Machine 2":
                return "Maquina 2 gano";
            case "Machine 3":
                return "Maquina 3 gano";
            case "Player":
                return "Jugador gano";
            default:
                return winnerName + " gano";
        }
    }

    @FXML
    void handleMenu(MouseEvent event) {
        GameStage.loadScene("main-menu-view.fxml");
    }
}