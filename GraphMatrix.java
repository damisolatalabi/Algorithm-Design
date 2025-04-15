
// Simple weighted graph representation 
// Uses an Adjacency Matrix, suitable for dense graphs

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

enum C {White, Grey, Black};

class GraphMatrix 
{
    // V = number of vertices
    // E = number of edges
    // adj[ ][ ] is the adjacency matrix
    private int V, E;
    private int[][] adj;

    // used for traversing graph to mark vertices already visited
    private C[] colour;
    private int time;
    
    // for storing the traversal tree and ditance from starting vertex
    private int[] parent, d, f ;
   
   
    // default constructor
    public GraphMatrix(String graphFile)  throws IOException
    {
        int u, v;
        int e, wgt;
       
		FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);	          
        
        String splits = " +";  // multiple whitespace as delimiter
         // Read the first line containing number of vertices and edges
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
		    
		V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);

        // create adjacency matrix, initialised to 0's        
        adj = new int[V+1][V+1];        
        colour = new C[V+1];
        parent = new int[V+1];
        d = new int[V+1];
        f = new int[V+1];
        
        // read the edges
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));    
            //missing code 
            // Read each edge from the file
            for (e = 1; e <= E; ++e) {
                line = reader.readLine();
                parts = line.split(splits);
                u = Integer.parseInt(parts[0]); // First vertex
                v = Integer.parseInt(parts[1]); // Second vertex
                wgt = Integer.parseInt(parts[2]); // Edge weight
                
                // Print the edge being read
                System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));

                // Store the weight in the adjacency matrix
                adj[u][v] = wgt;
                adj[v][u] = wgt;  // Assuming an undirected graph
            }	       
        }
    }
	// convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
	
    // method to display the graph representation
    public void display() {
        int u,v;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + v + "] = ");
            for(u=1; u<=V; ++u) 
                System.out.print("  " + adj[u][v]);
        }    
        System.out.println("");
    }


    // method to initialise Depth First Traversal of Graph
    // Assuming graph is connected
    public void DF( int s) 
    {     
        int v;
        for(v=1; v<=V; ++v) {
            colour[v] = C.White;
            parent[v] = 0;        
        }
        
        System.out.print("\nDepth First Graph Traversal\n");
        System.out.println("Starting with Vertex " + toChar(s));
        
        time = 0;
        dfVisit(s);                      
        
        System.out.print("\n\n");
    }


    // Recursive Depth First Traversal for adjacency matrix
    private void dfVisit( int v)
    {
        int u;
        ++time;
        d[v] = time;
        colour[v] = C.Grey;
        
        System.out.print("\n  DF just visited vertex " + toChar(v) + " along edge " + 
            toChar(parent[v]) + "--" + toChar(v) );
        
        // process all the vertices u connected to vertex v
       // lots of missing code
       // Explore all adjacent vertices
        for (u = 1; u <= V; ++u) {
            if (adj[v][u] > 0 && colour[u] == C.White) {  // Check for unvisited connected node
                parent[u] = v;
                dfVisit(u); // Recursive DFS call
            }
        }

        colour[v] = C.Black; // Mark as fully explored
        ++time;
        f[v] = time; // Record finishing time
    
       
    }
    
    public void BF(int s) {
        //lots of missing code
        Queue<Integer> queue = new LinkedList<>();
    
    // Initialize all vertices as unvisited (White) and set distances to "infinity"
    Arrays.fill(colour, C.White);
    Arrays.fill(parent, 0);
    Arrays.fill(d, Integer.MAX_VALUE);

    System.out.println("\nBreadth-First Graph Traversal");
    System.out.println("Starting with Vertex " + toChar(s));

    // Start BFS from vertex s
    colour[s] = C.Grey;  // Mark starting node as "visiting"
    d[s] = 0;  // Distance from itself is 0
    queue.add(s);

    while (!queue.isEmpty()) {
        int v = queue.poll(); // Dequeue a vertex
        System.out.print(" " + toChar(v));

        // Explore adjacent vertices
        for (int u = 1; u <= V; u++) {
            if (adj[v][u] > 0 && colour[u] == C.White) {  // If connected and unvisited
                colour[u] = C.Grey;
                d[u] = d[v] + 1;
                parent[u] = v;
                queue.add(u);
            }
        }
        colour[v] = C.Black; // Mark as fully explored
    }
    System.out.println(); // New line for readability

    }

    public static void main(String[] args) throws IOException
    {
        int s = 4; //STARTING VERTEX
        String fname = "wGraph3.txt";               

        GraphMatrix g = new GraphMatrix(fname);
       
        g.display();
        
        g.DF(s);

        g.BF(s);
        
    }

}

