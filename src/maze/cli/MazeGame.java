package maze.cli;
import java.util.Scanner;

/**
 * Created by Andre on 27/02/2016.
 */



public class MazeGame {

    private char input_reader(){
    Scanner reader = new Scanner(System.in);
    char c = reader.next().charAt(0);
    return c;
    }
    
    private void displayGameMenu(String[] s){
        for(int i =0; i < s.length; i++){
            System.out.println((i+1) + ". " + s[i]);
        }
        System.out.print("Insert your choice: ");
    }

    private int askForUserInput(int min, int max){

        int ch = 0;
        boolean invalid_input = true;
        while(invalid_input) {

            Scanner sc = new Scanner(System.in);
            ch = sc.nextInt();
            if (ch <= max && ch >= min) {
                invalid_input = false;
                break;
            }
            System.out.print("Invalid input, insert your choice: ");
        }
        return ch;
    }

    public void ChooseGameMode (){

        int ch;

        while(true)
        {
            String[] s;
            s = new String[4];
            s[0] = "Immobile Dragon";
            s[1] = "Dragon with random movement";
            s[2] = "Dragon with random movement and rest";
            s[3] = "Exit";

            displayGameMenu(s);
            ch = askForUserInput(1,4);

            switch(ch)
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
        int ch;

        while(true)
        {
            String[] s;
            s = new String[2];
            s[0] = "New Game";
            s[1] = "Exit";

            game.displayGameMenu(s);

            ch = game.askForUserInput(1,2);

            switch(ch)
            {
                case 1:
                    game.ChooseGameMode();
                    break;
                case 2:
                    System.exit(0);
            }
        }
    }

}
