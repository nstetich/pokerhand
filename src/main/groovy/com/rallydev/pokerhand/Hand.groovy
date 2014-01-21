package com.rallydev.pokerhand

class Hand {
    public final List<Card> cards;

    Hand(List<Card> cards) {
        if (cards?.size() != 5) {
            throw new IllegalArgumentException("A hand must have exactly five cards.")
        }
        this.cards = cards
    }

    public static final parse(String str) {
        return new Hand(str?.split(/\s+/).collect{ Card.parse(it) })
    }
}
