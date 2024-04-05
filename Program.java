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
    public static int canAttach(Domino brick1, Domino brick2, int index) {
        if (index != 0 && index != 1) return -1;
        for(int j = 0; j < 2; j++)
            if (brick1.getUsable()[index] && brick1.getValues()[index] == brick2.getValues()[j]) return j;
        return -1;
    }

    // Create a deck of 7 dominos
    public static DominoList serve() {
        DominoList list = new DominoList();
        
        for(int i = 0; i < 7; i++)
            list.append(draw(list));

        return list;
    }

    // Draw a random domino brick thats not in the deck
    public static Domino draw(DominoList deck) {
        Domino brick = new Domino();

        // Regenerate the brick until it's unique
        while(deck.has(brick))
            brick = new Domino();
        
        return brick;
    }

    // Render the screen
    public static void render() {
        // Clear console
        System.out.print("\033[H\033[2J");
        System.out.flush(); 

        // Print the board
        System.out.print(yellow);
        board.print();
        System.out.print("\n\n\n");

        // Setup player-specific vars
        String name = "";
        DominoList deck = new DominoList();
        boolean[] possible = {};
        boolean flag = false;

        while (!flag) {

            if (p1Turn) {
                name = player1.name;
                deck = player1.deck;
            }
            else {
                name = player2.name;
                deck = player2.deck;
            }

            possible = new boolean[deck.size];

            // Get array of possible choices
            for(int i = 0; i < deck.size; i++) {
                if (canAttach(board.get(0), deck.get(i), 0) != -1 ||
                canAttach(board.get(board.size-1), deck.get(i), 1) != -1) {
                    possible[i] = true;
                    flag = true;
                }
                else possible[i] = false;
            }

            // Check if the current player has a brick to put
            if (!flag) {
                p1Turn = !p1Turn;
                System.out.println(name+"'s "+yellow+"turn passed.");
                if (deck.size < 7) {
                    deck.append(draw(deck));
                    System.out.println(green+"+ 1 brick added");
                }
                System.out.print("\n");
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

        // Let the user choose a brick to use
        if (p1Turn) choose(player1, possible);
        else choose(player2, possible);
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
            canAttach(board.get(0), p.deck.get(choice-1), 0),
            canAttach(board.get(board.size-1), p.deck.get(choice-1), 1)
        };
        
        if (attachmentIndex[0] != -1 || attachmentIndex[1] != -1) {

            if (attachmentIndex[0] != -1) {
                if (attachmentIndex[0] == 0) p.deck.get(0).flip();
                board.get(0).setUsable(false, 0);
                p.deck.get(choice-1).setUsable(new boolean[] {true, false});
                board.prepend(p.deck.get(choice-1));
            }
            else {
                if (attachmentIndex[1] == 1) p.deck.get(choice-1).flip();
                board.get(board.size-1).setUsable(false, 1);
                p.deck.get(choice-1).setUsable(new boolean[] {false, true});
                board.append(p.deck.get(choice-1));
            }

            p.deck.removeAt(choice-1);
        }
    }

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 

        // Start screen
        System.out.println(yellow +
            "  _____                  _             \n" +
            " |  __ \\                (_)            \n" +
            " | |  | | ___  _ __ ___  _ _ __   ___  \n" +
            " | |  | |/ _ \\| '_ ` _ \\| | '_ \\ / _ \\ \n" +
            " | |__| | (_) | | | | | | | | | | (_) |\n" +
            " |_____/ \\___/|_| |_| |_|_|_| |_|\\___/ \n" +
            "                                       \n" +
            "                                       \n"
        );

        // Setup players
        System.out.print(red+"Player 1"+white+" enter your name: "+green);
        player1.name = red + reader.nextLine();
        System.out.print(blue+"Player 2"+white+" enter your name: "+green);
        player2.name = blue + reader.nextLine();

        board.append(new Domino());

        String winner = "";

        while(winner == "") {
            render();
            if (player1.deck.size == 0)
                winner = player1.name;
            else if (player2.deck.size == 0)
                winner = player2.name;

            p1Turn = !p1Turn;
        }
        
        System.out.print("\033[H\033[2J");
        System.out.flush(); 

        System.out.print(winner + yellow + " won!");
    }
}