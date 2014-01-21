package com.rallydev.pokerhand

enum Rank {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
    JACK, QUEEN, KING, ACE
}

enum Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS
}

class Card {
    Rank rank
    Suit suit
}
