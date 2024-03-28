import java.util.*;
import classes.*;

public class Program {
    public static Scanner reader = new Scanner(System.in);

    // Check if 2 domino bricks can be put together
    public static boolean canAttach(Domino d1, Domino d2) {
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                if (d1.getValues()[i] == d2.getValues()[j]) return true;
        return false;
    }

    // Create a deck of 7 dominos
    public static DominoList serve() {
        DominoList list = new DominoList();
        
        for(int i = 0; i < 7; i++) {
            Domino brick = new Domino();
            
            // Regenerate the brick until it's unique
            while(list.has(brick))
                brick = new Domino();
            
            list.append(brick);
        }

        list.print();
        
        return list;
    }

    // Draw the screen
    public static void draw() {

    }

    public static void main(String[] args) {
        serve();
    }
}