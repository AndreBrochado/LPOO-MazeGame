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
    public static void displayGameMenu(String[] s){
        for(int i =0; i < s.length; i++){
            System.out.println((i+1) + ". " + s[i]);
        }
        System.out.print("Insert your choice: ");
    }

    public static void ChooseGameMode (){

        int ch;
        Scanner sc=new Scanner(System.in);

        while(true)
        {
            String[] s;
            s = new String[4];
            s[0] = "Immobile Dragon";
            s[1] = "Dragon with random movement";
            s[2] = "Dragon with random movement and rest";
            s[3] = "Exit";

            displayGameMenu(s);
            ch = sc.nextInt();
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
                default:
                    System.out.println("invalid");
            }
        }

    }

    public static void main(String[] args){

        int ch;
        Scanner sc=new Scanner(System.in);

        while(true)
        {
            String[] s;
            s = new String[2];
            s[0] = "New Game";
            s[1] = "Exit";

            displayGameMenu(s);
            ch = sc.nextInt();
            switch(ch)
            {
                case 1:
                    ChooseGameMode();
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("invalid");
            }
        }
    }

}
