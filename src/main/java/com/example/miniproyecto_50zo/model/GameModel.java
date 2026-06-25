package com.example.miniproyecto_50zo.model;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.GameAlreadyOverException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.interfaces.GameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Central model class for the Cincuentazo game.
 * Manages the full game state including the deck, active players,
 * table pile, current table sum, and turn order.
 * Notifies a {@link GameObserver} whenever a significant game event occurs,
 * following the Observer design pattern to maintain MVC separation.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameModel {

    /** The deck of cards used in the game. */
    private Deck deck;

    /** The list of currently active (non-eliminated) players. */
    private List<Player> players;

    /** The pile of cards that have been played onto the table. */
    private List<Card> tablePile;

    /** The current sum of all cards played on the table. */
    private int tableSum;

    /** The index of the player whose turn it currently is. */
    private int currentPlayerIndex;

    /** The observer notified on every game event. */
    private final GameObserver observer;

    /**
     * Constructs a GameModel with the given observer.
     * The observer will be notified of all game events.
     *
     * @param observer the {@link GameObserver} to notify on game events
     */
    public GameModel(GameObserver observer) {
        this.observer = observer;
        this.players = new ArrayList<>();
        this.tablePile = new ArrayList<>();
        this.tableSum = 0;
        this.currentPlayerIndex = 0;
    }

    /**
     * Initializes a new game with one human player and the specified
     * number of machine players.
     * Resets all state, creates players, deals initial hands,
     * and places the first card on the table.
     *
     * @param numMachines the number of machine players (1, 2, or 3)
     */
    public void initGame(int numMachines) {
        deck = new Deck();
        players.clear();
        tablePile.clear();
        tableSum = 0;
        currentPlayerIndex = 0;

        players.add(new Player("Player", false));
        for (int i = 1; i <= numMachines; i++) {
            players.add(new Player("Machine " + i, true));
        }

        dealInitialHands();
        placeInitialCard();
    }

    /**
     * Deals 4 cards to each player from the deck.
     * Human player cards are dealt face-up; machine player cards are dealt face-down.
     */
    private void dealInitialHands() {
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                Card card = deck.dealCard();
                card.setFaceUp(!player.isMachine());
                player.drawCard(card);
            }
        }
    }

    /**
     * Draws one card from the deck, places it face-up on the table,
     * and initializes the table sum based on its value.
     */
    private void placeInitialCard() {
        Card initial = deck.dealCard();
        initial.setFaceUp(true);
        tablePile.add(initial);
        tableSum += initial.getValue(0);
    }

    /**
     * Plays the given card from the given player's hand onto the table.
     * Updates the table sum, removes the card from the player's hand,
     * and notifies the observer.
     *
     * @param player the player playing the card
     * @param card   the card to play
     * @throws InvalidCardPlayException if playing the card would exceed a table sum of 50
     * @throws GameAlreadyOverException if the game has already ended
     */
    public void playCard(Player player, Card card) throws InvalidCardPlayException {
        if (isGameOver()) throw new GameAlreadyOverException("The game is already over.");
        if (!card.isPlayable(tableSum)) throw new InvalidCardPlayException(
                "Card " + card + " cannot be played. Current sum: " + tableSum);

        tableSum += card.getValue(tableSum);
        player.removeCard(card);
        card.setFaceUp(true);
        tablePile.add(card);
        observer.onCardPlayed(player, card, tableSum);
    }

    /**
     * Draws a card from the deck and adds it to the given player's hand.
     * If the deck is empty, the table pile is recycled first.
     * The drawn card is face-up for the human player and face-down for machines.
     *
     * @param player the player drawing the card
     * @throws EmptyDeckException       if the deck is empty and the pile cannot be recycled
     * @throws GameAlreadyOverException if the game has already ended
     */
    public void drawCard(Player player) throws EmptyDeckException {
        if (isGameOver()) throw new GameAlreadyOverException("The game is already over.");
        if (deck.isEmpty()) {
            if (tablePile.size() <= 1) throw new EmptyDeckException(
                    "Deck is empty and pile cannot be recycled.");
            recyclePile();
        }
        Card card = deck.dealCard();
        card.setFaceUp(!player.isMachine());
        player.drawCard(card);
        observer.onCardDrawn(player, card);
    }

    /**
     * Recycles all table pile cards except the last played one back into the deck.
     * The cards are shuffled before being added to the deck.
     * The current table sum is not modified.
     */
    private void recyclePile() {
        if (tablePile.size() <= 1) return;
        List<Card> toRecycle = new ArrayList<>(tablePile.subList(0, tablePile.size() - 1));
        tablePile.subList(0, tablePile.size() - 1).clear();
        deck.recyclePile(toRecycle);
        observer.onDeckRecycled();
    }

    /**
     * Eliminates the current player from the game.
     * Returns their cards to the bottom of the deck, removes them from
     * the active player list, adjusts the turn index if necessary,
     * and checks if the game is over.
     *
     * @throws GameAlreadyOverException if the game has already ended
     */
    public void eliminateCurrentPlayer() {
        if (isGameOver()) throw new GameAlreadyOverException("The game is already over.");
        Player player = getCurrentPlayer();
        player.eliminate();
        List<Card> cards = player.clearHand();
        for (Card card : cards) deck.addToBottom(card);
        observer.onPlayerEliminated(player);
        players.remove(currentPlayerIndex);
        if (currentPlayerIndex >= players.size()) currentPlayerIndex = 0;
        checkWinner();
    }

    /**
     * Advances the turn to the next active player in circular order.
     */
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Checks if only one player remains and notifies the observer if so.
     */
    private void checkWinner() {
        if (players.size() == 1) observer.onGameOver(players.get(0));
    }

    /**
     * Returns {@code true} if only one player remains and the game is over.
     *
     * @return {@code true} if the game has ended
     */
    public boolean isGameOver() { return players.size() == 1; }

    /**
     * Returns the player whose turn it currently is.
     *
     * @return the current {@link Player}
     */
    public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }

    /**
     * Returns the current sum of all cards played on the table.
     *
     * @return the table sum
     */
    public int getTableSum() { return tableSum; }

    /**
     * Returns the list of all active (non-eliminated) players.
     *
     * @return list of active players
     */
    public List<Player> getPlayers() { return players; }

    /**
     * Returns the full list of cards currently on the table pile.
     *
     * @return the table pile
     */
    public List<Card> getTablePile() { return tablePile; }

    /**
     * Returns the top card of the table pile, or {@code null} if the pile is empty.
     *
     * @return the most recently played card on the table
     */
    public Card getTopCard() {
        return tablePile.isEmpty() ? null : tablePile.get(tablePile.size() - 1);
    }
}