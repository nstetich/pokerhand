package com.rallydev.pokerhand

class Hand {
    public final List<Card> cards;

    Hand(List<Card> cards) {
        if (cards?.size() != 5) {
            throw new IllegalArgumentException("A hand must have exactly five cards.")
        }
        def counts = [:]
        for (Card card : cards) {
            def count = counts.get(card, 0)
            counts[card] = count + 1
        }
        if (counts.values().find { count -> count > 1 }) {
            throw new IllegalArgumentException("A hand must not have duplicates of a card.")
        }
        this.cards = cards
    }

    public static final parse(String str) {
        return new Hand(str?.split(/\s+/).collect{ Card.parse(it) })
    }
}
