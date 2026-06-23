package com.example.miniproyecto_50zo.model;

import java.util.LinkedList;

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

    public void addCard(Card card) {
        hand.add(card);
    }

    public boolean playCard(Card card) {
        return hand.remove(card);
    }

    public LinkedList<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean isMachine() {
        return isMachine;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public int handSize() {
        return hand.size();
    }
}