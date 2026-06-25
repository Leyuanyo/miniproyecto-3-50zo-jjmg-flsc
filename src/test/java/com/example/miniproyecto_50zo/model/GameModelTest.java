package com.example.miniproyecto_50zo.model;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.interfaces.GameObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link GameModel} class.
 * Verifies game initialization, card play and draw logic,
 * player elimination, turn rotation, and end-game detection.
 * Uses {@link RecordingObserver} as a test double to track observer notifications.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
class GameModelTest {

    /** The game model under test. */
    private GameModel gameModel;

    /** The observer that records which game events were fired. */
    private RecordingObserver observer;

    /**
     * Initializes a fresh game model with one human and two machine players
     * before each test.
     */
    @BeforeEach
    void setUp() {
        observer = new RecordingObserver();
        gameModel = new GameModel(observer);
        gameModel.initGame(2);
    }

    /**
     * Verifies that initializing with 2 machines creates exactly 3 players
     * (1 human + 2 machines).
     */
    @Test
    void initGameShouldCreateCorrectNumberOfPlayers() {
        assertEquals(3, gameModel.getPlayers().size());
    }

    /**
     * Verifies that every player starts with exactly 4 cards in hand
     * after initialization.
     */
    @Test
    void eachPlayerShouldStartWithFourCards() {
        for (Player player : gameModel.getPlayers()) {
            assertEquals(4, player.getHand().size());
        }
    }

    /**
     * Verifies that the table starts with exactly one card placed face-up.
     */
    @Test
    void tableShouldStartWithOneCard() {
        assertNotNull(gameModel.getTopCard());
        assertEquals(1, gameModel.getTablePile().size());
    }

    /**
     * Verifies that playing a valid card correctly updates the table sum
     * and notifies the observer via {@code onCardPlayed}.
     *
     * @throws InvalidCardPlayException if the card is unexpectedly invalid
     */
    @Test
    void playingValidCardShouldUpdateSum() throws InvalidCardPlayException {
        Player human = gameModel.getPlayers().get(0);
        Card playableCard = new Card(Suit.HEARTS, Rank.TWO);
        human.getHand().add(playableCard);

        int sumBefore = gameModel.getTableSum();
        gameModel.playCard(human, playableCard);

        assertEquals(sumBefore + 2, gameModel.getTableSum());
        assertTrue(observer.cardPlayedCalled);
    }

    /**
     * Verifies that attempting to play a card that would cause the table sum
     * to exceed 50 throws an {@link InvalidCardPlayException}.
     */
    @Test
    void playingCardOverFiftyShouldThrowException() {
        Player human = gameModel.getPlayers().get(0);

        forceTableSumNear(49);

        Card eight = new Card(Suit.CLUBS, Rank.EIGHT);
        human.getHand().add(eight);

        assertThrows(InvalidCardPlayException.class, () -> gameModel.playCard(human, eight));
    }

    /**
     * Verifies that drawing a card increases the player's hand size by one
     * and notifies the observer via {@code onCardDrawn}.
     *
     * @throws EmptyDeckException if the deck is unexpectedly empty
     */
    @Test
    void drawingCardShouldAddToHand() throws EmptyDeckException {
        Player human = gameModel.getPlayers().get(0);
        int handSizeBefore = human.getHand().size();

        gameModel.drawCard(human);

        assertEquals(handSizeBefore + 1, human.getHand().size());
        assertTrue(observer.cardDrawnCalled);
    }

    /**
     * Verifies that eliminating the current player removes them from the active
     * player list and notifies the observer via {@code onPlayerEliminated}.
     */
    @Test
    void eliminatingPlayerShouldRemoveFromList() {
        int playersBefore = gameModel.getPlayers().size();

        gameModel.eliminateCurrentPlayer();

        assertEquals(playersBefore - 1, gameModel.getPlayers().size());
        assertTrue(observer.playerEliminatedCalled);
    }

    /**
     * Verifies that the game ends and the observer is notified via {@code onGameOver}
     * when only one player remains after successive eliminations.
     */
    @Test
    void gameShouldBeOverWithOnePlayerLeft() {
        gameModel.eliminateCurrentPlayer();
        gameModel.eliminateCurrentPlayer();

        assertTrue(gameModel.isGameOver());
        assertTrue(observer.gameOverCalled);
    }

    /**
     * Verifies that calling {@code nextTurn} advances to a different player
     * than the one who was current before the call.
     */
    @Test
    void nextTurnShouldAdvanceCircularly() {
        Player first = gameModel.getCurrentPlayer();

        gameModel.nextTurn();
        Player second = gameModel.getCurrentPlayer();

        assertNotEquals(first, second);
    }

    /**
     * Helper method that forces the table sum close to the given target
     * by repeatedly playing TWO cards from the human player's hand.
     * Stops when the next card would exceed the target or the table sum
     * has reached or passed it.
     *
     * @param target the desired approximate table sum
     */
    private void forceTableSumNear(int target) {
        Player human = gameModel.getPlayers().get(0);
        while (gameModel.getTableSum() < target) {
            Card filler = new Card(Suit.DIAMONDS, Rank.TWO);
            if (gameModel.getTableSum() + filler.getValue(gameModel.getTableSum()) > target) {
                break;
            }
            human.getHand().add(filler);
            try {
                gameModel.playCard(human, filler);
            } catch (InvalidCardPlayException e) {
                break;
            }
        }
    }

    /**
     * Test double implementation of {@link GameObserver} that records
     * which notification methods were called during a test.
     * Used to verify that the {@link GameModel} fires the correct events.
     */
    private static class RecordingObserver implements GameObserver {

        /** Whether {@code onCardPlayed} was called. */
        boolean cardPlayedCalled;

        /** Whether {@code onCardDrawn} was called. */
        boolean cardDrawnCalled;

        /** Whether {@code onPlayerEliminated} was called. */
        boolean playerEliminatedCalled;

        /** Whether {@code onGameOver} was called. */
        boolean gameOverCalled;

        /**
         * Records that a card was played.
         *
         * @param player the player who played the card
         * @param card   the card that was played
         * @param newSum the new table sum after the card was played
         */
        @Override
        public void onCardPlayed(Player player, Card card, int newSum) {
            cardPlayedCalled = true;
        }

        /**
         * Records that a card was drawn.
         *
         * @param player the player who drew the card
         * @param card   the card that was drawn
         */
        @Override
        public void onCardDrawn(Player player, Card card) {
            cardDrawnCalled = true;
        }

        /**
         * Records that a player was eliminated.
         *
         * @param player the player who was eliminated
         */
        @Override
        public void onPlayerEliminated(Player player) {
            playerEliminatedCalled = true;
        }

        /**
         * Records that the game ended with a winner.
         *
         * @param winner the last remaining player who won the game
         */
        @Override
        public void onGameOver(Player winner) {
            gameOverCalled = true;
        }

        /**
         * No recording needed for deck recycled events in these tests.
         */
        @Override
        public void onDeckRecycled() {
        }
    }
}