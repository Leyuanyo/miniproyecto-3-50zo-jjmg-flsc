package com.example.miniproyecto_50zo.model;

public class DeckTest {
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println("Cartas en el mazo: " + deck.size()); // 52
        Card primera = deck.draw();
        System.out.println("Carta robada: " + primera);
        System.out.println("Cartas restantes: " + deck.size()); // 51
    }
}