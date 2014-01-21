package com.rallydev.pokerhand

enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
    JACK, QUEEN, KING 
}

enum Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS
}

class Card {
    Rank rank
    Suit suit
}
