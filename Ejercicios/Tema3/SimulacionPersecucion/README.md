# JavaFX Persecution Simulation

This JavaFX application implements a simple simulation game where the player (represented by a blue circle) needs to avoid collisions with an enemy (represented by a red circle) while navigating through a grid-based environment. The game keeps track of the player's moves, displays the current score, and features a countdown before closing the application.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Gameplay](#gameplay)
- [High Score](#high-score)
- [Closing the Application](#closing-the-application)
- [SimulationApplication Class](#simulationapplication-class)
- [SimulationController Class](#simulationcontroller-class)

## Overview

The project consists of two main classes: [SimulationApplication](src/main/java/org/example/simulacionpersecucion/SimulationApplication.java) and [SimulationController](src/main/java/org/example/simulacionpersecucion/SimulationController.java). The [SimulationApplication](src/main/java/org/example/simulacionpersecucion/SimulationApplication.java) class handles the initialization of the JavaFX application, including setting up the user interface, while the [SimulationController](src/main/java/org/example/simulacionpersecucion/SimulationController.java) class manages the game logic.

Key components of the application include:
- Player and enemy circles
- Grid-based layout
- Labels for game information
- High score tracking

## Features

1. **Player Movement**: The player can move within the grid using the W, A, S, and D keys (or arrow keys).

2. **Collision Detection**: The game checks for collisions between the player and the enemy. If a collision occurs, the game ends.

3. **Score Tracking**: The number of moves made by the player is displayed as the score.

4. **High Score**: The highest score achieved in previous sessions is stored and displayed.

5. **Countdown**: After the game ends, a countdown is displayed before closing the application.

## Getting Started

To run the simulation game, follow these steps:

1. Clone the repository or download the source code.

2. Open the project in an Integrated Development Environment (IDE) that supports JavaFX.

3. Run the [SimulationApplication](src/main/java/org/example/simulacionpersecucion/SimulationApplication.java) class to launch the application.

## Gameplay

- Use the W, A, S, and D keys (or arrow keys) to move the player within the grid.
- Avoid collisions with the enemy circle.
- The score is displayed as the number of moves made by the player.

## High Score

- The highest score achieved is tracked and displayed.
- If the current score surpasses the previous high score, a “NEW HIGH SCORE” message is shown.

## Closing the Application

- After the game ends, a countdown is displayed.
- The application automatically closes after the countdown.
**Note:** You can also close the program manually without worrying about losing the high score.

## [SimulationApplication](src/main/java/org/example/simulacionpersecucion/SimulationApplication.java) Class

The [SimulationApplication](src/main/java/org/example/simulacionpersecucion/SimulationApplication.java) class serves as the entry point for the JavaFX application. It initializes the user interface, sets up the scene, and handles user input.

Key features of the class include:
- Setting the width and height of the application window.
- Loading the FXML file for the user interface.
- Managing the scene, stage, and event handling.
- Launching the application.

## [SimulationController](src/main/java/org/example/simulacionpersecucion/SimulationController.java) Class

The [SimulationController](src/main/java/org/example/simulacionpersecucion/SimulationController.java) class serves as the controller for the JavaFX application. It is responsible for managing the game logic, handling user input, and updating the graphical user interface.

Key features of the class include:
- Player and enemy circle management.
- Grid-based layout and styling.
- Game initialization and entity generation.
- Movement of the player and enemy.
- High score tracking and countdown handling.

## [Player](src/main/java/org/example/simulacionpersecucion/Player.java) Class
The [Player](src/main/java/org/example/simulacionpersecucion/Player.java) class represents the entity controlled by the user.
It is a singleton class, providing methods to get and set the player's coordinates.

## [Enemy](src/main/java/org/example/simulacionpersecucion/Enemy.java) Class
The [Enemy](src/main/java/org/example/simulacionpersecucion/Enemy.java) class represents the entity that the player must avoid.
Like the Player class, it is a singleton class, ensuring that only one instance of the enemy is created.
The class provides methods to get and set the enemy's coordinates and implements the observable pattern to notify changes in coordinates.

## [Application View](src/main/resources/org/example/simulacionpersecucion/application_view.fxml)

The [Application View](src/main/resources/org/example/simulacionpersecucion/application_view.fxml) file defines the structure and layout of the user interface using JavaFX FXML. It includes elements such as circles for the player and enemy, labels for score and game information, and styling for visual appeal.

Make sure to use this FXML file in conjunction with the [SimulationController](src/main/java/org/example/simulacionpersecucion/SimulationController.java) class to achieve the intended functionality.

[!NOTE]

Make sure to check the console for any potential error messages or additional information.