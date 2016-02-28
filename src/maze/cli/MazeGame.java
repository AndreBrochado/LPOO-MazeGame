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

}
