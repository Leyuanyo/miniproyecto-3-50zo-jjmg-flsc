package com.example.miniproyecto_50zo.model;

import com.example.miniproyecto_50zo.exceptions.EmptyDeckException;
import com.example.miniproyecto_50zo.exceptions.InvalidCardPlayException;
import com.example.miniproyecto_50zo.model.interfaces.GameObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel gameModel;
    private RecordingObserver observer;

    @BeforeEach
    void setUp() {
        observer = new RecordingObserver();
        gameModel = new GameModel(observer);
        gameModel.initGame(2);
    }

    @Test
    void initGameShouldCreateCorrectNumberOfPlayers() {
        assertEquals(3, gameModel.getPlayers().size());
    }

    @Test
    void eachPlayerShouldStartWithFourCards() {
        for (Player player : gameModel.getPlayers()) {
            assertEquals(4, player.getHand().size());
        }
    }

    @Test
    void tableShouldStartWithOneCard() {
        assertNotNull(gameModel.getTopCard());
        assertEquals(1, gameModel.getTablePile().size());
    }

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

    @Test
    void playingCardOverFiftyShouldThrowException() {
        Player human = gameModel.getPlayers().get(0);

        forceTableSumNear(49);

        Card eight = new Card(Suit.CLUBS, Rank.EIGHT);
        human.getHand().add(eight);

        assertThrows(InvalidCardPlayException.class, () -> gameModel.playCard(human, eight));
    }

    @Test
    void drawingCardShouldAddToHand() throws EmptyDeckException {
        Player human = gameModel.getPlayers().get(0);
        int handSizeBefore = human.getHand().size();

        gameModel.drawCard(human);

        assertEquals(handSizeBefore + 1, human.getHand().size());
        assertTrue(observer.cardDrawnCalled);
    }

    @Test
    void eliminatingPlayerShouldRemoveFromList() {
        int playersBefore = gameModel.getPlayers().size();

        gameModel.eliminateCurrentPlayer();

        assertEquals(playersBefore - 1, gameModel.getPlayers().size());
        assertTrue(observer.playerEliminatedCalled);
    }

    @Test
    void gameShouldBeOverWithOnePlayerLeft() {
        gameModel.eliminateCurrentPlayer();
        gameModel.eliminateCurrentPlayer();

        assertTrue(gameModel.isGameOver());
        assertTrue(observer.gameOverCalled);
    }

    @Test
    void nextTurnShouldAdvanceCircularly() {
        Player first = gameModel.getCurrentPlayer();

        gameModel.nextTurn();
        Player second = gameModel.getCurrentPlayer();

        assertNotEquals(first, second);
    }

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

    private static class RecordingObserver implements GameObserver {
        boolean cardPlayedCalled;
        boolean cardDrawnCalled;
        boolean playerEliminatedCalled;
        boolean gameOverCalled;

        @Override
        public void onCardPlayed(Player player, Card card, int newSum) {
            cardPlayedCalled = true;
        }

        @Override
        public void onCardDrawn(Player player, Card card) {
            cardDrawnCalled = true;
        }

        @Override
        public void onPlayerEliminated(Player player) {
            playerEliminatedCalled = true;
        }

        @Override
        public void onGameOver(Player winner) {
            gameOverCalled = true;
        }

        @Override
        public void onDeckRecycled() {
        }
    }
}