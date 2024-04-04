package classes;

public class Domino {
    private int[] values = new int[2];
    private boolean[] usable = new boolean[2];

    public Domino() {
        this.values = new int[]{
            (int)(Math.random() * 6 + 1),
            (int)(Math.random() * 6 + 1)
        };
        this.usable = new boolean[] {
            true, true
        };
    }

    // Set methods
    public void setValues(int[] values) {
        this.values = values;
    }

    public void setUsable(boolean[] usable) {
        this.usable = usable;
    }

    public void setUsable(boolean usable, int index) {
        if (index != 0 && index != 1) return;
        this.usable[index] = usable;
    }

    // Get methods
    public int[] getValues() {
        return this.values;
    }

    public boolean[] getUsable() {
        return this.usable;
    }

    // Compare 2 domino bricks
    public static boolean compare(Domino brick1, Domino brick2) {
        boolean flag = true;

        for(int i = 0; i < 2; i++)
            if (brick1.getValues()[i] != brick2.getValues()[i]) flag = false;
        
        if (flag) return true;

        // Checked flipped array
        else for(int i = 0; i < 2; i++)
            if (brick1.getValues()[i] != brick2.getValues()[1-i]) return false;

        return true;
    }

    // Flip the order of values
    public void flip() {
        int temp = this.values[0];
        this.values[0] = this.values[1];
        this.values[1] = temp;
    }

    public void printStats() {
        System.out.println(
            "brick 1: " + this.values[0] +
            "\nbrick 2: " + this.values[1]
        );
    }
}
