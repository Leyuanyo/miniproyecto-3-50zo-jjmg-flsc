package com.example.miniproyecto_50zo.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    private final String name;
    private final boolean isMachine;
    private final LinkedList<Card> hand;
    private boolean eliminated;

    public Player(String name, boolean isMachine) {
        this.name = name;
        this.isMachine = isMachine;
        this.hand = new LinkedList<>();
        this.eliminated = false;
    }

    public Card selectCard(int currentSum) {
        if (!isMachine) return null;

        List<Card> playable = hand.stream()
                .filter(card -> card.isPlayable(currentSum))
                .collect(Collectors.toList());

        if (playable.isEmpty()) return null;

        Card best = null;
        int bestSum = -1;

        for (Card card : playable) {
            int result = currentSum + card.getValue(currentSum);
            if (result > bestSum) {
                bestSum = result;
                best = card;
            }
        }

        return best;
    }

    public boolean canPlay(int currentSum) {
        for (Card card : hand) {
            if (card.isPlayable(currentSum)) return true;
        }
        return false;
    }

    public void drawCard(Card card) { hand.add(card); }

    public boolean isEliminated() { return eliminated; }

    public void eliminate() { this.eliminated = true; }

    public void removeCard(Card card) { hand.remove(card); }

    public List<Card> clearHand() {
        List<Card> remaining = new LinkedList<>(hand);
        hand.clear();
        return remaining;
    }

    public boolean isMachine() { return isMachine; }
    public String getName() { return name; }
    public LinkedList<Card> getHand() { return hand; }

    @Override
    public String toString() { return name; }
}