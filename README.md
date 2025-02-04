# Minesweeper Game

A custom implementation of the classic Minesweeper game, built using Java with the **MVC (Model-View-Controller)** framework. This project features a custom-designed board, a status panel, and robust game logic, including an optimized flood-fill algorithm for uncovering tiles.

## Features

- **Custom Board Design**  
  Built using Java's JSwing library, the game includes a visually appealing board and status panel to display the current game state.
  
- **MVC Architecture**  
  The project is segmented into three distinct areas:  
  - **Model**: Manages game logic and data (e.g., tile states, mine placement).  
  - **View**: Displays the game board and status panel.  
  - **Controller**: Handles user interactions like saving, loading, resetting, and uncovering tiles.
  
- **Optimized Flood-Fill Algorithm**  
  A refined `revealTiles` method ensures adjacent tiles are properly uncovered when an empty tile is clicked, avoiding infinite recursion.

## File Descriptions

- **Game.java**  
  Implements the core game logic, including win/loss conditions and interactions with other components.

- **GameBoard.java**  
  Represents the game board as part of the View. It handles rendering tiles and updating their states visually.

- **Minesweeper.java**  
  The main entry point for running the Minesweeper game. It initializes the MVC components and starts the application.

- **RunMinesweeper.java**  
  A utility class for launching and managing Minesweeper sessions.

- **Tile.java**  
  Defines individual tiles on the board, including their state (e.g., revealed, flagged) and whether they contain a mine.

## How to Play

1. Clone this repository to your local machine.
2. Compile all `.java` files using your preferred Java IDE or command line.
3. Run `Minesweeper.java` or `RunMinesweeper.java` to start the game.
4. Use mouse clicks to reveal tiles or flag potential mines:
   - Left-click: Reveal a tile.
   - Right-click: Flag/unflag a tile.
5. Save or reset your progress using buttons in the status panel.

Enjoy playing Minesweeper!
