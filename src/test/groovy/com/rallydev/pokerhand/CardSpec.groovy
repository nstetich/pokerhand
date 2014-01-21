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

    def "suits should support comparison"() {
        expect:
        high > low
        low < high

        where:
        low            | high
        Suit.CLUBS     | Suit.DIAMONDS
        Suit.DIAMONDS  | Suit.SPADES
        Suit.SPADES    | Suit.HEARTS
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

    def "rank parsing should throw error for unknown string"() {
        when:
        Rank.parse(str)

        then:
        def e = thrown(IllegalArgumentException)
        e.message =~ /'$str'/

        where:
        str << ['0', '1', '11', 'j', 'q', 'k', 'a', '', null]
    }

    def "suit should support parsing from string representation"() {
        expect:
        suit == Suit.parse(str)

        where:
        suit          | str
        Suit.CLUBS    | 'c'
        Suit.DIAMONDS | 'd'
        Suit.SPADES   | 's'
        Suit.HEARTS   | 'h'
    }

    def "suit parsing should throw exception for unknown strings"() {
        when:
        Suit.parse(str)

        then:
        def e = thrown(IllegalArgumentException)
        e.message =~ /'$str'/

        where:
        str << ['C', 'D', 'S', 'H', '1', '', null]
    }

}
