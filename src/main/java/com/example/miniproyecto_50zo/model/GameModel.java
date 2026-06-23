package com.example.miniproyecto_50zo.model;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.GameAlreadyOverException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.interfaces.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private Deck deck;
    private List<Player> players;
    private List<Card> tablePile;
    private int tableSum;
    private int currentPlayerIndex;
    private final GameObserver observer;

    public GameModel(GameObserver observer) {
        this.observer = observer;
        this.players = new ArrayList<>();
        this.tablePile = new ArrayList<>();
        this.tableSum = 0;
        this.currentPlayerIndex = 0;
    }

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

    private void dealInitialHands() {
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                Card card = deck.dealCard();
                card.setFaceUp(!player.isMachine());
                player.drawCard(card);
            }
        }
    }

    private void placeInitialCard() {
        Card initial = deck.dealCard();
        initial.setFaceUp(true);
        tablePile.add(initial);
        tableSum += initial.getValue(0);
    }

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

    private void recyclePile() {
        if (tablePile.size() <= 1) return;
        List<Card> toRecycle = new ArrayList<>(tablePile.subList(0, tablePile.size() - 1));
        tablePile.subList(0, tablePile.size() - 1).clear();
        deck.recyclePile(toRecycle);
        observer.onDeckRecycled();
    }

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

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private void checkWinner() {
        if (players.size() == 1) observer.onGameOver(players.get(0));
    }

    public boolean isGameOver() { return players.size() == 1; }
    public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }
    public int getTableSum() { return tableSum; }
    public List<Player> getPlayers() { return players; }
    public List<Card> getTablePile() { return tablePile; }
    public Card getTopCard() {
        return tablePile.isEmpty() ? null : tablePile.get(tablePile.size() - 1);
    }
}