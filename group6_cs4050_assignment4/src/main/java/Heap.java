import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heap {
    private List<Node> heap; //To hold the heap.

    //Constructor for the heap class.
    public Heap(){
        heap = new ArrayList<Node>();
    }


    public void heap_ini(double[] keys, int n){
        // Initializes a heap with the array keys of n elements indexed from 1 to n, where
        // key[i] is the key of the element whose id is i.
        for(int i = 0; i < n; i++){
            Node node = new Node(i + 1, keys[i]);
            heap.add(node);
        }
    }

    public boolean in_heap(int id){
        // Returns true if the element whose id is id is in the heap;
        for(Node n : heap){
            if(n.id == id){
                return true;
            }
        }
        return false;
    }

    public double min_key(){
        // Returns the minimum key of the heap;
        double minKey = Double.MAX_VALUE;
        for(Node n : heap){
            if(n.key < minKey){
                minKey = n.key;
            }
        }
        return minKey;
    }

    public int min_id(){
        // Returns the id of the element with minimum key in the heap;
        double minKey = Double.MAX_VALUE;
        int minIndex = -1;
        for(Node n : heap){
            if(n.key < minKey){
                minKey = n.key;
                minIndex = n.id;
            }
        }
        return minIndex;
    }

    public double key(int id){
        // Returns the key of the element whose id is id in the heap;
        for(Node n : heap){
            if(n.id == id){
                return n.key;
            }
        }
        return -1;
    }

    public void delete_min() {
        // Deletes the element with minimum key from the heap;
        int minId = min_id();
        heap.removeIf(n -> n.id == minId);
    }

    public void decrease_key(int id, double new_key){
        // Sets the key of the element whose id is id to new_key if its current key
        // is greater than new_key.
        for(Node n : heap){
            if(n.id == id && n.key > new_key){
                n.key = new_key;
            }
        }
    }

    // Returns whether the heap is empty.
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Prints the heap.
    public void printHeap() {
        for(Node n : heap){
            System.out.println(n.id + " " + n.key);
        }
    }

    // Inner node class for keeping information.
    private static class Node {
        int id;
        double key;

        Node(int id, double key) {
            this.id = id;
            this.key = key;
        }
    }
}
