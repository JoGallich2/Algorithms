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
        // TODO: Write the program to implement Prim MST

        //Distances for the vertices. Set vertex 1 to be the start.
        double[] distances = new double[V];
        Arrays.fill(distances, Double.MAX_VALUE);
        distances[1 - 1] = 0;

        //Creates a new Heap (minimum spanning tree) and fills it with placeholders.
        Heap mst = new Heap();
        mst.heap_ini(distances, V);

        //Keep track of vertex parents.
        int[] parent = new int[V];
        Arrays.fill(parent, -1);


        while(!mst.isEmpty()) {
            int u = mst.min_id() + 1;
            mst.delete_min();

            for (Edge e : adj.get(u)) {
                int v = e.getTo();
                double weight = e.getWeight();

                if(mst.in_heap(v) && weight < distances[v - 1]) {
                    distances[v - 1] = weight;
                    parent[v] = u;

                    mst.decrease_key(v, weight);
                }
            }
        }

        System.out.println("MST edges -> (u -> v. Weight):");
        for(int i = 1; i <= V; i++) {
            System.out.println(parent[i-1] + " -> " + i + ". Weight = " + distances[i-1]);
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
