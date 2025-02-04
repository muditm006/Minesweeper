# Minesweeper Game

A custom implementation of the classic **Minesweeper game**, built using Java with the **MVC (Model-View-Controller)** framework. This project features a custom-designed board, a status panel, and robust game logic, including an optimized flood-fill algorithm for uncovering tiles.

---

## ✨ Features

- 🎨 **Custom Board Design**  
  Built using Java's JSwing library, the game includes a visually appealing board and status panel to display the current game state.

- 🏗️ **MVC Architecture**  
  The project is segmented into three distinct areas:  
  - **Model**: Manages game logic and data (e.g., tile states, mine placement).  
  - **View**: Displays the game board and status panel.  
  - **Controller**: Handles user interactions like saving, loading, resetting, and uncovering tiles.

- ⚙️ **Optimized Flood-Fill Algorithm**  
  A refined `revealTiles` method ensures adjacent tiles are properly uncovered when an empty tile is clicked, avoiding infinite recursion.

- 💾 **Save and Load Game State**  
  Utilizes Java's File I/O library to save the current state of the game board to a file and load it back later. This allows players to pause and resume their games seamlessly.

---

## 🛠️ Technical Challenges

- **Flood-Fill Algorithm Debugging**  
  Initially, the `revealTiles` method caused every safe space on the board to be uncovered due to a missing base case in the recursion logic. After debugging and adding a condition to terminate recursion for already revealed tiles, the function worked as expected.

- **File I/O Implementation**  
  Careful serialization of the game board state was required to ensure all relevant data (e.g., mine locations, revealed tiles, flags) could be accurately saved and restored.

---

## 🗂️ File Descriptions

- **`Game.java`**  
  Implements the core game logic, including win/loss conditions and interactions with other components.

- **`GameBoard.java`**  
  Represents the game board as part of the View. It handles rendering tiles and updating their states visually.

- **`Minesweeper.java`**  
  The main entry point for running the Minesweeper game. It initializes the MVC components and starts the application.

- **`RunMinesweeper.java`**  
  A utility class for launching and managing Minesweeper sessions.

- **`Tile.java`**  
  Defines individual tiles on the board, including their state (e.g., revealed, flagged) and whether they contain a mine.

---

## 🎮 How to Play

1. Clone this repository to your local machine:
git clone https://github.com/muditm006/Minesweeper.git
cd Minesweeper
2. Compile all `.java` files using your preferred Java IDE or command line:
javac *.java
3. Run `Minesweeper.java` or `RunMinesweeper.java` to start the game:
java Minesweeper
4. Use mouse clicks to interact with the game:
- **Left-click**: Reveal a tile.
- **Right-click**: Flag/unflag a tile.

5. Save or reset your progress using buttons in the status panel.

Enjoy playing Minesweeper! 🎉

---

## 📝 Notes

- This project demonstrates efficient use of Java's MVC design pattern for creating interactive applications.
- The flood-fill algorithm has been optimized for performance to handle large boards efficiently.
- File I/O ensures that players can save their progress and resume later without losing data.
- The code is modular, making it easy to extend or modify components like game rules or UI design.


