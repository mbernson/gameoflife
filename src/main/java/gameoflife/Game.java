package gameoflife;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().start();
            }
        });
    }

    private int height = 5, width = 5;
    private Board board;

    public void start() {
        board = new Board(App.glider);
        setSize(800, 600);

        setLayout(new GridLayout(1, 2));
        setLocationRelativeTo(null);
        setTitle("Game of life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final Grid grid = new Grid();
        add(grid);

        JButton button = new JButton("Tick");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.tick();
                repaint();
                grid.repaint();
            }
        });
        add(button);

//        setResizable(false);
        setVisible(true);
    }

    class Grid extends JPanel {
        public final int CELL_WIDTH = 20,
                         CELL_HEIGHT = 20;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int y = 0; y < board.getHeight(); y++) {
                for(int x = 0; x < board.getWidth(); x++) {
                    int cellX = CELL_WIDTH + (x * CELL_WIDTH);
                    int cellY = CELL_HEIGHT + (y * CELL_HEIGHT);
                    int cell = board.getCell(x, y);
                    if(cell == 1)
                        g.setColor(Color.black);
                    else
                        g.setColor(Color.white);
                    g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);
                }
            }
            g.setColor(Color.gray);
            g.drawRect(CELL_WIDTH, CELL_HEIGHT, CELL_WIDTH * board.getWidth(), CELL_HEIGHT * board.getHeight());

            for (int i = CELL_WIDTH; i <= board.getWidth() * CELL_WIDTH; i += CELL_WIDTH) {
                g.drawLine(CELL_WIDTH, i, board.getWidth() * CELL_WIDTH + CELL_WIDTH, i);
            }

            for (int i = CELL_HEIGHT; i <= board.getHeight() * CELL_HEIGHT; i += CELL_HEIGHT) {
                g.drawLine(i, CELL_HEIGHT, i, board.getHeight() * CELL_HEIGHT + CELL_HEIGHT);
            }
        }
    }
}
