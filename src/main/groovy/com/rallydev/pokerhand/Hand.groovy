package com.rallydev.pokerhand

class Hand {
    public final List<Card> cards;
    public final List<Card> sortedCards;

    Hand(List<Card> cards) {
        if (cards?.size() != 5) {
            throw new IllegalArgumentException("A hand must have exactly five cards.")
        }
        if (hasDuplicates(cards)) {
            throw new IllegalArgumentException("A hand must not have duplicates of a card.")
        }
       this.cards = cards
       this.sortedCards = cards.sort(false) { a, b -> b <=> a }
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

}

enum Outcome {
    HIGH_CARD({ hand ->
        return [hand.sortedCards.first()]  
    }),
    PAIR({ hand ->
        return Outcome.findPair(hand.sortedCards)
    }),
    TWO_PAIR({ hand ->
        def cards = hand.sortedCards
        def firstPair = Outcome.findPair(cards)
        if (firstPair) {
            def secondPair = Outcome.findPair(cards - firstPair)
            if (secondPair) {
                return firstPair + secondPair
            }
        }
        return null
    });
//     THREE_OF_A_KIND,
//     STRAIGHT,
//     FLUSH,
//     FULL_HOUSE,
//     FOUR_OF_A_KIND,
//     STRAIGHT_FLUSH,
//     ROYAL_FLUSH
    public final Closure closure

    Outcome(Closure closure) {
        this.closure = closure
    }

    public List<Card> call(Hand hand) {
        return closure.call(hand)
    }

    public static List<Card> findPair(List<Card> sortedCards) {
        for (int n = 0; n < sortedCards.size() - 1; n++) {
            def a = sortedCards[n]
            def b = sortedCards[n + 1]
            if (a.rank == b.rank) {
                return [a, b]
            }
        }
        return null;
    }
}
