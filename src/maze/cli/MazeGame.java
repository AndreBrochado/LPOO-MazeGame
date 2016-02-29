package maze.cli;
import java.util.Scanner;

/**
 * Created by Andre on 27/02/2016.
 */



public class MazeGame {

    private char inputReader(){
    Scanner reader = new Scanner(System.in);
    char c = reader.next().charAt(0);
    return c;
    }

    private void displayMenu(String[] s){
        for(int i =0; i < s.length; i++){
            System.out.println((i+1) + ". " + s[i]);
        }
        System.out.print("Insert your choice: ");
    }

    private int askForUserInput(int min, int max){

        int choice= 0;
        boolean invalidInput = true;
        while(invalidInput) {

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

    public void chooseGameMode(){

        int choice;

        //TODO: replace while(true) to exit on 4
        while(true)
        {
            String[] options = {"Immobile Dragon","Dragon with random movement","Dragon with random movement and rest","Exit"};

            displayMenu(options);
            choice = askForUserInput(1,4);

            switch(choice)
            {
                case 1:
                    System.out.println("Game1;");
                    break;
                case 2:
                    System.out.println("Game2;");
                    break;
                case 3:
                    System.out.println("Game3;");
                    break;
                case 4:
                    System.exit(0);

            }
        }

    }

    public static void main(String[] args){
        MazeGame game = new MazeGame();
        int choice;

        //TODO: replace while(true) to exit on 2
        while(true)
        {
            String[] options = {"New Game", "Exit"};

            game.displayMenu(options);

            choice = game.askForUserInput(1,2);

            switch(choice)
            {
                case 1:
                    game.chooseGameMode();
                    break;
                case 2:
                    System.exit(0);
            }
        }
    }

}
