// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

// author : C23365646 Damisola Talabi

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

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
            
            System.out.println("Edge " + u + "--(" + wgt + ")--" + v);   

           
            
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
    }
   
    
    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + v + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + n.vert + " | " + n.wgt + "| ->");    
        }
        System.out.println("");
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
    
        while (!h.isEmpty()) {
            u = h.remove();
            for (t = adj[u]; t != z; t = t.next) {
                v = t.vert;
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
    }
    // Display method for SPT
    public void showSPT(int[] dist, int[] parent, int s) {
        System.out.println("\nShortest Path Tree from source: " + s);
        System.out.println("Vertex\tParent\tDistance");

        // Loop through results
        for (int v = 1; v <= V; v++) {
            System.out.printf("  %d\t  %d\t   %s\n", v, (v == s) ? '-' : parent[v], (dist[v] == Integer.MAX_VALUE) ? "∞" : dist[v]);
            // parent[v]means that if there is no path, print '-' instead
        }
    } // End Method    

}//END CLASS

public class DijkstraCA {
    public static void main(String[] args) throws IOException
    {
        int s = 2;
        String fname = "worldGraph.txt";               

        Graph g = new Graph(fname);
       
        g.display();

       g.SPT_Dijkstra(s);               
    }
}//end main
