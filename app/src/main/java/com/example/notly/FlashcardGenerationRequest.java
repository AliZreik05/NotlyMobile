package com.example.notly;

import com.google.gson.annotations.SerializedName;

public class FlashcardGenerationRequest {
    private String text;

    @SerializedName("num_cards")
    private int numCards;

    private String title;

    public FlashcardGenerationRequest(String text, int numCards, String title) {
        this.text = text;
        this.numCards = numCards;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public int getNumCards() {
        return numCards;
    }

    public String getTitle() {
        return title;
    }
}
