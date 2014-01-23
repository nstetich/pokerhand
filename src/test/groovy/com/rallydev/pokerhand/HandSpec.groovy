package com.rallydev.pokerhand

import spock.lang.Specification
import spock.lang.Unroll

import static com.rallydev.pokerhand.Rank.*
import static com.rallydev.pokerhand.Suit.*
import static com.rallydev.pokerhand.Outcome.*

class HandSpec extends Specification {
    def "hand constructor should throw exception if there are not exactly five cards"() {
        when:
        List<Card> cardsList = cards.split(' ').collect { Card.parse(it) }
        new Hand(cardsList)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "A hand must have exactly five cards."

        where:
        cards << [
            '4d',
            '4d 6c',
            '4d 6c 10s',
            '4d 6c 10s Qh',
            '4d 6c 10s Qh Qs Jd'
        ]
    }

   def "a hand can be parsed from an input string"() {
        expect:
        Hand.parse(cardsString).cards == cards

        where:
        cardsString        | cards
        '4d 6c 10s Qh 3d'  | [ new Card(FOUR, DIAMONDS), 
                               new Card(SIX, CLUBS),
                               new Card(TEN, SPADES),
                               new Card(QUEEN, HEARTS),
                               new Card(THREE, DIAMONDS) ]
    }

    def "hand parsing should throw an exception for invalid hand"() {
        when:
        Hand.parse(str)

        then:
        thrown(IllegalArgumentException)

        where:
        str << [
            null,
            // Too few cards
            '',
            '4d', 
            '4d 6c',
            '4d 6c 10s',
            '4d 6c 10s Qh',
            // Too many cards
            '4d 6c 10s Qh Qs 3d',
            // Invalid card
            '11d 6c 10s Qh Qs'
        ]
    }

    def "hand should not allow duplicate cards"() {
        when:
        Hand.parse("4d 6c 10s 6c Qh")

        then:
        thrown(IllegalArgumentException)
    }

    def "sortedCards field should contain sorted cards"() {
        when:
        def sorted = Hand.parse(str).sortedCards
        
        then:
        for (int n = 0; n < sorted.size(); n++) {
            if (n > 0) {
                assert sorted[n - 1] < sorted[n]
            }
        }

        where:
        str << [ 'Qs 4d 10s Kh 4c' ]
    }

    def "cardsByRank should hold cards partitioned by rank"() {
        expect:
        Hand.parse(str).cardsByRank == 
            cardsByRank.collectEntries { k, v ->
                [k, v.split().collect { Card.parse(it) }]
            }

        where:
        str              | cardsByRank
        '2d 3d 4d 5d 6d' | [ (TWO): '2d', 
                             (THREE): '3d',
                             (FOUR): '4d',
                             (FIVE): '5d',
                             (SIX): '6d' ]
        '4c 4d 4s 4h 8d' | [ (FOUR): '4c 4d 4s 4h',
                             (EIGHT): '8d' ]
    }

    def "cardsBySuit should hold cards partitioned by suit"() {
        expect:
        Hand.parse(str).cardsBySuit == 
            cardsBySuit.collectEntries { k, v ->
                [k, v.split().collect { Card.parse(it) }]
            }

        where:
        str               | cardsBySuit
        '2c 2d 2s 2h Qc'  | [ (CLUBS): '2c Qc',
                              (DIAMONDS): '2d',
                              (SPADES): '2s',
                              (HEARTS): '2h' ]
        '10h Jh Qh Kh Ah' | [ (HEARTS): '10h Jh Qh Kh Ah' ]
    }

    def "isStraight should reflect whether a hand has a straight"() {
        expect:
        Hand.parse(str).isStraight() == isStraight

        where:
        str               | isStraight
        '4h 10h 6c Js Qh' | false
        '2c 3d 4h 5s 6c'  | true
        '4h 6c 5s 2c 3d'  | true // Independent of order in hand
        'Ac 2h 3s 4d 5h'  | true // Ace may be low card
        'Kh Ah 2c 3d 4s'  | false // Straight may not "wrap around"
        'Ac 2h 3s 4d As'  | false
    }

    def "isFlush should reflect whether a hand is a flush"() {
        expect:
        Hand.parse(str).isFlush() == isFlush

        where:
        str               | isFlush
        "2h 10h 7h 8h 4h" | true
        "2h 10h 7h 8h 4d" | false
        "2d 2c 2s 2h 10h" | false
        "2d 10d 7d 8d 4d" | true
    }

    def "evaluate should always return the highest matching rank"() {
        expect:
        def evaluation = Hand.parse(hand).evaluate()
        evaluation != null
        evaluation.outcome == outcome
        evaluation.cards as Set == 
            cards.split().collect{Card.parse(it)} as Set
        
        where:
        hand              | outcome        | cards
        "10h Jh Qh Kh Ah" | ROYAL_FLUSH    | "10h Jh Qh Kh Ah"
        "9s 10s Js Qs Ks" | STRAIGHT_FLUSH | "9s 10s Js Qs Ks"
        "5h 5d 6h 5s 5c"  | FOUR_OF_A_KIND | "5h 5d 5s 5c"
        "3h 3s 3d 2h 2s"  | FULL_HOUSE     | "3h 3s 3d 2h 2s"
        "2h 5h 4h 6h Kh"  | FLUSH          | "2h 4h 5h 6h Kh"
    }

}
