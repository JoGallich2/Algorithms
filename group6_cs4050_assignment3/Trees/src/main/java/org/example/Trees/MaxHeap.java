package org.example.Trees;

// Author: Joseph Gallichio
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaxHeap<T extends Comparable<T>> implements Tree<T>, Serializable {
    private Node root;
    private int size;

    public MaxHeap() {
        root = null;
        size = 0;
    }

    /** Inserts a new value into the heap */
    @Override
    public void insert(T value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
        } else {
            Node parent = getInsertionParent();
            if (parent.left == null) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            newNode.parent = parent;
            bubbleUp(newNode);
        }
        size++;
    }

    /** Deletes a value from the heap */
    @Override
    public boolean delete(T value) {
        Node nodeToDelete = findNode(root, value);
        if (nodeToDelete == null) {
            return false; // Value not found
        }

        Node lastNode = getLastNode();
        if (nodeToDelete == lastNode) {
            removeLastNode(lastNode);
        } else {
            swapNodes(nodeToDelete, lastNode);
            removeLastNode(lastNode);
            bubbleDown(nodeToDelete); // Restore heap property
        }

        size--;
        return true;
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

    // Helper Functions for Heap

    /** Heapify-Up: Moves new node up if necessary */
    private void bubbleUp(Node node) {
        while (node.parent != null && node.value.compareTo(node.parent.value) > 0) {
            swapNodes(node, node.parent);
            node = node.parent;
        }
    }

    /** Heapify-Down: Moves root down if necessary */
    private void bubbleDown(Node node) {
        while (node.left != null) {
            Node largestChild = node.left;
            if (node.right != null && node.right.value.compareTo(node.left.value) > 0) {
                largestChild = node.right;
            }
            if (node.value.compareTo(largestChild.value) >= 0) break;
            swapNodes(node, largestChild);
            node = largestChild;
        }
    }

    /** Swaps two node values */
    private void swapNodes(Node a, Node b) {
        T temp = a.value;
        a.value = b.value;
        b.value = temp;
    }

    /** Finds the parent where the next node should be inserted */
    private Node getInsertionParent() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left == null || node.right == null) return node;
            queue.add(node.left);
            queue.add(node.right);
        }
        return null;
    }

    /** Finds the last inserted node */
    private Node getLastNode() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node lastNode = null;
        while (!queue.isEmpty()) {
            lastNode = queue.poll();
            if (lastNode.left != null) queue.add(lastNode.left);
            if (lastNode.right != null) queue.add(lastNode.right);
        }
        return lastNode;
    }

    /** Removes the last inserted node */
    private void removeLastNode(Node lastNode) {
        if (lastNode == root) {
            root = null;
            return;
        }

        Node parent = lastNode.parent;
        if (parent.left == lastNode) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    /** Finds a node with the given value */
    private Node findNode(Node node, T value) {
        if (node == null) return null;
        if (node.value.equals(value)) return node;

        Node found = findNode(node.left, value);
        if (found != null) return found;
        return findNode(node.right, value);
    }

    @Override
    public String type() {
        return "Max Heap";
    }

    @Override
    public Color color() {
        return Color.BLACK;
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    private class Node implements TreeNode<T>, Serializable {
        T value;
        Node left, right, parent;

        Node(T value) {
            this.value = value;
            left = right = parent = null;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public TreeNode<T> getLeft() {
            return left;
        }

        @Override
        public TreeNode<T> getRight() {
            return right;
        }

        public TreeNode<T> getParent() {
            return parent;
        }

        public String getColor() {
            return null;
        }
    }
}
