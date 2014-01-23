package com.rallydev.pokerhand

import static com.rallydev.pokerhand.Rank.*
import static com.rallydev.pokerhand.Suit.*

class Hand {
    public final List<Card> cards;
    public final List<Card> sortedCards;
    public final Map<Rank, Collection<Card>> cardsByRank;
    public final Map<Suit, Collection<Card>> cardsBySuit;

    Hand(List<Card> cards) {
        if (cards?.size() != 5) {
            throw new IllegalArgumentException("A hand must have exactly five cards.")
        }
        if (hasDuplicates(cards)) {
            throw new IllegalArgumentException("A hand must not have duplicates of a card.")
        }
       this.cards = cards
       sortedCards = cards.sort(false)
       cardsByRank = sortedCards.groupBy { card -> card.rank }
       cardsBySuit = sortedCards.groupBy { card -> card.suit }
    }

    public static final parse(String str) {
        return new Hand(str?.split(/\s+/).collect{ Card.parse(it) })
    }

    private static boolean hasDuplicates(Collection<Card> cards) {
        def counts = [:]
        for (Card card : cards) {
            def count = counts.get(card, 0)
            counts[card] = count + 1
        }
        return counts.values().find { count -> count > 1 }  
    }

    public boolean isStraight() {
        boolean straight = true
        int end = sortedCards.size() - 1
        // Ace may serve as low card
        if (cardsByRank[ACE] && cardsByRank[TWO]) {
            end = sortedCards.size() - 2   
        }
        for (int n = 0; n < end; n++) {
            Card current = sortedCards[n]
            Card next = sortedCards[n + 1]
            straight = straight && current.isAdjacentTo(next)
        }
        return straight
    }

    public boolean isFlush() {
        return 5 in cardsBySuit.values()*.size()
    }

    public Evaluation evaluate() { 
        def e
        def flush = isFlush()
        def straight = isStraight()
        if (straight && flush) {
            if (sortedCards.first().rank == TEN) {
                return new Evaluation(Outcome.ROYAL_FLUSH, sortedCards)
            } 
            else {
                return new Evaluation(Outcome.STRAIGHT_FLUSH, sortedCards)
            }
        } 
    }

}

class Evaluation {
    final Outcome outcome
    final Collection<Card> cards

    Evaluation(Outcome outcome, Collection<Card> cards) {
        this.outcome = outcome
        this.cards = cards
    }
}

enum Outcome {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH
}
