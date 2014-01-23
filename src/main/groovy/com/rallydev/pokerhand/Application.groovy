package com.rallydev.pokerhand

class Application {
    public static void main(String[] args) {
        println "Enter a hand, each card formatted [RANK][suit]"
        println "e.g Js = Jack of Spades, 10d = 10 of Diamonds"
        println "Type an empty line to quit."
        String line
        while (line = System.console().readLine()) { 
            try {
                if (line.empty) { 
                    break 
                }
                println(Hand.parse(line).evaluate())
            } catch (IllegalArgumentException e) {
                println "${e.class.name}: ${e.message}"
            }
        }
        println "Goodbye!"
    }
}
