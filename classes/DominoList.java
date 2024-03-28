package classes;

public class DominoList {
    private DomiNode head = null;
    public int size = 0;

    // Node class
    static class DomiNode {
        public Domino value;
        public DomiNode next;
    
        public DomiNode(Domino value) {
            this.value = value;
            this.next = null;
        }
    }

    // Appends an item to the end of the list
    public void append(Domino value) {

        DomiNode node = new DomiNode(value);
        this.size++;
        
        // If the list is empty
        if (this.size == 1) {
            this.head = node;
            return;
        }
        
        DomiNode curr = this.head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = node;
    }

    // Check if list has a specific value
    public boolean has(Domino value) {
        DomiNode curr = this.head;
        while (curr != null) {
            if (Domino.compare(curr.value, value)) return true;
            curr = curr.next;
        }
        return false;
    }

    // Prints the list
    public void print() {
        if (this.size == 0) {
            System.out.println("List is empty");
            return;
        }

        String str = "[ ";
        DomiNode curr = this.head;

        while(curr != null) {
            int[] values = curr.value.getValues();
            str += "|" + values[0] + "," + values[1] + "| ";
            curr = curr.next;
        }

        System.out.print(str + "]");
    }
}
