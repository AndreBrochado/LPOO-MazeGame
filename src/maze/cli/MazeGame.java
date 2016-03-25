package maze.cli;

import com.sun.javaws.exceptions.InvalidArgumentException;
import maze.logic.Board;
import maze.logic.GameCharacter;
import maze.logic.GameObject;

import java.util.Scanner;

/**
 * Created by Andre on 27/02/2016.
 */


public class MazeGame {

    //reads a char from the user and returns it
    private char inputReader() {
        Scanner reader = new Scanner(System.in);
        char c = reader.next().charAt(0);
        return c;
    }

    //outputs menus given an array of choices
    private void displayMenu(String[] s) {
        for (int i = 0; i < s.length; i++) {
            System.out.println((i + 1) + ". " + s[i]);
        }
        System.out.print("Insert your choice: ");
    }

    //reads a number from the user between min and max and returns it
    private int askForUserInput(int min, int max) {
        int choice = 0;
        boolean invalidInput = true;

        while (invalidInput) {
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            if (choice <= max && choice >= min)
                invalidInput = false;
            if (invalidInput)
                System.out.print("Invalid input, insert your choice: ");
        }
        return choice;
    }

    private void printBoard(Board b) {
        System.out.println(b);
    }

    //this function implements the game loop
    private boolean playGame(boolean dragonMoves, boolean dragonSleeps) {
        boolean validSize = false;
        Board gameBoard = null;

        while (!validSize) {
            try {
                System.out.print("Please insert board size: ");
                int size = askForUserInput(5, 5000);
                gameBoard = new Board(size);
                validSize = true;
            } catch (IllegalArgumentException ignore) {
            }
        }

        int boardState;
        do {
            this.printBoard(gameBoard);
            System.out.print("Insert your movement choice (W - up, A - left, S - down, D - right: ");
            char input = inputReader();
            gameBoard.moveHero(input);
            gameBoard.updateBoard();
            if (dragonSleeps)
                gameBoard.handleAllDragonsSleep();
            if (dragonMoves)
                gameBoard.moveAllDragons();
            boardState = gameBoard.getBoardState();
        } while (boardState == 0);

        return boardState != 1;
    }

    //allows the player to choose one of the 3 Game Modes
    public void chooseGameMode() {

        int choice = 0;

        while (choice != 4) {
            String[] options = {"Immobile Dragon", "Dragon with random movement", "Dragon with random movement and rest", "Exit"};

            displayMenu(options);
            choice = askForUserInput(1, 4);
            boolean playerWon = false;

            switch (choice) {
                case 1:
                    playerWon = playGame(false, false);
                    break;
                case 2:
                    playerWon = playGame(true, false);
                    break;
                case 3:
                    playerWon = playGame(true, true);
                    break;
            }
            if (playerWon)
                System.out.println("Congratulations! You won the game!");
            else
                System.out.println("You lost the game!");
            System.out.println("Do you want to play again? Choose your game mode or exit");
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        MazeGame game = new MazeGame();
        int choice = 0;

        do {
            String[] options = {"New Game", "Exit"};

            game.displayMenu(options);

            choice = game.askForUserInput(1, 2);

            game.chooseGameMode();

        } while (choice != 2);

        System.exit(0);
    }
}
