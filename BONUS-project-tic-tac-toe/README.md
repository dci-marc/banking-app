### **Tic Tac Toe Project Instructions**

---

## **Objective**
Create a CLI-based Tic Tac Toe game in Java where two players can play against each other. The project should demonstrate understanding of core Java concepts, including object-oriented programming, flow control, and basic I/O.

---

## **Requirements**

### **1. Game Setup**
- The game should be played on a **3x3 grid**.
- Each cell in the grid should be represented by a number (1-9) corresponding to its position:
  ```
  1 | 2 | 3
  ---------
  4 | 5 | 6
  ---------
  7 | 8 | 9
  ```
- Player 1 uses `X` and Player 2 uses `O`.

---

### **2. Game Rules**
- Players take turns choosing a position on the grid.
- A player can only choose an empty cell.
- The game ends when:
  - One player achieves **three in a row** (horizontally, vertically, or diagonally).
  - All cells are filled (resulting in a draw).

---

### **3. Core Features**
- **Display the Board**: After each move, display the updated board.
- **Input Validation**: Ensure players choose valid, unoccupied positions.
- **Winning Condition**: Check for a winner after each move.
- **Draw Condition**: Detect if the game ends in a draw.

---

### **4. Implementation Details**
1. **Classes and Methods**:
   - `TicTacToe` class:
     - `main(String[] args)`: Entry point of the game.
     - `printBoard()`: Displays the current state of the board.
     - `makeMove(int player)`: Handles the player's move.
     - `checkWinner()`: Checks if there's a winner.
     - `isDraw()`: Checks if the game ends in a draw.
   - Optionally, create a `Player` class to manage player-specific details.

2. **Input Handling**:
   - Use `Scanner` to get input from players.
   - Validate input to ensure the position is within 1-9 and not already occupied.

3. **Flow Control**:
   - Alternate turns between players.
   - Break the loop when there's a winner or a draw.

---

## **Bonus Features**
1. **Replay Option**:
   - After a game ends, allow players to start a new game or exit.

2. **AI Opponent**:
   - Add a single-player mode where the second player is an AI.
   - The AI should make random valid moves (basic) or use a strategy (advanced).

3. **Advanced Grid**:
   - Allow the game to support custom board sizes (e.g., 4x4, 5x5).

---

## **Sample Output**

### **Welcome Message**
```
Welcome to Tic Tac Toe!
Player 1: X
Player 2: O
```

### **Gameplay Example**
```
Current Board:
1 | 2 | 3
---------
4 | X | 6
---------
O | 8 | 9

Player 1, enter your move (1-9): 5
```

### **Winning Message**
```
Player 1 wins! Congratulations!
```

### **Draw Message**
```
It's a draw! Better luck next time.
```

---

## **Submission Requirements**
1. Submit the source code with proper documentation and comments.
2. Include a README file with:
   - Brief description of the project.
   - Instructions on how to compile and run the game.
3. Ensure the code is modular and adheres to OOP principles.

---

## **Evaluation Criteria**
- Correctness: The game behaves as expected for all scenarios.
- Code Quality: Code is clean, readable, and well-documented.
- Features: Implementation of all required and bonus features.
- Input Validation: Proper handling of invalid input.

---

This project provides an excellent opportunity to practice problem-solving, modular programming, and debugging skills. Have fun coding! 🎉