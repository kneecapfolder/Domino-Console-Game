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
    public static int canAttach(Domino d1, Domino d2) {
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                if (d1.getUsable()[i] && d1.getValues()[i] == d2.getValues()[j]) return j;
        return -1;
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
        String name = "";
        DominoList deck;
        boolean[] possible = {};
        boolean flag = false;

        if (p1Turn) deck = player1.deck;
        else deck = player2.deck;

        while (!flag) {
            possible = new boolean[deck.size];

            if (p1Turn) {
                name = red+player1.name;
                deck = player1.deck;
            }
            else {
                name = blue+player2.name;
                deck = player2.deck;
            }

            // Get array of possible choices
            for(int i = 0; i < deck.size; i++) {
                if (canAttach(board.get(0), deck.get(i)) != -1 ||
                canAttach(board.get(board.size-1), deck.get(i)) != -1) {
                    possible[i] = true;
                    flag = true;
                }
                else possible[i] = false;
            }

            // Check if the current player has a brick to put
            if (!flag) {
                p1Turn = !p1Turn;
                System.out.println(name+"'s "+yellow+"turn passed.\n");
            }
        }
        
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

        choose(p1Turn ? player1 : player2, possible);
    }

    public static void choose(Player p, boolean[] possible) {
        // pick a brick to use
        int choice;

        System.out.print("\n\n\nPick one of the "+yellow+"dominoes"+white+": "+green);
        
        choice = reader.nextInt();
        
        while(choice < 1 || choice > p.deck.size || !possible[choice-1]) {
            System.out.print(red+"Invalid choice "+white+"please try again: "+green);
            choice = reader.nextInt();
        }

        // See if the chosen brick is attachable on any end of the board
        int[] attachmentIndex = new int[] {
            canAttach(board.get(0), p.deck.get(choice-1)),
            canAttach(board.get(board.size-1), p.deck.get(choice-1))
        };
        
        if (attachmentIndex[0] != -1 || attachmentIndex[1] != -1) {
            boolean[] usable = new boolean[] {true, true};

            if (attachmentIndex[0] != -1) {
                board.get(0).setUsable(false, 0);
                usable[attachmentIndex[0]] = false;
                p.deck.get(choice-1).setUsable(usable);
                board.prepend(p.deck.get(choice-1));
            }
            else {
                board.get(board.size-1).setUsable(false, 1);
                usable[attachmentIndex[1]] = false;
                p.deck.get(choice-1).setUsable(usable);
                board.append(p.deck.get(choice-1));
            }

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