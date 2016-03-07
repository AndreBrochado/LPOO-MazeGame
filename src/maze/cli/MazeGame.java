package maze.cli;

import maze.logic.Board;
import maze.logic.GameCharacter;
import maze.logic.GameObject;

import java.util.Scanner;

/**
 * Created by Andre on 27/02/2016.
 */


public class MazeGame {

    private char inputReader() {
        Scanner reader = new Scanner(System.in);
        char c = reader.next().charAt(0);
        return c;
    }

    private void displayMenu(String[] s) {
        for (int i = 0; i < s.length; i++) {
            System.out.println((i + 1) + ". " + s[i]);
        }
        System.out.print("Insert your choice: ");
    }

    private int askForUserInput(int min, int max) {

        int choice = 0;
        boolean invalidInput = true;
        while (invalidInput) {

            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            if (choice <= max && choice >= min) {
                invalidInput = false;
                break;
            }
            System.out.print("Invalid input, insert your choice: ");
        }
        return choice;
    }

    public Board prepareGameBoard() {
        GameObject empty = new GameObject();

        char[] wallReps = {'X', ' '};
        GameObject wall = new GameObject(-1, -1, wallReps, true, false);

        char[] swordReps = {'E', ' '};
        GameObject sword = new GameObject(1, 8, swordReps, false, true);

        char[] heroReps = {'H', ' ', 'A'};
        GameCharacter hero = new GameCharacter(1, 1, heroReps);

        char[] dragonReps = {'D', ' ', 'F', 'd'};
        GameCharacter dragon = new GameCharacter(1, 3, dragonReps);

        char[] exitReps = {'S', ' '};
        GameObject exit = new GameObject(9, 5, exitReps);

        GameObject[] objects = {empty, wall, sword, exit};
        GameCharacter[] characters = {hero, dragon};
        return new Board(10, 10, objects, characters);
    }

    private boolean playGame(boolean dragonMoves, boolean dragonSleeps) {
        Board gameBoard = prepareGameBoard();
        gameBoard.updateBoard();

        int boardState;
        do {
            gameBoard.print();
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

    public void chooseGameMode() {

        int choice;

        //TODO: replace while(true) to exit on 4
        while (true) {
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
                case 4:
                    System.exit(0);

            }
            if (playerWon)
                System.out.println("Congratulations! You won the game!");
            else
                System.out.println("You lost the game!");
        }

    }

    public static void main(String[] args) {
        MazeGame game = new MazeGame();
        int choice;

        //TODO: replace while(true) to exit on 2
        while (true) {
            String[] options = {"New Game", "Exit"};

            game.displayMenu(options);

            choice = game.askForUserInput(1, 2);

            switch (choice) {
                case 1:
                    game.chooseGameMode();
                    break;
                case 2:
                    System.exit(0);
            }
        }
    }

}
