import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

 class TutteSimulation extends JPanel {
    class Point {
        double x, y, newX, newY;
        boolean fixed;
        ArrayList<Point> neighbors = new ArrayList<>();

        Point(double x, double y, boolean fixed) {
            this.x = x; this.y = y;
            this.newX = x; this.newY = y;
            this.fixed = fixed;
        }
    }

    private ArrayList<Point> points = new ArrayList<>();
    private boolean running = true;
    private Timer timer;

    public TutteSimulation() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        // Create a grid graph
        int rows = 4, cols = 4;
        Point[][] grid = new Point[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                boolean isFixed = (r == 0 && (c == 0 || c == cols-1)) || // marking three fixed vertices right, left and bottom middle
                        (r == rows-1 && c == cols/2);
                grid[r][c] = new Point(
                        100 + c * 600/(cols-1) + Math.random()*30,
                        100 + r * 400/(rows-1) + Math.random()*30,
                        isFixed
                );
                points.add(grid[r][c]);
            }
        }

        // Connect grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r > 0) grid[r][c].neighbors.add(grid[r-1][c]);
                if (c > 0) grid[r][c].neighbors.add(grid[r][c-1]);
                if (r < rows-1) grid[r][c].neighbors.add(grid[r+1][c]);
                if (c < cols-1) grid[r][c].neighbors.add(grid[r][c+1]);
                //System.out.println("Point (" + r + "," + c + ") has " +
                        //grid[r][c].neighbors.size() + " neighbors");// to see how many neighbours each point has
            }

        }

        // Position fixed points
        grid[0][0].x = 100; grid[0][0].y = 100;
        grid[0][cols-1].x = 700; grid[0][cols-1].y = 100;
        grid[rows-1][cols/2].x = 400; grid[rows-1][cols/2].y = 500;

        // Animation timer
        timer = new Timer(25, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (running) {
                    updatePositions();
                    repaint();
                }
            }
        });
        timer.start();

        // Mouse listener to drag points
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                for (Point p : points) {
                    double dx = p.x - e.getX();
                    double dy = p.y - e.getY();
                    if (dx*dx + dy*dy < 100) { // Clicked near point
                        p.fixed = true;
                        p.x = e.getX();
                        p.y = e.getY();
                        repaint();
                        break;
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                for (Point p : points) {
                    if (p.fixed) {
                        double dx = p.x - e.getX();
                        double dy = p.y - e.getY();
                        if (dx*dx + dy*dy < 100) {
                            p.x = e.getX();
                            p.y = e.getY();
                            repaint();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void updatePositions() {
        // Calculate new positions
        for (Point p : points) {
            if (!p.fixed && p.neighbors.size() > 0) {
                double sumX = 0, sumY = 0;
                for (Point n : p.neighbors) {
                    sumX += n.x;
                    sumY += n.y;
                }
                p.newX = sumX / p.neighbors.size();
                p.newY = sumY / p.neighbors.size();
            }
        }

        // Update with smoothing
        double blend = 0.1;
        for (Point p : points) {
            if (!p.fixed) {
                p.x = p.x * (1-blend) + p.newX * blend;
                p.y = p.y * (1-blend) + p.newY * blend;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw edges
        g2.setColor(Color.LIGHT_GRAY);
        for (Point p : points) {
            for (Point n : p.neighbors) {
                g2.drawLine((int)p.x, (int)p.y, (int)n.x, (int)n.y);
            }
        }

        // Draw vertices
        for (Point p : points) {
            if (p.fixed) {
                g2.setColor(Color.RED);
                g2.fillOval((int)p.x-6, (int)p.y-6, 12, 12);
            } else {
                g2.setColor(Color.BLUE);
                g2.fillOval((int)p.x-4, (int)p.y-4, 8, 8);
            }
        }

        // Draw text
        g2.setColor(Color.BLACK);
        g2.drawString("Tutte Embedding - Click to pin vertices, drag to move", 20, 20);
        g2.drawString("Red = fixed, Blue = free", 20, 40);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tutte Embedding Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TutteSimulation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}