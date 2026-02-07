import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 🌸 Blossom Tutte Simulator - Girly Edition
 * Enhanced version with pastel aesthetics and reusable components
 */
 class BlossomTutteSimulator extends JFrame {

    // 🎨 Pastel Color Palette
    static class PastelColors {
        static final Color BACKGROUND = new Color(255, 250, 250);
        static final Color SOFT_PINK = new Color(255, 209, 220);
        static final Color LAVENDER = new Color(230, 230, 250);
        static final Color MINT = new Color(189, 252, 201);
        static final Color PEACH = new Color(255, 229, 217);
        static final Color BABY_BLUE = new Color(221, 238, 255);
        static final Color ROSE = new Color(255, 183, 197);
        static final Color EDGE_COLOR = new Color(200, 200, 220);
        static final Color TEXT_COLOR = new Color(100, 100, 120);
    }

    public BlossomTutteSimulator() {
        setTitle("🌸 Blossom Tutte Embedding Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main graph panel
        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // Control panel
        ControlPanel controlPanel = new ControlPanel(graphPanel);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🎯 Reusable Graph Panel (can be used in CodeMafia too!)
    static class GraphPanel extends JPanel {
        private ArrayList<Vertex> vertices = new ArrayList<>();
        private ArrayList<Edge> edges = new ArrayList<>();
        private boolean isAnimating = true;
        private Timer animationTimer;
        private Vertex draggedVertex = null;

        public GraphPanel() {
            setPreferredSize(new Dimension(900, 700));
            setBackground(PastelColors.BACKGROUND);

            initializeGraph();
            setupAnimation();
            setupMouseListeners();
        }

        private void initializeGraph() {
            // Create 4x4 grid graph (16 vertices)
            int rows = 4, cols = 4;
            Vertex[][] grid = new Vertex[rows][cols];

            // Create vertices with random initial positions
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    boolean isFixed = (r == 0 && (c == 0 || c == cols-1)) ||
                            (r == rows-1 && c == cols/2);

                    double x = 150 + c * 600.0/(cols-1) + (Math.random()-0.5)*40;
                    double y = 100 + r * 500.0/(rows-1) + (Math.random()-0.5)*40;

                    grid[r][c] = new Vertex(x, y, isFixed);
                    vertices.add(grid[r][c]);
                }
            }

            // Create edges (grid connections)
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (r > 0) addEdge(grid[r][c], grid[r-1][c]);
                    if (c > 0) addEdge(grid[r][c], grid[r][c-1]);
                }
            }

            // Position fixed vertices on boundary
            grid[0][0].setPosition(150, 100);
            grid[0][cols-1].setPosition(750, 100);
            grid[rows-1][cols/2].setPosition(450, 600);
        }

        private void addEdge(Vertex v1, Vertex v2) {
            v1.addNeighbor(v2);
            v2.addNeighbor(v1);
            edges.add(new Edge(v1, v2));
        }

        private void setupAnimation() {
            animationTimer = new Timer(30, e -> {
                if (isAnimating) {
                    updateTutteEmbedding();
                    repaint();
                }
            });
            animationTimer.start();
        }

        private void setupMouseListeners() {
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    for (Vertex v : vertices) {
                        if (v.contains(e.getX(), e.getY())) {
                            draggedVertex = v;
                            draggedVertex.setFixed(true);
                            break;
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    draggedVertex = null;
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (draggedVertex != null) {
                        draggedVertex.setPosition(e.getX(), e.getY());
                        repaint();
                    }
                }
            };

            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        // 🌸 Tutte's Barycentric Embedding Algorithm
        private void updateTutteEmbedding() {
            // Calculate new positions (average of neighbors)
            for (Vertex v : vertices) {
                if (!v.isFixed()) {
                    v.calculateNewPosition();
                }
            }

            // Smooth update (prevents jittering)
            double smoothing = 0.15;
            for (Vertex v : vertices) {
                if (!v.isFixed()) {
                    v.applyNewPosition(smoothing);
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw title with cute font
            g2.setColor(PastelColors.TEXT_COLOR);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("🌸 Tutte Embedding Visualization", 20, 30);

            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawString("💕 Click and drag vertices to pin them", 20, 50);
            g2.drawString("🎀 Red flowers = fixed, Blue flowers = free", 20, 70);

            // Draw edges
            g2.setStroke(new BasicStroke(2));
            for (Edge edge : edges) {
                edge.draw(g2);
            }

            // Draw vertices
            for (Vertex v : vertices) {
                v.draw(g2);
            }
        }

        public void toggleAnimation() {
            isAnimating = !isAnimating;
        }

        public void resetGraph() {
            vertices.clear();
            edges.clear();
            initializeGraph();
            repaint();
        }

        public boolean isAnimating() {
            return isAnimating;
        }
    }

    // 🌺 Vertex class (reusable for CodeMafia player nodes!)
    static class Vertex {
        private double x, y;
        private double newX, newY;
        private boolean fixed;
        private ArrayList<Vertex> neighbors = new ArrayList<>();
        private static final int RADIUS = 12;

        public Vertex(double x, double y, boolean fixed) {
            this.x = x;
            this.y = y;
            this.newX = x;
            this.newY = y;
            this.fixed = fixed;
        }

        public void addNeighbor(Vertex neighbor) {
            neighbors.add(neighbor);
        }

        public void calculateNewPosition() {
            if (neighbors.isEmpty()) return;

            double sumX = 0, sumY = 0;
            for (Vertex n : neighbors) {
                sumX += n.x;
                sumY += n.y;
            }
            newX = sumX / neighbors.size();
            newY = sumY / neighbors.size();
        }

        public void applyNewPosition(double smoothing) {
            x = x * (1 - smoothing) + newX * smoothing;
            y = y * (1 - smoothing) + newY * smoothing;
        }

        public void setPosition(double x, double y) {
            this.x = x;
            this.y = y;
            this.newX = x;
            this.newY = y;
        }

        public void setFixed(boolean fixed) {
            this.fixed = fixed;
        }

        public boolean isFixed() {
            return fixed;
        }

        public boolean contains(int mouseX, int mouseY) {
            double dx = x - mouseX;
            double dy = y - mouseY;
            return dx*dx + dy*dy <= RADIUS*RADIUS*4;
        }

        public void draw(Graphics2D g2) {
            // Draw flower-like vertices with gradient
            int size = RADIUS * 2;

            if (fixed) {
                // Fixed vertices = Rose flowers 🌹
                GradientPaint gradient = new GradientPaint(
                        (float)x, (float)y, PastelColors.ROSE,
                        (float)x+size, (float)y+size, PastelColors.SOFT_PINK
                );
                g2.setPaint(gradient);
                g2.fillOval((int)x-RADIUS, (int)y-RADIUS, size, size);

                // Border
                g2.setColor(new Color(255, 100, 130));
                g2.setStroke(new BasicStroke(2));
                g2.drawOval((int)x-RADIUS, (int)y-RADIUS, size, size);
            } else {
                // Free vertices = Blue blossoms 🌸
                GradientPaint gradient = new GradientPaint(
                        (float)x, (float)y, PastelColors.BABY_BLUE,
                        (float)x+size, (float)y+size, PastelColors.LAVENDER
                );
                g2.setPaint(gradient);
                g2.fillOval((int)x-RADIUS+2, (int)y-RADIUS+2, size-4, size-4);

                // Border
                g2.setColor(new Color(150, 170, 255));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval((int)x-RADIUS+2, (int)y-RADIUS+2, size-4, size-4);
            }
        }

        public double getX() { return x; }
        public double getY() { return y; }
    }

    // 🌿 Edge class
    static class Edge {
        private Vertex v1, v2;

        public Edge(Vertex v1, Vertex v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public void draw(Graphics2D g2) {
            g2.setColor(PastelColors.EDGE_COLOR);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine((int)v1.getX(), (int)v1.getY(),
                    (int)v2.getX(), (int)v2.getY());
        }
    }

    // 🎀 Pretty Control Panel
    static class ControlPanel extends JPanel {
        private GraphPanel graphPanel;
        private JButton playPauseButton;

        public ControlPanel(GraphPanel graphPanel) {
            this.graphPanel = graphPanel;
            setBackground(PastelColors.PEACH);
            setPreferredSize(new Dimension(900, 80));
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

            createButtons();
        }

        private void createButtons() {
            // Play/Pause button
            playPauseButton = createPrettyButton("⏸️ Pause", PastelColors.LAVENDER);
            playPauseButton.addActionListener(e -> {
                graphPanel.toggleAnimation();
                playPauseButton.setText(graphPanel.isAnimating() ? "⏸️ Pause" : "▶️ Play");
            });
            add(playPauseButton);

            // Reset button
            JButton resetButton = createPrettyButton("🔄 Reset", PastelColors.MINT);
            resetButton.addActionListener(e -> graphPanel.resetGraph());
            add(resetButton);

            // Info label
            JLabel infoLabel = new JLabel("💫 Drag vertices to explore Tutte's algorithm!");
            infoLabel.setFont(new Font("Arial", Font.ITALIC, 13));
            infoLabel.setForeground(PastelColors.TEXT_COLOR);
            add(infoLabel);
        }

        private JButton createPrettyButton(String text, Color color) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(color);
            button.setForeground(PastelColors.TEXT_COLOR);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 2),
                    BorderFactory.createEmptyBorder(8, 20, 8, 20)
            ));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effect
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(color.brighter());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(color);
                }
            });

            return button;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlossomTutteSimulator());
    }
}
