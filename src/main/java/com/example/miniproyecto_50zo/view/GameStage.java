package com.example.miniproyecto_50zo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

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