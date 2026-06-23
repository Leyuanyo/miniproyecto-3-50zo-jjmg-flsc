package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.view.GameSettings;
import com.example.miniproyecto_50zo.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MainMenuController {

    @FXML
    private ImageView oneMachineButton;

    @FXML
    private ImageView twoMachineButton;

    @FXML
    private ImageView threeMachinesButton;

    @FXML
    public void initialize() {
        oneMachineButton.setOnMouseClicked(event -> startGame(1));
        twoMachineButton.setOnMouseClicked(event -> startGame(2));
        threeMachinesButton.setOnMouseClicked(event -> startGame(3));
    }

    private void startGame(int numberOfMachines) {
        GameSettings.setNumberOfMachines(numberOfMachines);
        GameStage.loadScene("game-view.fxml");
    }
}