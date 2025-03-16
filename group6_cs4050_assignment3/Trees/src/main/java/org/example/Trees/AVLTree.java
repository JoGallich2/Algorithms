package org.example.Trees;
//Auther: Joseph Gallichio
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> implements Tree<T> , Serializable {
    private Node root;
    private int size;

    @Override
    public void insert(T value) {
        root = insert(root, value);
        size++;
    }

    private Node insert(Node rootNode, T value) {
        if (rootNode == null) {
            return new Node(value);
        }
        if (value.compareTo(rootNode.value) < 0) {
            rootNode.left = insert(rootNode.left, value);
        } else if (value.compareTo(rootNode.value) > 0) {
            rootNode.right = insert(rootNode.right, value);
        }
        return balance(rootNode);
    }

    @Override
    public boolean delete(T value) {
        int originalSize = size;
        root = delete(root, value);
        return size < originalSize;
    }

    private Node delete(Node rootNode, T value) {
        if (rootNode == null) {
            return null;
        }
        if (value.compareTo(rootNode.value) < 0) {
            rootNode.left = delete(rootNode.left, value);
        } else if (value.compareTo(rootNode.value) > 0) {
            rootNode.right = delete(rootNode.right, value);
        } else {
            if (rootNode.left == null) {
                size--;
                return rootNode.right;
            } else if (rootNode.right == null) {
                size--;
                return rootNode.left;
            }
            Node minRight = findMin(rootNode.right);
            rootNode.value = minRight.value;
            rootNode.right = delete(rootNode.right, minRight.value);
        }
        return balance(rootNode);
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Height
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            return node.height;
        }
    }

    // Get Balance Factor
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            return getHeight(node.left) - getHeight(node.right);
        }
    }

    //balance the nodes if needed
    private Node balance(Node node) {
        if(node == null) {
            return null;
        }

        // Update height
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        int balanceFactor = getBalanceFactor(node);

        //Perform rotations as necessary
        if(balanceFactor > 1) {
            if(getBalanceFactor(node.left) < 0){
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        else if(balanceFactor < -1){
            if(getBalanceFactor(node.right) > 0){
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateLeft(Node nodeN) {
        Node nodeC = nodeN.right; // nodeC is the right child of nodeN
        Node T2 = nodeC.left;     // tore the left child of nodeC (which will be moved)

        nodeC.left = nodeN;        // Set nodeN as the left child of nodeC
        nodeN.right = T2;          // Set nodeC's left child (T2) as the new right child of nodeN

        // Update heights
        nodeN.height = Math.max(getHeight(nodeN.left), getHeight(nodeN.right)) + 1;
        nodeC.height = Math.max(getHeight(nodeC.left), getHeight(nodeC.right)) + 1;

        //Return the new root (nodeC)
        return nodeC;
    }

    private Node rotateRight(Node nodeN) {
        Node nodeC = nodeN.left;  // nodeC is the left child of nodeN
        Node T2 = nodeC.right;    // store the right child of nodeC (which will be moved)

        nodeC.right = nodeN;      // Set nodeN as the right child of nodeC
        nodeN.left = T2;          // Set nodeC's right child (T2) as the new left child of nodeN

        // Update heights
        nodeN.height = Math.max(getHeight(nodeN.left), getHeight(nodeN.right)) + 1;
        nodeC.height = Math.max(getHeight(nodeC.left), getHeight(nodeC.right)) + 1;

        // Return the new root (nodeC)
        return nodeC;
    }


    @Override
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node rootNode, T value) {
        if (rootNode == null) {
            return false;
        }
        if (value.compareTo(rootNode.value) == 0) {
            return true;
        } else if (value.compareTo(rootNode.value) < 0) {
            return contains(rootNode.left, value);
        } else {
            return contains(rootNode.right, value);
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(Node rootNode, List<T> result) {
        if (rootNode != null) {
            inorderTraversal(rootNode.left, result);
            result.add(rootNode.value);
            inorderTraversal(rootNode.right, result);
        }
    }

    @Override
    public String type() {
        return "AVL Tree";
    }

    @Override
    public Color color() {
        return Color.BLACK;
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    private class Node implements TreeNode<T> , Serializable{
        T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }

        @Override
        public T getValue() { return value; }

        @Override
        public TreeNode<T> getLeft() { return left; }

        @Override
        public TreeNode<T> getRight() { return right; }

        public String getColor() { return null; }
    }

}