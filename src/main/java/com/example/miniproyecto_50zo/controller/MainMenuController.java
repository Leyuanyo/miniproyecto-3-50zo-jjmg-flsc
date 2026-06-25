package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.view.GameSettings;
import com.example.miniproyecto_50zo.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * Controller for the main menu screen of the Cincuentazo game.
 * Handles the selection of the number of machine players
 * and navigates to the game screen.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class MainMenuController {

    /** Button for selecting one machine player. */
    @FXML
    private ImageView oneMachineButton;

    /** Button for selecting two machine players. */
    @FXML
    private ImageView twoMachineButton;

    /** Button for selecting three machine players. */
    @FXML
    private ImageView threeMachinesButton;

    /**
     * Initializes the controller after the FXML is loaded.
     * Assigns click handlers to each machine count button.
     */
    @FXML
    public void initialize() {
        oneMachineButton.setOnMouseClicked(event -> startGame(1));
        twoMachineButton.setOnMouseClicked(event -> startGame(2));
        threeMachinesButton.setOnMouseClicked(event -> startGame(3));
    }

    /**
     * Saves the selected number of machines and loads the game screen.
     *
     * @param numberOfMachines the number of machine players selected (1, 2, or 3)
     */
    private void startGame(int numberOfMachines) {
        GameSettings.setNumberOfMachines(numberOfMachines);
        GameStage.loadScene("game-view.fxml");
    }
}