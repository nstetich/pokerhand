This code evaluates poker hands and chooses the highest ranking available
for the hand, also showing the cards that were part of that outcome.

This code is built with [Gradle](http://www.gradle.org/) using the 
Groovy plugin and the Application plugin. The gradle wrapper `gradlew` 
is included with the source code. The wrapper will download gradle 
and install it within the project folder. To build the project and 
install an executable in the build directory, run `./gradlew installApp`. 
This will install an executable script (shell script or batch script) 
inside ./build/install/pokerhand/bin. To run the unit tests for the 
project, run `./gradlew test`. An HTML unit test report may be viewed 
at ./build/reports/test/index.html.

The `pokerhand` executable is designed be run in interactive mode, and 
accepts hands entered one hand per line via stdin. A hand is always a 
set of five cards, and is represented as a sequence of two to three 
character "words" each signifying a card, with the cards separated by 
whitespace characters. Each card is represented as rank (a number or 
capital letter) and a suit (a lowercase letter). For example:
- 2h = Two of Hearts
- 10s = Ten of Spades
- Jc = Jack of Clubs
- Ad = Ace of Diamonds

This code makes some assumptions about poker hands, most importantly 
the following:
- A hand is always a set of five cards
- The hand is drawn from a single deck, and may not contain more than 
  one card of the same rank and suit
- There are no wildcards: 
  - There are no Jokers
  - Cards with a normal rank and suit may not be assigned as wildcards

Support for wildcards, multiple decks, and larger hands could be features 
to add in the future.

Information about poker hands and rankings is drawn from the [List of 
Poker Hands](http://en.wikipedia.org/wiki/List_of_poker_hands) Wikipedia
article.
