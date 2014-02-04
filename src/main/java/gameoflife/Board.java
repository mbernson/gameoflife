package gameoflife;

public class Board {
    private int[][] board;
    private int width, height;

    private int generation = 0;

    public Board(int width, int height) {
        board = new int[height][width];
        this.width = width;
        this.height = height;
    }

    public Board(int[][] pattern) {
        board = pattern;
        height = board.length;
        width = board[0].length; // Assume every row has the same width
    }

    public void tick() {
        generation++;
        // Copy the new board
        int[][] futureBoard = new int[getHeight()][getWidth()];

        for(int row = 0; row < getHeight(); row++) {
            for(int col = 0; col < getWidth(); col++) {
                // Check if the cell should live or die in the next generation
                int newState = checkCell(col, row);
                futureBoard[row][col] = newState;
            }
        }

        board = futureBoard;
    }

    private int checkCell(int x, int y) {
        final int cell = getCell(x, y);
        final int[] neighbors = getNeighbors(x, y);

        int aliveNeighbors = 0, deadNeighbors = 0;
        for(int n : neighbors) {
            if(n == 0) deadNeighbors++;
            else if(n == 1) aliveNeighbors++;
        }

        // Any live cell with fewer than two live neighbours dies, as if caused by under-population.
        if(cell == 1 && aliveNeighbors < 2) return 0;
        // Any live cell with two or three live neighbours lives on to the next generation.
        else if(cell == 1 && (aliveNeighbors == 2 || aliveNeighbors == 3)) return 1;
        // Any live cell with more than three live neighbours dies, as if by overcrowding.
        else if(cell == 1 && aliveNeighbors > 3) return 0;
        // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
        else if(cell == 0 && aliveNeighbors == 3) return 1;

        else return 0;
    }

    public int[] getNeighbors(int x, int y) {
        // The cell's neighbors are sorted clockwise around the cell,
        // starting at the top
        return new int[] {
            getCell(x, y-1), // Top
            getCell(x+1, y-1), // Top-right
            getCell(x+1, y), // Right
            getCell(x+1, y+1), // Bottom-right
            getCell(x, y+1), // Bottom
            getCell(x-1, y+1), // Bottom-left
            getCell(x-1, y), // Left
            getCell(x-1, y-1), // Top-left
        };
    }

    public void print() {
        for(int[] row : board) {
            for(int cell : row) {
                System.out.printf("%d ", cell);
            }
            System.out.print("\n");
        }
        System.out.printf("Generation: %d", generation);
    }

    public int getGeneration() { return generation; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int[][] getBoard() { return board; }
    public int[] getCells() {
        int[] cells = new int[width * height];
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                cells[row + col] = board[row][col];
            }
        }
        return cells;
    }

    public int getCell(int x, int y) {
        if(y >= getHeight() || y < 0)
            return -1;
        else if(x >= getWidth() || x < 0)
            return -1;

        return board[y][x];
    }

    public boolean setCell(int x, int y, int state) {
        if(getCell(x, y) == -1)
            return false;

        board[y][x] = state;
        return true;
    }
}