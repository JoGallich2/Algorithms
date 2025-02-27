package assignment.mammals;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/mammals/DictionaryException.java
     */
    @Override
    public MammalRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {         
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws mammals.DictionaryException
     */
    @Override
    public void insert(MammalRecord r) throws DictionaryException {
        DataKey newKey = r.getDataKey();

        //If the root is empty, set the data of the root to be r.
        if (root.isEmpty()) {
            root = new Node(r);
            return;
        }

        //Find a location for the new node.
        Node parent = null;
        Node current = root;
        int comparison;
        while(current != null) {
            comparison = current.getData().getDataKey().compareTo(newKey);
            parent = current;

            if (comparison == 0) {
                throw new DictionaryException("Key already exists in the dictionary.");
            }
            else if (comparison > 0) {
                current = current.getLeftChild();
            }
            else {
                current = current.getRightChild();
            }
        }

        //Creates a new node to be inserted.
        Node newNode = new Node(r);
        if(newKey.compareTo(parent.getData().getDataKey()) < 0) {
            parent.setLeftChild(newNode);
        }
        else {
            parent.setRightChild(newNode);
        }
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws mammals.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        Node current = root;
        Node parent = null;
        int comparison;

        while (current != null) {
            comparison = current.getData().getDataKey().compareTo(k);

            if (comparison == 0) {
                //Found the node.
                break;
            }

            parent = current;

            if (comparison > 0) {
                current = current.getLeftChild();
            }
            else {
                current = current.getRightChild();
            }
        }

        if(current == null) {
            throw new DictionaryException("Key doesn't exist in dictionary.");
        }

        //If node has no children
        if(current.getLeftChild() == null && current.getRightChild() == null) {
            if (current == root) {
                root = null;
            }
            else if (parent.getLeftChild() == current) {
                parent.setLeftChild(null);
            }
            else {
                parent.setRightChild(null);
            }
        }

        //If node has one child
        else if(current.getLeftChild() == null || current.getRightChild() == null) {
            Node child;
            if (current.getLeftChild() != null) {
                child = current.getLeftChild();
            }
            else{
                child = current.getRightChild();
            }

            if(current == root) {
                root = child;
            }
            else if(parent.getLeftChild() == current) {
                parent.setLeftChild(child);
            }
            else {
                parent.setRightChild(child);
            }
        }
        //Node has two children.
        else {
            Node successor = current.getRightChild();
            while (successor.getLeftChild() != null) {
                successor = successor.getLeftChild();
            }
            current.setData(successor.getData());
            remove(successor.getData().getDataKey());
        }
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws mammals.DictionaryException
     */
    @Override
    public MammalRecord successor(DataKey k) throws DictionaryException {
        Node current = root;
        Node successor = null;

        while (current != null) {
            int comparison = current.getData().getDataKey().compareTo(k);

            if (comparison > 0) {
                successor = current;
                current = current.getLeftChild();
            }
            else {
                current = current.getRightChild();
            }
        }

        if(successor == null) {
            throw new DictionaryException("No successor found for given key.");
        }

        return successor.getData();
    }

   
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws mammals.DictionaryException
     */
    @Override
    public MammalRecord predecessor(DataKey k) throws DictionaryException {
        Node current = root;
        Node predecessor = null;

        while (current != null) {
            int comparison = current.getData().getDataKey().compareTo(k);

            if (comparison < 0) {
                predecessor = current;
                current = current.getRightChild();
            }
            else {
                current = current.getLeftChild();
            }
        }

        if(predecessor == null) {
            throw new DictionaryException("No predecessor found for given key.");
        }

        return predecessor.getData();
    }

    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public MammalRecord smallest() throws DictionaryException {
        if(root == null) {
            throw new DictionaryException("Dictionary is empty.");
        }

        Node current = root;

        while(current.getLeftChild() != null) {
            current = current.getLeftChild();
        }

        return current.getData();
    }

    /*
	 * Returns the record with largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public MammalRecord largest() throws DictionaryException {
        if(root == null) {
            throw new DictionaryException("Dictionary is empty.");
        }

        Node current = root;

        while(current.getRightChild() != null) {
            current = current.getRightChild();
        }

        return current.getData();
    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
