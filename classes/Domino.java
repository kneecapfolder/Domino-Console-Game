package classes;

public class Domino {
    private int[] values = new int[2];

    public Domino() {
        this.values = new int[]{
            (int)(Math.random() * 6 + 1),
            (int)(Math.random() * 6 + 1)
        };
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int[] getValues() {
        return this.values;
    }

    public void printStats() {
        System.out.println(
            "brick 1: " + this.values[0] +
            " brick 2: " + this.values[1]
        );
    }
}
