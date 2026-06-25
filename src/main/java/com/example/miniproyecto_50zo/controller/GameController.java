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

/**
 * Main controller for the Cincuentazo game screen.
 * Implements {@link GameObserver} to receive and respond to model events,
 * and connects user interactions from the GUI to the {@link GameModel}.
 * Manages card rendering, turn flow, keyboard shortcuts, and player elimination display.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameController implements GameObserver {

    /** Display width in pixels for human player cards. */
    private static final double HUMAN_CARD_WIDTH = 130;

    /** Display height in pixels for human player cards. */
    private static final double HUMAN_CARD_HEIGHT = 185;

    /** Display width in pixels for machine player cards. */
    private static final double MACHINE_CARD_WIDTH = 90;

    /** Display height in pixels for machine player cards. */
    private static final double MACHINE_CARD_HEIGHT = 130;

    /** Display width in pixels for the center table card. */
    private static final double CENTER_CARD_WIDTH = 130;

    /** Display height in pixels for the center table card. */
    private static final double CENTER_CARD_HEIGHT = 185;

    /** ImageView displaying the top card of the deck. */
    @FXML
    private ImageView deckView;

    /** HBox containing the human player's hand. */
    @FXML
    private HBox humanHBox;

    /** HBox containing machine player 1's hand. */
    @FXML
    private HBox machine1HBox;

    /** HBox containing machine player 2's hand. */
    @FXML
    private HBox machine2HBox;

    /** HBox containing machine player 3's hand. */
    @FXML
    private HBox machine3HBox;

    /** Label displaying the current table sum. */
    @FXML
    private Label pointLabel;

    /** ImageView displaying the top card on the table. */
    @FXML
    private ImageView tableCenterView;

    /** The game model managing all game logic and state. */
    private GameModel gameModel;

    /** Runner responsible for executing machine player turns asynchronously. */
    private MachineTurnRunner machineTurnRunner;

    /** Whether the human player has already played a card this turn. */
    private boolean hasPlayedThisTurn;

    /**
     * Initializes the controller after the FXML is loaded.
     * Creates the game model, starts the game, sets up event handlers,
     * renders the initial state, and starts the first turn if it belongs to a machine.
     */
    @FXML
    public void initialize() {
        gameModel = new GameModel(this);
        gameModel.initGame(com.example.miniproyecto_50zo.view.GameSettings.getNumberOfMachines());
        machineTurnRunner = new MachineTurnRunner(gameModel);
        hasPlayedThisTurn = false;
        deckView.setOnMouseClicked(event -> handleDrawCard());
        setupKeyboardShortcuts();
        renderAll();
        startTurnIfMachine();
    }

    /**
     * Registers keyboard shortcuts on the scene once it becomes available.
     * Keys 1-4 play the card at the corresponding hand index.
     * SPACE draws a card from the deck.
     */
    private void setupKeyboardShortcuts() {
        humanHBox.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case DIGIT1 -> handlePlayCardAtIndex(0);
                        case DIGIT2 -> handlePlayCardAtIndex(1);
                        case DIGIT3 -> handlePlayCardAtIndex(2);
                        case DIGIT4 -> handlePlayCardAtIndex(3);
                        case SPACE  -> handleDrawCard();
                        default     -> { }
                    }
                });
            }
        });
    }

    /**
     * Plays the card at the given index in the human player's hand.
     *
     * @param index the zero-based index of the card in the hand
     */
    private void handlePlayCardAtIndex(int index) {
        Player human = gameModel.getPlayers().get(0);
        List<Card> hand = human.getHand();
        if (index < hand.size()) {
            handlePlayCard(hand.get(index));
        }
    }

    /**
     * Renders a player's hand into the given container.
     * Human cards show their front image with a click handler.
     * Machine cards show their back image with no interaction.
     *
     * @param container the {@link HBox} to populate
     * @param hand      the list of cards to render
     * @param isMachine {@code true} if the hand belongs to a machine player
     * @param width     the display width for each card image
     * @param height    the display height for each card image
     */
    private void renderHand(HBox container, List<Card> hand, boolean isMachine,
                            double width, double height) {
        container.getChildren().clear();
        for (Card card : hand) {
            Image image = isMachine
                    ? CardImageLoader.loadBack()
                    : CardImageLoader.loadFront(card);
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

    /**
     * Handles the human player's attempt to play a card.
     * Validates that it is the human's turn and that they have not already played this turn.
     *
     * @param card the card the human wants to play
     */
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

    /**
     * Handles the human player's attempt to draw a card from the deck.
     * Validates that it is the human's turn and that they have already played a card.
     */
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

    /**
     * Returns {@code true} if the current player is the human player.
     *
     * @return {@code true} if it is the human's turn
     */
    private boolean isHumanTurn() {
        return !gameModel.getCurrentPlayer().isMachine();
    }

    /**
     * Advances to the next turn, eliminates any players who cannot play,
     * and starts the machine turn if the next player is a machine.
     */
    private void advanceTurn() {
        gameModel.nextTurn();
        eliminatePlayersWhoCannotPlay();
        if (!gameModel.isGameOver()) {
            startTurnIfMachine();
        }
    }

    /**
     * Eliminates all consecutive players who have no playable card
     * given the current table sum, until a player who can play is found
     * or the game ends.
     */
    private void eliminatePlayersWhoCannotPlay() {
        while (!gameModel.isGameOver() &&
                !gameModel.getCurrentPlayer().canPlay(gameModel.getTableSum())) {
            gameModel.eliminateCurrentPlayer();
        }
    }

    /**
     * Starts the machine turn via {@link MachineTurnRunner}
     * if the current player is a machine.
     */
    private void startTurnIfMachine() {
        Player current = gameModel.getCurrentPlayer();
        if (current.isMachine()) {
            machineTurnRunner.runTurn(current, this::advanceTurn);
        }
    }

    /**
     * Displays a warning dialog with the given title and message.
     *
     * @param title   the dialog title
     * @param message the warning message to display
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Renders the top card of the table pile in the center image view.
     */
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

    /**
     * Updates the score label with the current table sum.
     */
    private void renderSum() {
        pointLabel.setText("Puntaje: " + gameModel.getTableSum());
    }

    /**
     * Re-renders all visual elements: all player hands, the table card, and the sum label.
     */
    private void renderAll() {
        List<Player> players = gameModel.getPlayers();
        HBox[] machineBoxes = { machine1HBox, machine2HBox, machine3HBox };

        for (Player player : players) {
            if (!player.isMachine()) {
                renderHand(humanHBox, player.getHand(), false,
                        HUMAN_CARD_WIDTH, HUMAN_CARD_HEIGHT);
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

    /**
     * {@inheritDoc}
     * Re-renders the full game view when a card is played.
     *
     * @param player the player who played the card
     * @param card   the card that was played
     * @param newSum the new table sum after the card was played
     */
    @Override
    public void onCardPlayed(Player player, Card card, int newSum) { renderAll(); }

    /**
     * {@inheritDoc}
     * Re-renders the full game view when a card is drawn.
     *
     * @param player the player who drew the card
     * @param card   the card that was drawn
     */
    @Override
    public void onCardDrawn(Player player, Card card) { renderAll(); }

    /**
     * {@inheritDoc}
     * Re-renders the view and shows an elimination warning dialog.
     *
     * @param player the player who was eliminated
     */
    @Override
    public void onPlayerEliminated(Player player) {
        renderAll();
        showWarning("Jugador eliminado", player.getName() + " fue eliminado del juego.");
    }

    /**
     * {@inheritDoc}
     * Saves the winner's name and navigates to the game-over screen.
     *
     * @param winner the last remaining player who won the game
     */
    @Override
    public void onGameOver(Player winner) {
        renderAll();
        com.example.miniproyecto_50zo.view.GameResult.setWinnerName(winner.getName());
        com.example.miniproyecto_50zo.view.GameStage.loadScene("game-over-view.fxml");
    }

    /**
     * {@inheritDoc}
     * Re-renders the full game view when the deck is recycled.
     */
    @Override
    public void onDeckRecycled() { renderAll(); }
}