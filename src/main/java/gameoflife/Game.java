package gameoflife;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        setupFrame();

        grid = new Grid();
        add(grid);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addControls();

        setVisible(true);
        Dimension d = grid.getPreferredSize();
        d.height += 100;
        d.width += CELL_WIDTH * 2;
        setSize(d);
    }

    private void setupFrame() {
        setLocationRelativeTo(null);
        setTitle("Game of life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

    }

    private void addControls() {
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
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.clear();
                grid.repaint();
                updateGenerationsLabel();
            }
        });
        controls.add(startStopButton);
        controls.add(tickButton);
        controls.add(clearButton);
        controls.add(generationsLabel);

        add(controls);
    }

    public void actionPerformed(ActionEvent e) {
        tick();
    }

    private void tick() {
        if(grid != null) {
            board.tick();
            updateGenerationsLabel();
            grid.repaint();
            repaint();
        }
    }

    private void updateGenerationsLabel() {
        generationsLabel.setText("Generation: " + board.getGeneration());
    }

    class Grid extends JPanel {
        public Grid() {
            super();
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX() / CELL_WIDTH,
                        y = e.getY() / CELL_HEIGHT;
                    board.toggleCell(x - 1, y - 1);
                    repaint();
                }

                // All these methods have to be implemented.
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            });
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = new Dimension();
            d.setSize(CELL_WIDTH * board.getWidth() + 1, CELL_HEIGHT * board.getHeight() + 1);
            return d;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the cell squares
            for(int y = 0; y < board.getHeight(); y++) {
                for(int x = 0; x < board.getWidth(); x++) {
                    int cellX = CELL_WIDTH + (x * CELL_WIDTH),
                        cellY = CELL_HEIGHT + (y * CELL_HEIGHT);
                    int cell = board.getCell(x, y);

                    g.setColor(cell == 1 ? Color.black : Color.white);

                    g.fillRect(cellX, cellY, CELL_WIDTH, CELL_HEIGHT);
                }
            }
            g.setColor(Color.gray);
            g.drawRect(CELL_WIDTH, CELL_HEIGHT, CELL_WIDTH * board.getWidth(), CELL_HEIGHT * board.getHeight());

            // Draw vertical gridlines
            for (int i = CELL_WIDTH; i <= board.getWidth() * CELL_WIDTH; i += CELL_WIDTH) {
                g.drawLine(i, CELL_HEIGHT, i, board.getHeight() * CELL_HEIGHT + CELL_HEIGHT );
            }

            // Draw horizontal gridlines
            for (int i = CELL_HEIGHT; i <= board.getHeight() * CELL_HEIGHT; i += CELL_HEIGHT) {
                g.drawLine(CELL_WIDTH, i, board.getWidth() * CELL_WIDTH + CELL_WIDTH, i);
            }
        }
    }
}
