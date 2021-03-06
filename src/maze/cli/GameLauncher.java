package maze.cli;

import maze.logic.Board;
import maze.logic.MazeGame;

import java.util.Scanner;

public class GameLauncher {

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

    private Board createBoard(){
        boolean validSize = false;
        Board gameBoard = null;

        while (!validSize) {
            try {
                System.out.print("Please insert board size: ");
                int size = askForUserInput(5, 5000);
                System.out.print("Please insert number of dragons (1 - " + size/2 + "): ");
                int noDragons = askForUserInput(1, size/2);
                gameBoard = new Board(size, noDragons);
                validSize = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Board size must be odd!");
            }
        }
        return gameBoard;
    }

    private boolean playGame(int gameMode) {

        MazeGame game = new MazeGame(createBoard(), gameMode);

        int boardState;
        do {
            this.printBoard(game.getGameBoard());
            System.out.print("Insert your movement choice (W - up, A - left, S - down, D - right: ");
            char input = Character.toLowerCase(inputReader());
            boardState = game.makePlay(input);
        } while (boardState == MazeGame.GAME_UNDERWAY);

        return boardState != 1;
    }

    public void chooseGameMode() {

        int choice;

        while (true) {
            String[] options = {"Immobile Dragon", "Dragon with random movement", "Dragon with random movement and rest", "Exit"};

            displayMenu(options);
            choice = askForUserInput(1, 4);

            if(choice == 4)
                System.exit(0);

            boolean playerWon = false, playedGame = choice != 4;

            playerWon = playGame(choice-1);

            if (playedGame) {
                if (playerWon)
                    System.out.println("Congratulations! You won the game!");
                else {
                    System.out.println("You lost the game!");
                    System.out.println("Do you want to play again? Choose your game mode or exit");
                }
            } else
                System.out.println("Ok :( Goodbye!");
        }
    }

    public static void main(String[] args) {
        GameLauncher game = new GameLauncher();
        int choice;

        do {
            String[] options = {"New Game", "Exit"};

            game.displayMenu(options);

            choice = game.askForUserInput(1, 2);

            if(choice != 2)
                game.chooseGameMode();

        } while (choice != 2);

        System.exit(0);
    }
}
