# Tutte Embedding Simulation

![Java](https://img.shields.io/badge/Java-17+-blue.svg)
![GUI](https://img.shields.io/badge/GUI-Swing-orange.svg)

An interactive visualization of Tutte's Theorem - watch planar graphs untangle themselves in real-time!

## 🎯 What is Tutte's Embedding?

Tutte's Theorem (1963) states that if you take a 3-connected planar graph, fix its outer face as a convex polygon, and place each interior vertex at the average (barycenter) of its neighbors, you get a perfect planar straight-line drawing.

**This simulation makes that mathematical theorem visually intuitive!**

## ✨ Features

- **Real-time visualization** of graph untangling
- **Interactive controls**: Click to pin vertices, drag to reposition
- **Multiple graph types**: Grid graphs with customizable layouts
- **Step-by-step animation** with adjustable speed
- **Smooth animations** with a clean GUI
- **Educational tool** for understanding graph theory concepts

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or higher
- Git (for cloning)

### Installation & Running

```bash
# Clone the repository
git clone https://github.com/ishekaa12/TutteSimulation.git
cd TutteSimulation

# Compile
javac TutteSimulation.java

# Run the simulation
java TutteSimulation
```

## 🎮 How to Use

1. **Watch the magic**: The graph automatically untangles itself
2. **Interact with vertices**:
   - Click on any blue vertex to pin it (turns red)
   - Drag red vertices to reshape the embedding
3. **Keyboard controls**:
   - `Spacebar` - Pause/resume animation
   - `R` key - Reset to random positions
4. **Experiment**: Modify the code to try different graph structures

## 🧠 How It Works

### The Algorithm

For each interior vertex v at position (x, y):

```
x_new = average(x_coordinates of all neighbors)
y_new = average(y_coordinates of all neighbors)
```

### Code Implementation

```java
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
```

### Key Components

- **Fixed Boundary**: 3 vertices form a triangle (convex polygon)
- **Barycentric Coordinates**: Each vertex is a convex combination of its neighbors
- **Iterative Solution**: Uses Jacobi method for solving linear equations
- **Planar Guarantee**: Tutte's theorem ensures no edge crossings

## 📁 Project Structure

```
TutteSimulation/
├── src/
│   └── TutteSimulation.java    # Main simulation class
└── README.md                   # This file
```

### Main Classes

- **TutteSimulation**: Main JPanel with rendering and animation logic
- **Point**: Inner class representing graph vertices
- **GUI Components**: Swing-based interactive interface

## 🔧 Customization Ideas

### Change Graph Size

```java
// In TutteSimulation constructor
int rows = 6, cols = 6;  // Creates a 6x6 grid (36 vertices instead of 16)
```

### Adjust Animation Speed

```java
// In updatePositions() method
double blend = 0.2;  // Faster movement (default is 0.1)
```

### Try Different Boundary Shapes

```java
// Create a square boundary instead of triangle
grid[0][0].fixed = true;              // Top-left
grid[0][cols-1].fixed = true;         // Top-right
grid[rows-1][0].fixed = true;         // Bottom-left
grid[rows-1][cols-1].fixed = true;    // Bottom-right
```

## 📚 What You'll Learn

### Concepts Illustrated

- Tutte's Theorem (1963)
- Barycentric Coordinates
- Planar Graph Drawing
- Laplacian Smoothing
- Linear Systems (iterative solutions)
- Graph 3-Connectivity

### Real-World Applications

- **Network Visualization**: Untangling complex graphs
- **VLSI Chip Design**: Component placement without crossings
- **Mesh Generation**: 3D computer graphics
- **Cartography**: Map drawing algorithms

## 🧪 Try These Experiments

1. **Add more fixed points**: Click multiple vertices to create different boundary shapes
2. **Change neighbor connections**: Modify the grid connection logic for different graph types
3. **Visualize convergence**: Add energy calculation to see when the graph stabilizes
4. **Different initial layouts**: Start with circular, spiral, or clustered arrangements

## 🤝 Contributing

Contributions are welcome! Here's how:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-idea`)
3. Commit your changes (`git commit -m 'Add amazing idea'`)
4. Push to the branch (`git push origin feature/amazing-idea`)
5. Open a Pull Request

## 👨‍🏫 Perfect For

- **Computer Science students** learning graph theory
- **Mathematics students** exploring planar embeddings
- **Educators** teaching algorithmic visualization
- **Researchers** prototyping graph drawing algorithms

## 🙏 Acknowledgments

- William T. Tutte for the foundational theorem (1963)
- Java Swing team for the GUI framework
- Graph Drawing community for continuous inspiration

---

**"Watch mathematics come to life, one vertex at a time!"**

Made by [ishekaa12](https://github.com/ishekaa12)
