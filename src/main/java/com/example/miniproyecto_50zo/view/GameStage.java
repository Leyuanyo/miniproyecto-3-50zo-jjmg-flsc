package com.example.miniproyecto_50zo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class that centralizes JavaFX stage and scene management
 * for the Cincuentazo application.
 * Holds a reference to the primary {@link Stage} and provides
 * a single method to navigate between FXML-defined screens.
 * This class cannot be instantiated.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameStage {

    /** The primary JavaFX stage shared across the application. */
    private static Stage primaryStage;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameStage() { }

    /**
     * Returns the primary stage of the application.
     *
     * @return the primary {@link Stage}
     */
    public static Stage getPrimaryStage() { return primaryStage; }

    /**
     * Registers the primary stage and configures its title,
     * resize behavior, and application icon.
     *
     * @param stage the primary {@link Stage} provided by JavaFX
     */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("50zo");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(
                new Image(GameStage.class.getResourceAsStream(
                        "/com/example/miniproyecto_50zo/images/game-icon.png"
                ))
        );
    }

    /**
     * Loads the given FXML file and sets it as the current scene on the primary stage.
     *
     * @param fxmlName the name of the FXML file to load (e.g. {@code "game-view.fxml"})
     */
    public static void loadScene(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    GameStage.class.getResource(
                            "/com/example/miniproyecto_50zo/" + fxmlName
                    )
            );
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}