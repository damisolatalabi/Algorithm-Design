// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

// author : C23365646 Damisola Talabi

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Heap
{
    private int[] a;	   // heap array
    private int[] hPos;	   // hPos[h[k]] == k
    private int[] dist;    // dist[v] = priority of v

    private int N;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array
    public Heap(int maxSize, int[] _dist, int[] _hPos) 
    {
        N = 0;
        a = new int[maxSize + 1];
        dist = _dist;
        hPos = _hPos;
    }


    public boolean isEmpty() 
    {
        return N == 0;
    }


    public void siftUp( int k) 
    {
        int v = a[k];

        // code yourself
        // must use hPos[] and dist[] arrays
        while (k > 1 && dist[v] < dist[a[k/2]]) {
            a[k] = a[k/2];
            hPos[a[k]] = k;
            k = k/2;
        }
        a[k] = v;
        hPos[v] = k;
    }


    public void siftDown( int k) 
    {
        int v, j;
       
        v = a[k];  
        
        // code yourself 
        // must use hPos[] and dist[] arrays
        while (2*k <= N) {
            j = 2*k;
            if (j < N && dist[a[j]] > dist[a[j+1]]) j++;
            if (dist[v] <= dist[a[j]]) break;
            a[k] = a[j];
            hPos[a[k]] = k;
            k = j;
        }
        a[k] = v;
        hPos[v] = k;
    }


    public void insert( int x) 
    {
        a[++N] = x;
        siftUp( N);
    }


    public int remove() 
    {   
        int v = a[1];
        hPos[v] = 0; // v is no longer in heap
        a[N+1] = 0;  // put null node into empty spot
        
        a[1] = a[N--];
        siftDown(1);
        
        return v;
    }

}

class Graph {
    class Node {
        public int vert;
        public int wgt;
        public Node next;
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private int[] mst;
    
    // used for traversing graph
    private int[] visited; //tracks visited vertices
    private int id;
    
    
    // default constructor
    public Graph(String graphFile)  throws IOException
    {
        int u, v;
        int e, wgt;
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
        
        // create adjacency lists, initialised to sentinel node z       
        adj = new Node[V+1];        
        for(v = 1; v <= V; ++v)
        {
            adj[v] = z;               
        }
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

           
            
            // write code to put edge into adjacency matrix     
            // Insert edge u->v
            t = new Node(); 
            t.vert = v; 
            t.wgt = wgt; 
            t.next = adj[u]; 
            adj[u] = t;

            
            // Insert second edge v->u (undirected graph)
            t = new Node();
            t.vert = u;
            t.wgt = wgt;
            t.next = adj[v];
            adj[v] = t;
                        
        }	       
    }// END CONSTRUCTOR
   
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

    public void DF(int startVertex){
        visited = new int [V + 1];// Initialize visited array (indices 1 to V)
        id = 0;                   // Reset visitation counter
        System.out.println("\nDFS starting from " + toChar(startVertex));
        dfVisit(startVertex);     // Begin recursion
    }
    
    // Private recursive DFS helper
    private void dfVisit(int currentVertex) {
        visited[currentVertex] = ++id; // Mark as visited
        System.out.println("  Visited " + toChar(currentVertex) + " (Order #" + id + ")");
        
        // Explore all neighbors
        for (Node neighbor = adj[currentVertex]; neighbor != z; neighbor = neighbor.next) {
            if (visited[neighbor.vert] == 0) {  // If neighbor is unvisited
                dfVisit(neighbor.vert);         // Recurse!
            }
        }
    }
    // breathfrist function
    public void breadthFirst(int s) {
        visited = new int[V+1];
        Queue<Integer> q = new LinkedList<>();
        id = 0;
        
        q.add(s);
        visited[s] = ++id;
        
        System.out.println("\nBFS starting from " + toChar(s));
        
        while (!q.isEmpty()) {
            int v = q.remove();
            System.out.println("Visited " + toChar(v) + " (order: " + visited[v] + ")");
            // Explore all neighbors
            for (Node t = adj[v]; t != z; t = t.next) {
                if (visited[t.vert] == 0) {
                    q.add(t.vert);
                    visited[t.vert] = ++id;
                }
            }// End for loop
        }
    }


    public void MST_Prim(int s)
    {
        int v, u;
        int wgt_sum = 0;
        int[] dist = new int[V+1];    // Minimum edge weight to connect to MST
        int[] parent = new int[V+1];  // Parent of each vertex in MST
        int[] hPos = new int[V+1];    // Position of vertex in heap
        Node t;

        // Initialize
        for (v = 1; v <= V; v++) {
            dist[v] = Integer.MAX_VALUE;
            parent[v] = 0;
            hPos[v] = 0;
        }
        
        dist[s] = 0;
        
        Heap h =  new Heap(V, dist, hPos);
        h.insert(s);
        // Prim's algorithm
        // Initialize the heap with the starting vertex
       System.out.println("\nPrim's MST Construction Trace:");
        while (!h.isEmpty()) 
        {
            u = h.remove();  // Extract vertex with minimum key
                // Only add vertices to weight sum
            
                wgt_sum += dist[u]; // Add weight of edge to MST
                 // Mark the vertex as included in the MST by negating its dist value
                dist[u] = -dist[u];
                System.out.println("  Visited " + toChar(u) + " (dist: " + -dist[u] + ")"); // Show current vertex and distance
            
            // Update keys of adjacent vertices
            for (t = adj[u]; t != z; t = t.next) 
            {
                v = t.vert;
                System.out.println("    Checking edge " + toChar(u) + " --(" + t.wgt + ")--> " + toChar(v));// Show edge being checked
                // Check if the edge is valid and if it improves the key
                if (t.wgt < dist[v]) 
                {  // Found better connection to v
                    dist[v] = t.wgt;
                    parent[v] = u;
                    if (hPos[v] == 0) 
                    {
                        h.insert(v);  // New vertex
                    } else 
                    {
                        h.siftUp(hPos[v]);  // Update existing vertex
                    }
                }
            }
        }
        mst = parent;  // Store result for showMST()
        System.out.print("\n\nWeight of MST = " + wgt_sum + "\n");
        showMST(); 

                  		
    }
    
    public void showMST()
    {
            System.out.print("\n\nMinimum Spanning tree parent array is:\n");
            for(int v = 1; v <= V; ++v)
            {
                if (mst[v] != 0) 
                { // Skip root
                    System.out.println(toChar(v) + " -> " + toChar(mst[v]) + " (weight: " + getEdgeWeight(mst[v], v) + ")");
                    System.out.println("");
                }
            }
               
    }
    

    private int getEdgeWeight(int u, int v) {
        for (Node t = adj[u]; t != z; t = t.next) {
            if (t.vert == v) {
                return t.wgt;
            }
        }
        return 0; // Return 0 if edge not found (shouldn't happen in MST)
    }

    public void SPT_Dijkstra(int s) 
    {
        int v, u;
        int[] dist = new int[V+1];
        int[] parent = new int[V+1];
        int[] hPos = new int[V+1];
        Node t;
    
        // Initialize
        for (v = 1; v <= V; v++) {
            dist[v] = Integer.MAX_VALUE;
            parent[v] = 0;
            hPos[v] = 0;
        }
    
        dist[s] = 0;
        Heap h = new Heap(V, dist, hPos);
        h.insert(s);
    
        System.out.println("\nDijkstra's SPT Construction Trace:");
        while (!h.isEmpty()) {
            u = h.remove();
            System.out.println("  Visited " + toChar(u) + " (dist: " + dist[u] + ")"); // Show current vertex and distance
            for (t = adj[u]; t != z; t = t.next) {
                v = t.vert;
                int wgt = t.wgt;
                System.out.println("    Checking edge " + toChar(u) + " --(" + wgt + ")--> " + toChar(v));

                if (dist[u] + t.wgt < dist[v]) {
                    dist[v] = dist[u] + t.wgt;
                    parent[v] = u;
                    if (hPos[v] == 0) {
                        h.insert(v);
                    } else {
                        h.siftUp(hPos[v]);
                    }
                }
            }
        }
         // Call function to show shortest path
         showSPT(dist, parent, s);
    }// End method

    // Display method for SPT
    public void showSPT(int[] dist, int[] parent, int s) {
        System.out.println("\nShortest Path Tree from source: " + toChar(s));
        System.out.println("Vertex\tParent\tDistance");

        // Loop through results
        for (int v = 1; v <= V; v++) {
            System.out.printf("  %c\t  %c\t   %s\n", toChar(v), 
                            (v == s) ? '-' : toChar(parent[v]), 
                            (dist[v] == Integer.MAX_VALUE) ? "∞" : dist[v]);
        }
    }    

}//END CLASS

public class GraphLists {
    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the graph file: ");
        String fname = sc.nextLine(); // Read the file name from user input 

        //create a graph object using the file name
        Graph g = new Graph(fname);
       
        // Set starting vertex for BFS to 'L' (12)
        int bfsStart = 12; // 'L' is vertex 12 (A=1, B=2, ..., L=12)

        System.out.print("Enter the starting vertex for DFS and others: ");
        int s = sc.nextInt(); // Read the starting vertex from user input

        //close the scanner
        sc.close();

        g.display();

        g.DF(s);
        g.breadthFirst(bfsStart); // Now starting BFS from 'L'
        g.MST_Prim(s);   
        g.SPT_Dijkstra(s);               
    }//end main
}//end class GraphLists