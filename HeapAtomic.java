// Lab test with Heap where heap values refer to atoms in an array
// skeleton code

import java.io.*;    
 
class Atom {
        public int num, wgt;
        public String sym;

        public Atom(String s, int n, int w) {
            sym = s;
            num = n;
            wgt = w;        
        }
        
        public void show() {
            System.out.print("Atom " + sym + " atomic weight " + wgt + " and number " + num + "\n" ) ;
        }
}


class HeapAtomic
{    
	private int[] a;  // heap array
    int N, Nmax;
    Atom[] atom;


    // Bottom up heap construction
    public HeapAtomic(int _N, Atom[] _atom) {
        Nmax = 200;
        N  = _N;
        a = new int[Nmax+1];
        atom = _atom;
        int i;
        
        // initially just fill heap array with 
        // indices of atom array.
        for (i=0; i <= N; ++i) 
            a[i] = i;
           
        // Then convert a[] into a heap
        // from the bottom up. 
        for(i = N/2; i > 0; --i)
           // missing code
            siftDown(i);
        
    }


    private void siftDown(int k) {
        int ai = a[k];  // ai stands for atom index
        int j = 2 * k;
    
        while (j <= N) {
            if (j < N && atom[a[j + 1]].wgt > atom[a[j]].wgt) {
                j++;
            }
            if (atom[ai].wgt >= atom[a[j]].wgt) {
                break;
            }
            a[k] = a[j];
            k = j;
            j = 2 * k;
        }
        a[k] = ai;
    }//completed
    

    private void siftUp(int k) {
        int ai = a[k];
    
        while (k > 1 && atom[ai].wgt > atom[a[k / 2]].wgt) {
            a[k] = a[k / 2];
            k = k / 2;
        }
        a[k] = ai;
    }//COMPLETED
    
    
     
    public int remove() {
        int i = a[1];
        a[1] = a[N--];
        siftDown(1);
        return i;
    }

    
    public void insert(int ai) {
        a[++N] = ai;
        siftUp(N);
    }

    public int getSize(){
        return N;
    }
    
    // display heap atoms and their atomic weights
    void display() {
        System.out.println("\nThe tree structure of the heaps is: ");
        System.out.println( atom[a[1]].sym + "(" + atom[a[1]].wgt + ")" );
        for(int i = 1; i<= N/2; i = i * 2) {
            for(int j = 2*i; j < 4*i && j <= N; ++j)
                System.out.print( atom[a[j]].sym + "(" + atom[a[j]].wgt + ")  ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("My name is Damisola Talabi and my student ID is C23365646");
        
        Atom[] atom = new Atom[100];

        atom[0] = new Atom("", 0, 0);  // a dummy atom
        atom[1] = new Atom("Be",4,9);   atom[2] = new Atom("Ti",22,48); 
        atom[3] = new Atom("Fe",26,56); atom[4] = new Atom("H",1,1);
        atom[5] = new Atom("Li",3,7);   atom[6] = new Atom("Mg",12,24);
        atom[7] = new Atom("N",7,14);   atom[8] = new Atom("C",6,12);
        atom[9] = new Atom("F",9,19);   atom[10] = new Atom("O",8,16);

        HeapAtomic heap = new HeapAtomic(10, atom);
        heap.display();
        System.out.println("Heap size = " + heap.getSize());

        int a = heap.remove();
        System.out.println("Heap size after removal = " + heap.getSize());
        atom[a].show();
        heap.display();
        
        
        Atom gold = new Atom("Au", 79, 197); 
        atom[11] = gold;
        heap.insert(11);
        System.out.println("Heap size after insert = " + heap.getSize());
        heap.display();
        
                
        System.out.println();
        a = heap.remove();
        atom[a].show();        
    }

}    


