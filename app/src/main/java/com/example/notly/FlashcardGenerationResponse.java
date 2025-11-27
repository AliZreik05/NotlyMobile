package com.example.notly;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FlashcardGenerationResponse {

    @SerializedName("deck_id")
    private int deckId;

    private String title;

    private List<FlashcardItem> cards;

    public int getDeckId() {
        return deckId;
    }

    public String getTitle() {
        return title;
    }

    public List<FlashcardItem> getCards() {
        return cards;
    }
}
