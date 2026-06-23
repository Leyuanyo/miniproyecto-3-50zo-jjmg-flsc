package com.example.miniproyecto_50zo.model;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Deck {

    private final Deque<Card> cards;

    public Deck() {
        this.cards = new ArrayDeque<>();
        buildFullDeck();
        shuffle();
    }

    private void buildFullDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        List<Card> temp = new java.util.ArrayList<>(cards);
        Collections.shuffle(temp);
        cards.clear();
        cards.addAll(temp);
    }

    public Card draw() {
        return cards.poll();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public void refillFromTablePile(List<Card> tableCardsExceptLast) {
        cards.addAll(tableCardsExceptLast);
        shuffle();
    }
}