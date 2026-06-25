package com.example.miniproyecto_50zo;

import com.example.miniproyecto_50zo.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point of the Cincuentazo JavaFX application.
 * Initializes the primary stage and loads the main menu screen.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {

    /**
     * Called by the JavaFX runtime to start the application.
     * Registers the primary stage with {@link GameStage} and loads the main menu.
     *
     * @param primaryStage the primary window provided by JavaFX
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        GameStage.setPrimaryStage(primaryStage);
        GameStage.loadScene("main-menu-view.fxml");
    }
}