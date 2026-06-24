package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameOverController {

    @FXML
    private ImageView backToMenuButton;

    @FXML
    private Label winnerLabel;

    @FXML
    void handleMenu(MouseEvent event) {
        GameStage.loadScene("main-menu-view.fxml");
    }

}

