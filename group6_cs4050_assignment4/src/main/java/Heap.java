import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heap {

    private List<Node> heap;
    private Map<Integer, Integer> positionMap;

    public Heap(){
        heap = new ArrayList<Node>();
        positionMap = new HashMap<Integer, Integer>();
    }


    public void heap_ini(double[] keys, int n){
        // TODO: Initializes a heap with the array keys of n elements indexed from 1 to n, where
        //  key[i] is the key of the element whose id is i.
    }

    public boolean in_heap(int id){
        // TODO: returns true if the element whose id is id is in the heap;

        return false;
    }

    public double min_key(){
        // TODO: returns the minimum key of the heap;

        return 0;
    }

    public int min_id(){
        // TODO: returns the id of the element with minimum key in the heap;

        return 0;
    }

    public double key(int id){
        // TODO: returns the key of the element whose id is id in the heap;

        return 0;
    }

    public void delete_min() {
        // TODO: deletes the element with minimum key from the heap;
    }

    public void decrease_key(int id, double new_key){
        // TODO: sets the key of the element whose id is id to new_key if its current key
        //    is greater than new_key.
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
