
// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*;

class GraphLists2 {
    class Node {
        public int vert;
        public int wgt;
        public Node next;
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj; //adj[] array of adjacency linked list
    private Node z;
    
    // used for traversing graph
    private int[] visited; //tracks the visited nodes for  DFS
    private int id;
    
    
    // default constructor
    public GraphLists(String graphFile)  throws IOException
    {
        int u = 0, v = 0;
        int e, wgt = 0;
        Node t;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z //IF COMMENTED OUT IT MEANS THE EDGES CANNOT BE STORED
        visited = new int[V+1];
        adj = new Node[V + 1];
        for(v = 1; v <= V; ++v){
           adj[v] = z;               
        }
        
        // read the edges
        System.out.println("Reading edges from text file");
        
        // missing for loop here
        for (e =1 ; e<= E ;e++){
            line = reader.readLine();
            parts= line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]);
            wgt = Integer.parseInt(parts[2]); 
        }
        System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));

        // Insert edge (u -> v)
        t = new Node();
        t.vert = v;
        t.wgt = wgt;
        t.next = adj[u];  
        adj[u] = t;  

        // Insert edge (v -> u) for undirected graph
        t = new Node();
        t.vert = u;
        t.wgt = wgt;
        t.next = adj[v];  
        adj[v] = t;

    }
   
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");    
        }
        System.out.println("");
    }


    // method to initialise Depth First Traversal of Graph
    public void DF( int s) 
    {
        for (int v = 1; v <= V; v++)
        visited[v] = 0; // Mark all vertices as unvisited

        System.out.println("Depth First Search from vertex " + toChar(s));
        dfVisit(0, s);
    }


    // Recursive Depth First Traversal for adjacency matrix
    private void dfVisit( int prev, int v)
    {   
        visited[v] = ++id;  // Mark current vertex as visited
        System.out.println("Visited " + toChar(v) + " from " + toChar(prev));

        for (Node n = adj[v]; n != z; n = n.next) {
            if (visited[n.vert] == 0)  // If unvisited, continue DFS
                dfVisit(v, n.vert);
        }
        
      
    }


    public static void main(String[] args) throws IOException
    {
        int s = 4; // Start DFS from vertex 4
        String fname = "wGraph3.txt";               

        GraphLists g = new GraphLists(fname);
       
        g.display();
        
        g.DF(s);
    }

}

