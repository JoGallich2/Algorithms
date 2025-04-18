import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heap {
    private List<Node> heap;

    public Heap(){
        heap = new ArrayList<Node>();
    }


    public void heap_ini(double[] keys, int n){
        // TODO: Initializes a heap with the array keys of n elements indexed from 1 to n, where
        //  key[i] is the key of the element whose id is i.
        for(int i = 0; i < n; i++){
            Node node = new Node(i + 1, keys[i]);
            heap.add(node);
        }
    }

    public boolean in_heap(int id){
        // TODO: returns true if the element whose id is id is in the heap;
        for(Node n : heap){
            if(n.key == id){
                return true;
            }
        }
        return false;
    }

    public double min_key(){
        // TODO: returns the minimum key of the heap;
        double minKey = Double.MAX_VALUE;
        for(Node n : heap){
            if(n.key < minKey){
                minKey = n.key;
            }
        }
        return minKey;
    }

    public int min_id(){
        // TODO: returns the id of the element with minimum key in the heap;
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
        // TODO: returns the key of the element whose id is id in the heap;
        for(Node n : heap){
            if(n.id == id){
                return n.key;
            }
        }
        return -1;
    }

    public void delete_min() {
        // TODO: deletes the element with minimum key from the heap;
        int minId = min_id();
        heap.removeIf(n -> n.id == minId);
    }

    public void decrease_key(int id, double new_key){
        // TODO: sets the key of the element whose id is id to new_key if its current key
        //    is greater than new_key.
        for(Node n : heap){
            if(n.id == id){
                n.key = new_key;
            }
        }
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private static class Node {
        int id;
        double key;

        Node(int id, double key) {
            this.id = id;
            this.key = key;
        }
    }
}
