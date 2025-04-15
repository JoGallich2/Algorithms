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
    }

    private static class Edge {
        int to;
        double weight;
        Edge(int to, double weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}
