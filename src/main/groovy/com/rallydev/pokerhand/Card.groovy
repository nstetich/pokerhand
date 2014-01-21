package com.rallydev.pokerhand

enum Rank {
    TWO   ('2'), 
    THREE ('3'), 
    FOUR  ('4'), 
    FIVE  ('5'), 
    SIX   ('6'), 
    SEVEN ('7'), 
    EIGHT ('8'), 
    NINE  ('9'), 
    TEN   ('10'),
    JACK  ('J'), 
    QUEEN ('Q'), 
    KING  ('K'), 
    ACE   ('A');

    public final String text

    Rank(String text) {
        this.text = text
    }
    
    public static Rank parse(String str) {
        def rank = values().find { it.text == str }
        if (!rank)
            throw new IllegalArgumentException("Invalid rank '${str}'")
        return rank
    }
}

enum Suit {
    CLUBS    ('c'),
    DIAMONDS ('d'), 
    SPADES   ('s'), 
    HEARTS   ('h'); 

    public final String text

    Suit(String text) {
        this.text = text
    }

    public static Suit parse(String str) {
        def suit = values().find { it.text == str }
        if (!suit)
            throw new IllegalArgumentException("Invalid suit '${str}'")
        return suit
    }
}

class Card {
    Rank rank
    Suit suit

    public static Card parse(String str) {
        def trimmed = str?.trim()
        if (trimmed == null || !(trimmed.size() in 2..3)) {
            throw new IllegalArgumentException("Invalid card '${str}'")
        }
        def rank = trimmed[0..-2] 
        def suit = trimmed[-1]
        return new Card(rank: Rank.parse(rank), suit: Suit.parse(suit))
    }
}
