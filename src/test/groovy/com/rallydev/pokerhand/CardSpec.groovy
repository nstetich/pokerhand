package com.rallydev.pokerhand

import spock.lang.Specification

import static com.rallydev.pokerhand.Suit.*
import static com.rallydev.pokerhand.Rank.*

class CardSpec extends Specification {
    def "card should have a rank and a suit"() {
        given: 
        def card = new Card(rank, suit)
        
        expect:
        card != null
        card.rank == rank
        card.suit == suit

        where:
        rank       | suit
        Rank.ACE   | Suit.HEARTS
        Rank.QUEEN | Suit.SPADES
   }

    def "card constructor should throw exception for invalid rank or suit"() {
        when:
        new Card(rank, suit)

        then:
        thrown(IllegalArgumentException)

        where:
        rank     | suit
        null     | Suit.CLUBS
        Rank.TWO | null
        null     | null
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

    def "card should support parsing from string representation"() {
        expect:
        def card = Card.parse(str)
        card.rank == rank
        card.suit == suit

        where:
        str     | rank       | suit
        'Ah'    | Rank.ACE   | Suit.HEARTS
        'Qs'    | Rank.QUEEN | Suit.SPADES
        '2c'    | Rank.TWO   | Suit.CLUBS
        '10d'   | Rank.TEN   | Suit.DIAMONDS
        '  Jc ' | Rank.JACK  | Suit.CLUBS
    }

    def "card parsing should throw exception for illegal strings"() {
        when:
        Card.parse(str)

        then:
        thrown(IllegalArgumentException)

        where:
        str << ['jC', '11h', 'toolong', '', null]
    }

    def "card should show text representation for toString"() {
        expect:
        new Card(rank, suit).toString() == str

        where:
        rank       | suit          | str
        Rank.ACE   | Suit.HEARTS   | 'Ah'
        Rank.QUEEN | Suit.SPADES   | 'Qs'
        Rank.TEN   | Suit.DIAMONDS | '10d'
    }

    def "cards should be comparable"() {
        expect:
        def loCard = Card.parse(low)
        def hiCard = Card.parse(high)
        loCard < hiCard
        hiCard > loCard
    
        where:
        low  | high
        "2c" | "3c"
        "Kd" | "Ad"
        "9h" | "10h"
        "2c" | "2d"
    }

    def "card should implement equals and hashCode"() {
        when:
        def left = new Card(rank, suit) 
        def right = new Card(rank, suit)
        
        then:
        left == right
        left.hashCode() == right.hashCode()

        where:
        rank  | suit
        KING  | HEARTS
        QUEEN | SPADES
    }

    def "isAdjacent should indicate whether two Ranks are adjacent"() {
        expect:
        TWO.isAdjacentTo(THREE)
        THREE.isAdjacentTo(FOUR)
        !TWO.isAdjacentTo(FOUR)
        KING.isAdjacentTo(ACE)
    }

    def "isAdjacent should indicate whether two Cards are adjacent"() {
        expect:
        Card.parse(left).isAdjacentTo(Card.parse(right)) == result

        where:
        left | right | result
        "2h" | "3h"  | true
        "2h" | "4h"  | false
        "2h" | "3d"  | true
    }

}
