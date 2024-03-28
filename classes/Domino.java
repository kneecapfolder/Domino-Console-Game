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
