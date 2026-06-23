package com.example.miniproyecto_50zo.util;

import com.example.miniproyecto_50zo.model.Card;
import javafx.scene.image.Image;

public class CardImageLoader {

    private static final String CARD_IMAGE_PATH = "/com/example/miniproyecto_50zo/images/cards/";
    private static final String CARD_BACK_FILE = "Deck.png";

    public static Image loadFront(Card card) {
        String path = CARD_IMAGE_PATH + card.getImageFileName();
        return new Image(CardImageLoader.class.getResourceAsStream(path));
    }

    public static Image loadBack() {
        String path = CARD_IMAGE_PATH + CARD_BACK_FILE;
        return new Image(CardImageLoader.class.getResourceAsStream(path));
    }

    public static Image loadForCard(Card card) {
        return card.isFaceUp() ? loadFront(card) : loadBack();
    }
}