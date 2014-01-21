package com.rallydev.pokerhand

import spock.lang.Specification
import spock.lang.Unroll

import static com.rallydev.pokerhand.Rank.*
import static com.rallydev.pokerhand.Suit.*

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

}
