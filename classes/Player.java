package classes;

public class Player {
    public String name;
    public DominoList deck;

    public Player(
        String name,
        DominoList deck
    ) {
        this.name = name;
        this.deck = deck;
    }
}
