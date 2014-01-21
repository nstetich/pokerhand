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
}
