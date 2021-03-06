package com.rallydev.pokerhand

class Application {
    public static void main(String[] args) {
        if (System.console()) {
            println "Enter a hand, each card formatted [RANK][suit]"
            println "e.g Js = Jack of Spades, 10d = 10 of Diamonds"
            println "Type an empty line to quit."
        }
        String line
        def input = System.console() ?: System.in.newReader()
        while (line = input.readLine()) { 
            try {
                if (line.empty) { 
                    break 
                }
                println(Hand.parse(line).evaluate())
            } catch (IllegalArgumentException e) {
                println "${e.class.name}: ${e.message}"
            }
        } 
        if (System.console()) {
            println "Goodbye!"
        }
    }
}
