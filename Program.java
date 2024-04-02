import java.util.*;
import classes.*;

public class Program {
    // Colors
    public static String white = "\u001B[37m";
    public static String green = "\u001B[32m";
    public static String red = "\u001B[31m";
    public static String blue = "\u001B[34m";
    public static String yellow = "\u001B[33m";

    // Init
    public static Scanner reader = new Scanner(System.in);

    public static Player player1 = new Player(
        null,
        serve()
    );
    public static Player player2 = new Player(
        null,
        serve()
    );

    public static DominoList board = new DominoList();

    public static boolean p1Turn = true;

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

        return list;
    }

    // Draw the screen
    public static void draw() {
        // Clear console
        System.out.print("\033[H\033[2J");
        System.out.flush(); 

        // Print the board
        System.out.print(yellow);
        board.print();
        System.out.print("\n\n\n");

        // Setup player-specific vars
        String name;
        DominoList deck;

        if (p1Turn) {
            name = blue+player1.name;
            deck = player1.deck;
        }
        else {
            name = blue+player2.name;
            deck = player2.deck;
        }

        // Check if the current player has a brick to put
        for(int i = 0; i < board.size; i++) {
            for(int j = 0; j < deck.size; j++) {
                if (canAttach(board.get(i), deck.get(j))) ;
            }
        }

        // Display whos turn it is
        System.out.print(name+"'s"+white+" turn.\n");

        // Display the player's deck
        for(int i = 0; i < deck.size; i++)
            System.out.print("╔═══╗");

        System.out.print("\n");
        for(int i = 0; i < deck.size; i++)
            System.out.print("║ "+deck.get(i).getValues()[0]+" ║");
            
        System.out.print("\n");
        for(int i = 0; i < deck.size; i++)
            System.out.print("╠═══╣");

        System.out.print("\n");
        for(int i = 0; i < deck.size; i++)
            System.out.print("║ "+deck.get(i).getValues()[1]+" ║");

        System.out.print("\n");
        for(int i = 0; i < deck.size; i++)
            System.out.print("╚═══╝");

        System.out.print("\n");
        for(int i = 0; i < deck.size; i++)
            System.out.print(" ["+(i+1)+"] ");

        choose(p1Turn ? player1 : player2);
    }

    public static void choose(Player p) {
        // pick a brick to use
        int choice;

        System.out.print("\n\n\nPick one of the "+yellow+"dominoes"+white+": "+green);
        
        choice = reader.nextInt();
        
        while(choice < 1 || choice > p.deck.size) {
            System.out.print(red+"Number was out of range "+white+"please try again: "+green);
            choice = reader.nextInt();
        }

        if (canAttach(board.get(0), p.deck.get(choice-1))) {
            board.prepend(p.deck.get(choice-1));
            p.deck.removeAt(choice-1);
        }
        else if (canAttach(board.get(board.size-1), p.deck.get(choice-1))) {
            board.append(p.deck.get(choice-1));
            p.deck.removeAt(choice-1);
        }
    }

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 

        // Setup players
        System.out.print(red+"Player 1"+white+" enter your name: "+green);
        player1.name = reader.nextLine();
        System.out.print(blue+"Player 2"+white+" enter your name: "+green);
        player2.name = reader.nextLine();

        board.append(new Domino());

        while(true) {
            draw();
            p1Turn = !p1Turn;
        }
    }
}