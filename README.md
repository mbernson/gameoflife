# Game of Life

This is a simple implementation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life) in Java, made for a school exercise.

## Running and testing

To run the game (make sure you have Java 1.7 configured with maven for this):

`mvn exec:java`

To run the tests:

`mvn test`

To spit out a Mac app, run:

`package osxappbundle:bundle -DmainClass=gameoflife.Game`

If you don't like maven, just import the project into whatever IDE/editor you're using and run the `Game` class. Run the `CLI` class for a text based game of life.

## License

GPLv3