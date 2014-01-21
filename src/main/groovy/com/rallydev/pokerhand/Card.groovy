package com.rallydev.pokerhand

enum Rank {
    TWO('2'), 
    THREE('3'), 
    FOUR('4'), 
    FIVE('5'), 
    SIX('6'), 
    SEVEN('7'), 
    EIGHT('8'), 
    NINE('9'), 
    TEN('10'),
    JACK('J'), 
    QUEEN('Q'), 
    KING('K'), 
    ACE('A');

    public final String text

    Rank(String text) {
        this.text = text
    }
    
    public static Rank parse(String str) {
       return values().find { it.text == str }
    }
}

enum Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS
}

class Card {
    Rank rank
    Suit suit
}
