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
}
