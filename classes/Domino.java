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

    public static boolean compare(Domino brick1, Domino brick2) {
        boolean flag = true;

        for(int i = 0; i < 2; i++)
            if (brick1.getValues()[0] != brick2.getValues()[1]) flag = false;
        
        if (flag) return true;

        // Checked flipped array
        else for(int i = 0; i < 2; i++)
            if (brick1.getValues()[0] != brick2.getValues()[1]) return false;

        return true;
    }

    public void printStats() {
        System.out.println(
            "brick 1: " + this.values[0] +
            "\nbrick 2: " + this.values[1]
        );
    }
}
