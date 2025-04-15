class Node {
    int data;
    Node next;
    Node prev; // Added previous pointer

    public Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class SortedDLL {
    private Node head;

    public SortedDLL() {
        head = null;
    }

    // Insert a node in sorted order
    public void insert(int x) {
        Node newNode = new Node(x);
        if (head == null || head.data >= x) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            return;
        }

        Node curr = head;
        while (curr.next != null && curr.next.data < x) {
            curr = curr.next;
        }
        newNode.next = curr.next;
        if (curr.next != null) {
            curr.next.prev = newNode;
        }
        curr.next = newNode;
        newNode.prev = curr;
    }

    // Remove a node from the list
    public boolean remove(int x) {
        if (head == null) return false;
        if (head.data == x) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            return true;
        }

        Node curr = head;
        while (curr != null && curr.data != x) {
            curr = curr.next;
        }

        if (curr == null) return false;
        if (curr.next != null) {
            curr.next.prev = curr.prev;
        }
        if (curr.prev != null) {
            curr.prev.next = curr.next;
        }
        return true;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Display the doubly linked list
    public void display() {
        Node t = head;
        System.out.print("\nHead -> ");
        while (t != null) {
            System.out.print(t.data + " <-> ");
            t = t.next;
        }
        System.out.println("NULL\n");
    }
}
