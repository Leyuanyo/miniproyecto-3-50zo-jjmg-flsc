package com.example.miniproyecto_50zo.controller;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.Card;
import com.example.miniproyecto_50zo.model.GameModel;
import com.example.miniproyecto_50zo.model.Player;
import com.example.miniproyecto_50zo.model.interfaces.GameObserver;
import com.example.miniproyecto_50zo.util.CardImageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;

public class GameController implements GameObserver {

    // Card sizes matching the current FXML layout for the 1100x750 window
    private static final double HUMAN_CARD_WIDTH = 130;
    private static final double HUMAN_CARD_HEIGHT = 185;
    private static final double MACHINE_CARD_WIDTH = 90;
    private static final double MACHINE_CARD_HEIGHT = 130;
    private static final double CENTER_CARD_WIDTH = 130;
    private static final double CENTER_CARD_HEIGHT = 185;

    @FXML
    private ImageView deckView;

    @FXML
    private HBox humanHBox;

    @FXML
    private HBox machine1HBox;

    @FXML
    private HBox machine2HBox;

    @FXML
    private HBox machine3HBox;

    @FXML
    private Label pointLabel;

    @FXML
    private ImageView tableCenterView;

    private GameModel gameModel;
    private MachineTurnRunner machineTurnRunner;

    private boolean hasPlayedThisTurn;

    @FXML
    public void initialize() {
        gameModel = new GameModel(this);
        gameModel.initGame(com.example.miniproyecto_50zo.view.GameSettings.getNumberOfMachines());
        machineTurnRunner = new MachineTurnRunner(gameModel);
        hasPlayedThisTurn = false;

        deckView.setOnMouseClicked(event -> handleDrawCard());

        renderAll();
        startTurnIfMachine();
    }

    private void renderHand(HBox container, List<Card> hand, boolean isMachine,
                            double width, double height) {
        container.getChildren().clear();
        for (Card card : hand) {
            Image image = isMachine ? CardImageLoader.loadBack() : CardImageLoader.loadFront(card);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(true);

            if (!isMachine) {
                imageView.setOnMouseClicked(event -> handlePlayCard(card));
            }

            container.getChildren().add(imageView);
        }
    }

    private void handlePlayCard(Card card) {
        if (!isHumanTurn()) {
            showWarning("No es tu turno", "Espera a que termine el turno actual.");
            return;
        }
        if (hasPlayedThisTurn) {
            showWarning("Ya jugaste", "Ya jugaste una carta este turno. Ahora debes robar del mazo.");
            return;
        }

        Player human = gameModel.getPlayers().get(0);
        try {
            gameModel.playCard(human, card);
            hasPlayedThisTurn = true;
        } catch (InvalidCardPlayException e) {
            showWarning("Jugada inválida", e.getMessage());
        }
    }

    private void handleDrawCard() {
        if (!isHumanTurn()) {
            showWarning("No es tu turno", "Espera a que termine el turno actual.");
            return;
        }
        if (!hasPlayedThisTurn) {
            showWarning("Falta jugar", "Primero debes jugar una carta antes de robar.");
            return;
        }

        Player human = gameModel.getCurrentPlayer();
        try {
            gameModel.drawCard(human);
            hasPlayedThisTurn = false;
            advanceTurn();
        } catch (EmptyDeckException e) {
            showWarning("Mazo vacío", e.getMessage());
        }
    }

    private boolean isHumanTurn() {
        return !gameModel.getCurrentPlayer().isMachine();
    }

    private void advanceTurn() {
        gameModel.nextTurn();
        eliminatePlayersWhoCannotPlay();
        if (!gameModel.isGameOver()) {
            startTurnIfMachine();
        }
    }

    private void eliminatePlayersWhoCannotPlay() {
        while (!gameModel.isGameOver() && !gameModel.getCurrentPlayer().canPlay(gameModel.getTableSum())) {
            gameModel.eliminateCurrentPlayer();
        }
    }

    private void startTurnIfMachine() {
        Player current = gameModel.getCurrentPlayer();
        if (current.isMachine()) {
            machineTurnRunner.runTurn(current, this::advanceTurn);
        }
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void renderTableCenter() {
        Card topCard = gameModel.getTopCard();
        if (topCard == null) {
            tableCenterView.setImage(null);
            return;
        }
        tableCenterView.setImage(CardImageLoader.loadFront(topCard));
        tableCenterView.setFitWidth(CENTER_CARD_WIDTH);
        tableCenterView.setFitHeight(CENTER_CARD_HEIGHT);
        tableCenterView.setPreserveRatio(true);
    }

    private void renderSum() {
        pointLabel.setText("Puntaje: " + gameModel.getTableSum());
    }

    private void renderAll() {
        List<Player> players = gameModel.getPlayers();

        HBox[] machineBoxes = { machine1HBox, machine2HBox, machine3HBox };

        for (Player player : players) {
            if (!player.isMachine()) {
                renderHand(humanHBox, player.getHand(), false, HUMAN_CARD_WIDTH, HUMAN_CARD_HEIGHT);
            }
        }

        int machineSlot = 0;
        for (Player player : players) {
            if (player.isMachine() && machineSlot < machineBoxes.length) {
                renderHand(machineBoxes[machineSlot], player.getHand(), true,
                        MACHINE_CARD_WIDTH, MACHINE_CARD_HEIGHT);
                machineSlot++;
            }
        }
        for (int i = machineSlot; i < machineBoxes.length; i++) {
            machineBoxes[i].getChildren().clear();
        }

        renderTableCenter();
        renderSum();
    }

    @Override
    public void onCardPlayed(Player player, Card card, int newSum) {
        renderAll();
    }

    @Override
    public void onCardDrawn(Player player, Card card) {
        renderAll();
    }

    @Override
    public void onPlayerEliminated(Player player) {
        renderAll();
        showWarning("Jugador eliminado", player.getName() + " fue eliminado del juego.");
    }

    @Override
    public void onGameOver(Player winner) {
        renderAll();
        showWarning("Fin del juego", winner.getName() + " ha ganado la partida.");
        // Cambiar esto por un fxml para el HU-6
    }

    @Override
    public void onDeckRecycled() {
        renderAll();
    }
}