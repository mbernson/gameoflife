package gameoflife;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest {

    private final int[][] blinker = {
            { 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
    };

    @Test
    public void testGetNeighbors() {
        Board b = new Board(blinker);
        assertArrayEquals(b.getNeighbours(0, 0), new int[]{
                -1, -1, 0, 0, 0, -1, -1, -1
        });
        assertArrayEquals(b.getNeighbours(2, 2), new int[]{
                1, 0, 0, 0, 1, 0, 0, 0
        });
        assertArrayEquals(b.getNeighbours(2, 1), new int[]{
                0, 0, 0, 0, 1, 0, 0, 0
        });
        assertArrayEquals(b.getNeighbours(4, 5), new int[]{
                0, -1, -1, -1, -1, -1, 0, 0
        });
        assertArrayEquals(b.getNeighbours(-1, -1), new int[]{
                -1, -1, -1, 0, -1, -1, -1, -1
        });
        assertArrayEquals(b.getNeighbours(2, 5), new int[]{
                0, 0, 0, -1, -1, -1, 0, 0
        });
    }

    @Test
    public void testGetCell() {
        Board b = new Board(blinker);
        assertEquals(b.getCell(0, 0), 0);
        assertEquals(b.getCell(1, 1), 0);
        assertEquals(b.getCell(2, 1), 1);
        assertEquals(b.getCell(2, 2), 1);
        assertEquals(b.getCell(2, 3), 1);
        assertEquals(b.getCell(3, 3), 0);
        assertEquals(b.getCell(4, 5), 0);

        assertEquals(b.getCell(-1, -1), -1);
        assertEquals(b.getCell(9000, 9000), -1);
    }

    @Test
    public void testBoardDimensions() {
        Board b = new Board(4, 5);
        assertEquals(b.getHeight(), 5);
        assertEquals(b.getWidth(), 4);
    }

    @Test
    public void testBoardDimensionsWithPattern() {
        Board board = new Board(blinker);

        assertEquals(board.getHeight(), 6);
        assertEquals(board.getWidth(), 5);
    }
}
