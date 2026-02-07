Tutte Embedding Simulation
https://img.shields.io/badge/Java-17+-blue.svg
https://img.shields.io/badge/GUI-Swing-orange.svg
https://img.shields.io/badge/License-MIT-green.svg

An interactive visualization of Tutte's Theorem - watch planar graphs untangle themselves in real-time!

https://demo.gif ()

🎯 What is Tutte's Embedding?
Tutte's Theorem (1963) states that if you take a 3-connected planar graph, fix its outer face as a convex polygon, and place each interior vertex at the average (barycenter) of its neighbors, you get a perfect planar straight-line drawing.

This simulation makes that mathematical theorem visually intuitive!

✨ Features
Real-time visualization of graph untangling

Interactive controls: Click to pin vertices, drag to reposition

Multiple graph types: Grid graphs, customizable layouts

Step-by-step animation with adjustable speed

Beautiful GUI with smooth animations

Educational tool for graph theory concepts


🚀 Getting Started
Prerequisites
Java JDK 8 or higher

Git (for cloning)

Installation & Running
bash
# 1. Clone the repository
git clone https://github.com/ishekaa12/TutteSimulation.git
cd TutteSimulation

# 2. Compile (if needed)
javac TutteSimulation.java

# 3. Run the simulation
java TutteSimulation
Or just double-click the .jar file if available!

🎮 How to Use
Watch the magic: The graph automatically untangles itself

Interact:

Click on any blue vertex to pin it (red)

Drag red vertices to reshape the embedding

Spacebar to pause/resume animation

R key to reset to random positions

Experiment with different graph structures by modifying the code

🧠 How It Works - The Algorithm
Mathematical Core
For each interior vertex v at position (x, y):

text
x_new = average(x_coordinates of all neighbors)
y_new = average(y_coordinates of all neighbors)
Code Implementation
java
// The heart of Tutte's algorithm
for (Point p : points) {
    if (!p.fixed) {
        double sumX = 0, sumY = 0;
        for (Point neighbor : p.neighbors) {
            sumX += neighbor.x;
            sumY += neighbor.y;
        }
        p.newX = sumX / p.neighbors.size();  // Barycenter
        p.newY = sumY / p.neighbors.size();  // calculation
    }
}
Key Components
Fixed Boundary: 3 vertices form a triangle (convex polygon)

Barycentric Coordinates: Each vertex = convex combination of neighbors

Iterative Solution: Jacobi method for solving linear equations

Planar Guarantee: Tutte's theorem ensures no edge crossings

📁 Project Structure
text
TutteSimulation/
├── src/
│   └── TutteSimulation.java    # Main simulation class
├── screenshots/                # Demo images
├── README.md                   # This file
└── .gitignore
Main Classes
TutteSimulation: Main JPanel with rendering and animation

Point: Inner class representing graph vertices

GUI Components: Swing-based interactive interface

🔧 Customization
Change Graph Size
java
// In TutteSimulation constructor
int rows = 6, cols = 6;  // 36 vertices instead of 16
Adjust Animation Speed
java
// In updatePositions() method
double blend = 0.2;  // Faster movement (was 0.1)
Different Boundary Shapes
java
// Try a square boundary instead of triangle
grid[0][0].fixed = true;        // Top-left
grid[0][cols-1].fixed = true;   // Top-right
grid[rows-1][0].fixed = true;   // Bottom-left
grid[rows-1][cols-1].fixed = true; // Bottom-right
 Learning Resources
Concepts Illustrated
Tutte's Theorem (1963)

Barycentric Coordinates

Planar Graph Drawing

Laplacian Smoothing

Linear Systems (iterative solutions)

Graph 3-Connectivity

Real-World Applications
Network Visualization (untangling complex graphs)

VLSI Chip Design (component placement without crossings)

Mesh Generation (3D computer graphics)

Cartography (map drawing algorithms)

 Try These Experiments
Add more fixed points: Click vertices to create different boundary shapes

Change neighbor connections: Modify the grid connection logic

Visualize convergence: Add energy calculation to see when graph stabilizes

Different initial layouts: Start with circular or random arrangements

🤝 Contributing
Found a bug? Have a cool feature idea? Contributions welcome!

Fork the repository

Create a feature branch (git checkout -b feature/amazing-idea)

Commit changes (git commit -m 'Add amazing idea')

Push to branch (git push origin feature/amazing-idea)

Open a Pull Request



 Educational Value
Perfect for:

Computer Science students learning graph theory

Mathematics students exploring planar embeddings

Educators teaching algorithmic visualization

Researchers prototyping graph drawing algorithms

 Acknowledgments
William T. Tutte for the foundational theorem (1963)

Java Swing team for the GUI framework

Graph Drawing community for continuous inspiration

Made with ❤️ by [Isheka]
"Watch mathematics come to life, one vertex at a time!"
