import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Driver code for the heap and graph classes with the provided text file.
public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("src/main/java/graph.txt"));
            int n = sc.nextInt();
            Graph graph = new Graph(n);

            while (sc.hasNext()) {
                int i = sc.nextInt();
                int j = sc.nextInt();
                double w = sc.nextDouble();
                graph.addEdge(i, j, w);
            }

            graph.printAdjList();
            graph.primMST();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Graph file could not be found.");
        }
    }
}
