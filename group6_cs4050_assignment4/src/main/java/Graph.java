import java.util.*;

public class Graph {
    private final int V; //Holds the total amount of vertices in the graph.
    private final Map<Integer, List<Edge>> adj; //Holds the edges for each vertex.

    //Constructor class.
    public Graph(int vertices) {
        this.V = vertices;
        adj = new HashMap<>();
        for (int i = 1; i <= V; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    //Adds an edge to the graph.
    public void addEdge(int u, int v, double w) {
        adj.get(u).add(new Edge(v, w));
        adj.get(v).add(new Edge(u, w));
    }

    //Prints the adjacency list.
    public void printAdjList() {
        System.out.println("Adjacency List:");
        for (int u : adj.keySet()) {
            for (Edge e : adj.get(u)) {
                System.out.println(u + " --(" + e.weight + ")--> " + e.to);
            }
        }
    }

    //Performs Prim's algorithm on the graph. Prints the edges in the minimum spanning tree.

    //Maybe I should keep a local hashmap for edges and add to them over the course of the algorithm?
    //Might prevent my issue with not eliminating certain edges (or maybe I should change the fact that
    //they seem to be two-way and not one-way.
    public void primMST() {
        // TODO: Some edges are not properly being relaxed. Plus it seems that the example
        //  wants some of the edges to point backwards and I guess they don't do that right now?
        //  Example: I fixed 20 and 10 pointing to each other (edge relaxed) but not 6 -> 12.


        //Distances for the vertices. Set vertex 1 to be the start.
        double[] distances = new double[V];
        int[] parents = new int[V];
        boolean[] inMST = new boolean[V];

        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(parents, -1);
        distances[0] = 0;

        Map<Integer, List<Edge>> adjCopy = new HashMap<>(adj);

        //Creates a new Heap (minimum spanning tree) for use as a queue and fills it with placeholders.
        Heap mst = new Heap();
        mst.heap_ini(distances, V);

        //List for printing messages for output.
        List<String> mstEdges = new ArrayList<>();

        //Iterate until the queue is empty
        while(!mst.isEmpty()) {
            //Get the vertex with the minimum key and remove it from the queue.
            int u = mst.min_id();
            mst.delete_min();
            inMST[u-1] = true;

            //For every edge the vertex is connected to...
            for (Edge e : adjCopy.get(u)) {
                //Get the information about the edge.
                int v = e.getTo();
                double weight = e.getWeight();

                //If the edge connected vertex is in the heap and the weight is less than the stored distance for that vertex,
                //store the weight, change the weight of the vertex in the queue, and add the message to be printed about the edge.
                if (mst.in_heap(v) && !inMST[v - 1] && weight < distances[v - 1]) {
                    distances[v - 1] = weight;
                    parents[v - 1] = u;

                    mst.decrease_key(v, weight);

                    mstEdges.add(u + " -> " + v + ". Weight: " + weight);
                }
                else {
                    //Relax (remove) the edge.
                    adjCopy.get(v).remove(e);
                }
            }
        }

        //Print the output of the function.
        System.out.println("MST edges (u -> v. Weight):");
        for (int u = 1; u <= V; u++) {
            System.out.print(u);
            for (int v = 1; v < V; v++) {
                if (parents[v] == u) {
                    System.out.print(" --> (" + (v + 1) + ", " + distances[v] + ")");
                } else if (parents[u - 1] == v + 1) {
                    System.out.print(" --> (" + (v + 1) + ", " + distances[u - 1] + ")");
                }
            }
            System.out.println();
        }
    }

    private static class Edge {
        private final int to;
        private final double weight;
        Edge(int to, double weight) {
            this.to = to;
            this.weight = weight;
        }

        public int getTo() {
            return to;
        }

        public double getWeight() {
            return weight;
        }
    }
}
