
// Exercise to separate ADT Queue from its implementation
// and to provide 2 implementations. Also exception handling.

class QueueException extends Exception {
    // do as in stack example
    public QueueException(String q){
        super(q);
    }
}    

// In Java an interface can often be the best way to 
// describe an Abstract Data Type (ADT) such as Queue or Stack
interface Queue {
    public void enQueue(int x) throws QueueException;
    public int deQueue() throws QueueException;
    public boolean isEmpty();   
}


class QueueLL implements Queue {
    
    
    private class Node {
        int data;
        Node next;
    }

    Node z, head, tail;

    
    public QueueLL() {
        z = new Node(); 
        z.next = z;
        head = z;
        tail = null;
        
    }
    
    
    public void enQueue( int x) {
        Node t;

        t = new Node();
        t.data = x;
        t.next = z;

        if(head == z)       // case of empty list
            head = t;
        else                // case of list not empty
            tail.next = t;
            
        tail = t;           // new node is now at the tail

        System.out.println("Enqueueing : "+ x);
    }


    // assume the queue is non-empty when this method is called, otherwise thro exception
    public int deQueue() throws QueueException 
    {
        if (isEmpty())
        {
            System.out.print("Queue is empty");
        }//checks if the queue is empty.

        int value = head.data; //head.data contains the value at th front of the queue

        head = head.next; //removes the front node. updates head to point to the next node in the queue

        if (isEmpty()){

            tail = null;
        }//it means the queue became empty after removing elements.
        // missing code
        

        System.out.println("Dequeueing : "+ value);

        return value;


    }

    public boolean isEmpty()
    {
        return head == z;
    }



} // end of QueueLL[linked list] class



class QueueCB implements Queue {
    private int q[], back, front;
    private int qmax, size;

 
    public QueueCB() {
        qmax = 4;
        size = front = back = 0;
        q = new int[qmax];
    }

    public void enQueue( int x) throws QueueException  {
        // do it
        // Add an element to the queue (at the back)
        if (size == qmax) {
            throw new QueueException("Queue overflow: Cannot add more elements.");
        }
        q[back] = x;              // Insert element at the back
        back = (back + 1) % qmax; // Move back pointer in a circular manner
        size++;                    // Increase queue size
           
    }
  
    public int deQueue()  throws QueueException 
    {
        // do it
        if (isEmpty()) {
            throw new QueueException("Queue is empty. Cannot dequeue.");
        }
        int value = q[front];      // Get front element
        front = (front + 1) % qmax; // Move front pointer in a circular manner
        size--;                     // Decrease queue size
        return value;

        //return -1; // just so the code runs
  }

    public boolean isEmpty() {
        return size == 0;
    }
}


// here we test both implementations
class QueueTest2 {
    public static void main(String[] arg) {
        Queue q1, q2;
        q1 = new QueueLL();
        q2 = new QueueCB();
        
        System.out.println("Testing QueueLL:");
        for (int i = 1; i <= 5; ++i) {
            try {
                q1.enQueue(i);
                System.out.println("Enqueued: " + i);
            } catch (QueueException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        while (!q1.isEmpty()) {
            try {
                System.out.println("Dequeued: " + q1.deQueue());
            } catch (QueueException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        try {
            q1.deQueue();
        } catch (QueueException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("\nTesting QueueCB:");
        for (int i = 1; i <= 5; ++i) {
            try {
                q2.enQueue(i);
                System.out.println("Enqueued: " + i);
            } catch (QueueException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        while (!q2.isEmpty()) {
            try {
                System.out.println("Dequeued: " + q2.deQueue());
            } catch (QueueException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        try {
            q2.deQueue();
        } catch (QueueException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
        
        // more test code: repeat deQueue() until Queue is empty
        // and deQueue() even when empty to see exceptions working.
     
}

