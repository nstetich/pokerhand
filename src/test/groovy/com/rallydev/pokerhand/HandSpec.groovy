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

    def "sortedCards field should contain reverse-sorted cards"() {
        when:
        def sorted = Hand.parse(str).sortedCards
        
        then:
        for (int n = 0; n < sorted.size(); n++) {
            if (n > 0) {
                assert sorted[n - 1] > sorted[n]
            }
        }

        where:
        str << [ 'Qs 4d 10s Kh 4c' ]
    }

    def "high card outcome should be highest card in the hand"() {
        expect:
        outcome.call(Hand.parse(cards)) as Set == 
            outcomeCards?.collect { Card.parse(it) } as Set
        
        where:
        cards            | outcome   | outcomeCards
        "2c Qs Kh Ad 3s" | HIGH_CARD | [ "Ad" ]
        "4h 5h 6h 7h 8h" | HIGH_CARD | [ "8h" ]
    }

    def "pair outcome should be highest pair in the hand"() {
        expect:
        outcome.call(Hand.parse(cards)) as Set == 
            outcomeCards.collect { Card.parse(it) } as Set
            outcomeCards?.collect { Card.parse(it) } as Set

        where:
        cards              | outcome | outcomeCards
        "2c Js 3d Jc Qh"   | PAIR    | [ 'Js', 'Jc' ] // find the pair
        "3d 4c 10s 10c 4h" | PAIR    | [ '10s', '10c' ] // select highest pair
        "3d 7s 3h 7c 7h"   | PAIR    | [ '7h', '7s' ] // treat three-of-a-kind as a pair
        "2h 3h 4h 5h 6h"   | PAIR    | null // no pairs in hand

    }

    }

}
