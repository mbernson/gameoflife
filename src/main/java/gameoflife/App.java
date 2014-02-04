package gameoflife;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Conway's game of life.");

        Board board = new Board(Patterns.glider);

        System.out.printf("Board height: %d, width: %d\n", board.getHeight(), board.getWidth());
        board.print();

        while(! sc.nextLine().startsWith("q")) {
            board.tick();
            board.print();
        }
    }
}
