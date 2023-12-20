# Battleship Program Overview

Implemented a one-player version of the Battleship game in Java.

## Specifications

- Implemented classes: BattleshipGame, Ocean, Ship (abstract class), Battleship, Cruiser, Destroyer, Submarine, EmptySea.
- Maintained a 10x10 array of Ship objects in the Ocean class.
- Utilized abstract class Ship and its subclasses for different ship types.
- Ensured proper ship placement, gameplay rules, and scoring.

## How to Play

- Set up the game on a 10x10 ocean with ten ships of different types.
- Player aims to sink the fleet by providing row and column numbers for shots.
- Computer responds with hit or miss messages.
- Ships are sunk when all tiles are hit, and the game ends when the fleet is sunk.
- Displayed ocean state after each shot.
- Calculated and displayed the final score based on the number of shots taken.
