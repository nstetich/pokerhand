package com.rallydev.pokerhand

import spock.lang.Specification

class CardSpec extends Specification {
    def "card should have a rank and a suit"() {
        given: 
        def card = new Card(rank:rank, suit:suit)
        
        expect:
        card != null
        card.rank == rank
        card.suit == suit

        where:
        rank       | suit
        Rank.ACE   | Suit.HEARTS
        Rank.QUEEN | Suit.SPADES
   }

    def "there should be four suits"() {
        expect:
        Suit.values().size() == 4
    }

    def "there should be thirteen ranks"() {
        expect:
        Rank.values().size() == 13
    }

    def "ranks should support comparison"() {
        expect:
        high > low
        low < high

        where:
        low       | high
        Rank.TWO  | Rank.THREE
        Rank.FIVE | Rank.JACK
        Rank.JACK | Rank.QUEEN
        Rank.KING | Rank.ACE
    }

    def "ace should outrank all other ranks"() {
        expect:
        Rank.ACE > otherRank

        where:
        otherRank << (Rank.values() - Rank.ACE)
    }

    def "rank should support parsing from string representation"() {
        expect:
        rank == Rank.parse(str)

        where:
        rank       | str
        Rank.TWO   | '2'
        Rank.THREE | '3'
        Rank.FOUR  | '4'
        Rank.FIVE  | '5'
        Rank.SIX   | '6'
        Rank.SEVEN | '7'
        Rank.EIGHT | '8'
        Rank.NINE  | '9'
        Rank.TEN   | '10'
        Rank.JACK  | 'J'
        Rank.QUEEN | 'Q'
        Rank.KING  | 'K'
        Rank.ACE   | 'A'
    }

}
