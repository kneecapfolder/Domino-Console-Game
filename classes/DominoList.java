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

    // Inserts a value to the start of the list
    public void prepend(Domino value) {
        DomiNode node = new DomiNode(value);
        node.next = this.head;
        this.head = node;
        this.size++;
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

    // Get value by index
    public Domino get(int index) {
        if (this.size == 0 || index < 0 || index > size-1) return null;

        DomiNode curr = this.head;
        if (index == 0) return head.value;
        else {
            for(int i = 0; i < index; i++)
                curr = curr.next;
        }
        return curr.value;
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

    // Remove value by index
    public void removeAt(int index) {
        if (this.size == 0 || index < 0 || index >= this.size) return;

        this.size--;
        if (index == 0) this.head = this.head.next;
        else {
            DomiNode prev = this.head;
            DomiNode nextNode;
            for(int i = 0; i < index-1; i++)
                prev = prev.next;
            nextNode = prev.next.next;
            prev.next = nextNode;
        }
    }

    // Prints the list
    public void print() {
        String white = "\u001B[37m";
        String yellow = "\u001B[33m";

        if (this.size == 0) {
            System.out.println("List is empty");
            return;
        }

        String str = white+"[ ";
        DomiNode curr = this.head;

        while(curr != null) {
            int[] values = curr.value.getValues();
            str += "|";

            if (curr.value.getUsable()[0]) str += yellow;
            else str += white;

            str += values[0];
            str += white + ",";

            if (curr.value.getUsable()[1]) str += yellow;
            else str += white;

            str += values[1];
            str += white + "| ";
            curr = curr.next;
        }

        System.out.print(str + "]");
    }
}
