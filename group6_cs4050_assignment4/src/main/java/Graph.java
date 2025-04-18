import java.util.*;

public class Graph {
    private final int V;
    private final Map<Integer, List<Edge>> adj;

    public Graph(int vertices) {
        this.V = vertices;
        adj = new HashMap<>();
        for (int i = 1; i <= V; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, double w) {
        adj.get(u).add(new Edge(v, w));
        adj.get(v).add(new Edge(u, w));
    }

    public void printAdjList() {
        System.out.println("Adjacency List:");
        for (int u : adj.keySet()) {
            for (Edge e : adj.get(u)) {
                System.out.println(u + " --(" + e.weight + ")--> " + e.to);
            }
        }
    }

    public void primMST() {
        //Distances for the vertices. Set vertex 1 to be the start.
        double[] distances = new double[V];
        Arrays.fill(distances, Double.MAX_VALUE);
        distances[0] = 0;

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

            //For every edge the vertex is connected to...
            for (Edge e : adj.get(u)) {
                //Get the information about the edge.
                int v = e.getTo();
                double weight = e.getWeight();

                //If the edge connected vertex is in the heap and the weight is less than the stored distance for that vertex,
                //store the weight, change the weight of the vertex in the queue, and add the message to be printed about the edge.
                if(mst.in_heap(v) && weight < distances[v - 1]) {
                    distances[v - 1] = weight;

                    mst.decrease_key(v, weight);

                    mstEdges.add(u + " -> " + v + ". Weight: " + weight);
                }
            }
        }

        //Print the output of the function.
        System.out.println("MST edges (u -> v. Weight):");
        for(String s : mstEdges) {
            System.out.println(s);
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
