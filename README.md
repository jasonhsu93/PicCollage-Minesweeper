# Minesweeper Game in Java
This Minesweeper game is a simple console-based implementation of the classic game where players uncover tiles on a grid without hitting mines. This implementation allows customization of the grid size and the number of mines.

# Features
Customizable board size: Players can define the width and height of the game board.
Customizable mine count: Players can set the number of mines on the board.
Automatic win/loss detection: The game automatically detects and announces a win or loss condition.

# Prerequisites
To run this game, you will need:

Java Runtime Environment (JRE) or Java Development Kit (JDK) version 8 or higher.

# Installation
No installation is required. You only need to have Java installed on your machine.

# Running the Game
To run the game, follow these steps:

1. Download the source code: Clone or download the zip file of the project to your local machine.
2. Navigate to the project directory: Open your command line interface (CLI) and navigate to the directory where the project files are located.
3. Compile the Java program:
javac Minesweeper.java
4. Run the compiled Java program:
java Minesweeper
Follow the on-screen prompts in the terminal to start playing the game. You will be asked to enter the number of rows, columns, and mines you would like on the board.

# How to Play
Upon starting the game, enter the dimensions of the board and the number of mines.
Input coordinates to reveal a spot on the board. The format is row col, where row is the row number and col is the column number. Both start from 0.
The game will reveal the cell at the specified coordinates. If you hit a mine, the game ends. Otherwise, it will either show a number (indicating the number of adjacent mines) or automatically reveal adjacent areas if there are no adjacent mines.
Continue revealing cells until all non-mine cells are revealed to win the game.
At any time after revealing a mine, you can choose to restart the game or exit.
