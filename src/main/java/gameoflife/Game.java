package gameoflife;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Game extends JFrame implements ActionListener {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }

    public final int CELL_WIDTH = 20,
                     CELL_HEIGHT = 20;

    private Board board;
    private Grid grid;
    private JLabel generationsLabel = new JLabel("Generation: 0");
    private Timer timer;

    public Game() {
        timer = new Timer(750, this);
        board = new Board(Patterns.glider);

        setLocationRelativeTo(null);
        setTitle("Game of life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        grid = new Grid();
        add(grid);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));
        controls.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton startStopButton = new JButton("Start/stop");
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning())
                    timer.stop();
                else
                    timer.start();
            }
        });
        JButton tickButton = new JButton("Tick");
        tickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { tick(); }
        });
        controls.add(startStopButton);
        controls.add(tickButton);
        controls.add(generationsLabel);

        add(controls);

        setResizable(true);
        setVisible(true);
        Dimension d = grid.getPreferredSize();
        d.height += 100;
        d.width += CELL_WIDTH * 2;
        setSize(d);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed");
        tick();
    }

    private void tick() {
        if(grid != null) {
            board.tick();
            generationsLabel.setText("Generation: " + board.getGeneration());
            repaint();
            grid.repaint();
        }
    }

    class Grid extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            Dimension d = new Dimension();
            d.setSize(CELL_WIDTH * board.getWidth() + 1, CELL_HEIGHT * board.getHeight() + 1);
            return d;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int y = 0; y < board.getHeight(); y++) {
                for(int x = 0; x < board.getWidth(); x++) {
                    int cellX = CELL_WIDTH + (x * CELL_WIDTH),
                        cellY = CELL_HEIGHT + (y * CELL_HEIGHT);
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

            // Vertical lines
            for (int i = CELL_WIDTH; i <= board.getWidth() * CELL_WIDTH; i += CELL_WIDTH) {
                g.drawLine(i, CELL_HEIGHT, i, board.getHeight() * CELL_HEIGHT + CELL_HEIGHT );
            }

            // Horizontal lines
            for (int i = CELL_HEIGHT; i <= board.getHeight() * CELL_HEIGHT; i += CELL_HEIGHT) {
                g.drawLine(CELL_WIDTH, i, board.getWidth() * CELL_WIDTH + CELL_WIDTH, i);
            }
        }
    }
}
