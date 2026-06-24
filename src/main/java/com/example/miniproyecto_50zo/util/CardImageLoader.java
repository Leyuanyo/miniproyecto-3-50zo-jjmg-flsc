package com.example.miniproyecto_50zo.util;

import com.example.miniproyecto_50zo.model.Card;
import javafx.scene.image.Image;

/**
 * Utility class for loading card images from the application's resource directory.
 * Provides methods to load the front or back image of a card,
 * and to automatically choose between them based on the card's face state.
 * This class cannot be instantiated.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class CardImageLoader {

    /** Base path to the card image resources directory. */
    private static final String CARD_IMAGE_PATH =
            "/com/example/miniproyecto_50zo/images/cards/";

    /** File name of the card back image. */
    private static final String CARD_BACK_FILE = "Deck.png";

    /**
     * Private constructor to prevent instantiation.
     */
    private CardImageLoader() { }

    /**
     * Loads and returns the front face image of the given card.
     *
     * @param card the card whose front image to load
     * @return the front {@link Image} of the card
     */
    public static Image loadFront(Card card) {
        String path = CARD_IMAGE_PATH + card.getImageFileName();
        return new Image(CardImageLoader.class.getResourceAsStream(path));
    }

    /**
     * Loads and returns the back face image used for all face-down cards.
     *
     * @return the back {@link Image} shared by all face-down cards
     */
    public static Image loadBack() {
        String path = CARD_IMAGE_PATH + CARD_BACK_FILE;
        return new Image(CardImageLoader.class.getResourceAsStream(path));
    }

    /**
     * Loads the appropriate image for the given card based on its face state.
     * Returns the front image if the card is face-up, or the back image if face-down.
     *
     * @param card the card to load the image for
     * @return the {@link Image} corresponding to the card's current face state
     */
    public static Image loadForCard(Card card) {
        return card.isFaceUp() ? loadFront(card) : loadBack();
    }
}