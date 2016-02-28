package maze.cli;
import java.util.Scanner;

/**
 * Created by Andre on 27/02/2016.
 */



public class MazeGame {

public char input_reader(){
    Scanner reader = new Scanner(System.in);
    char c = reader.next().charAt(0);
    return c;
}

    public void NewGameType(){

        int ch;
        Scanner sc=new Scanner(System.in);

        while(true)
        {
            System.out.println("1. Immobile Dragon");
            System.out.println("2. Dragon with random movement");
            System.out.println("3. Dragon with random movement and rest");
            System.out.println("4. Exit");
            System.out.print("Insert your choice: ");
            ch = sc.nextInt();
            switch(ch)
            {
                case 1:
                    Game1();
                    break;
                case 2:
                    Game2();
                    break;
                case 3:
                    Game3();
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
            System.out.println("1. New Game");
            System.out.println("2. Exit");
            System.out.print("Insert your choice: ");
            ch = sc.nextInt();
            switch(ch)
            {
                case 1:
                    NewGameType();
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("invalid");
            }
        }
    }

}
