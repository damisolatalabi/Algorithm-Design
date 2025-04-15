// Heap.java
// Implementation of a Binary Heap (Max Heap)

class Heap {
    private int[] a;  // Array to store heap elements
    private int N;    // Number of elements in heap
    private static final int maxH = 100;  // Default maximum size

    // Default constructor (heap with maxH size)
    Heap() {
        N = 0;
        a = new int[maxH + 1];  // Using 1-based indexing for easier calculations
    }

    // Constructor with a given size
    Heap(int size) {
        N = 0;
        a = new int[size + 1];  // Using 1-based indexing
    }

    // Move element up the heap to restore heap property
    void siftUp(int k) {
        int v = a[k];  // Store the value at index k
        a[0] = Integer.MAX_VALUE;  // Sentinel value to simplify loop

        while (a[k / 2] < v) { // Parent must be greater in max heap
            a[k] = a[k / 2];  // Move parent down
            k = k / 2;  // Move up
        }
        a[k] = v;  // Place the value in correct position
    }

    // Move element down the heap to restore heap property
    void siftDown(int k) {
        int v = a[k];  // Store the value to be moved
        int j;
        
        while (2 * k <= N) {  // While there is at least one child
            j = 2 * k;  // Left child
            if (j < N && a[j] < a[j + 1]) {  // Check if right child is larger
                j++;
            }
            if (v >= a[j]) {  // If parent is larger, stop
                break;
            }
            a[k] = a[j];  // Move child up
            k = j;  // Move index down
        }
        a[k] = v;  // Place value in correct position
    }

    // Insert a new value into the heap
    void insert(int x) {
        a[++N] = x;  // Place new value at the end
        siftUp(N);  // Restore heap property
    }

    // Remove and return the maximum element (root)
    int remove() {
        a[0] = a[1];  // Store highest priority value in a[0]
        a[1] = a[N--];  // Replace root with last element
        siftDown(1);  // Restore heap property
        return a[0];  // Return removed value
    }

    // Display the heap structure
    void display() {
        System.out.println("\nHeap structure:");
        System.out.println(a[1]);  // Print root

        for (int i = 1; i <= N / 2; i *= 2) {
            for (int j = 2 * i; j < 4 * i && j <= N; ++j) {
                System.out.print(a[j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Main function to test the heap
    public static void main(String args[]) {
        Heap h = new Heap();
        int r;
        double x;

        // Insert random numbers between 0 and 99 into heap
        for (int i = 1; i <= 10; ++i) {
            x = (Math.random() * 100.0);
            r = (int) x;
            System.out.println("Inserting " + r);
            h.insert(r);
            h.display();
        }

        // Remove elements one by one
        System.out.println("Removing elements:");
        while (h.N > 0) {
            System.out.println("Removed: " + h.remove());
            h.display();
        }
    }
}
