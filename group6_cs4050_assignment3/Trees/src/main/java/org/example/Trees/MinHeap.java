package org.example.Trees;

// Author: Van Grabner
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MinHeap<T extends Comparable<T>> implements Tree<T>, Serializable {
    private Node root;
    private int size;
    public MinHeap() {
        root = null;
        size = 0;
    }

    @Override
    public void insert(T value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
        } else {
            Node parent = getInsertionParent();
            if(parent.left == null) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            newNode.parent = parent;
            bubbleUp(newNode);
        }
        size++;

    }

    @Override
    public boolean delete(T value) {
        Node nodeToDelete = findNode(root, value);
        if (nodeToDelete == null) {
            return false;
        }
        Node lastNode = getLastNode();
        if (nodeToDelete == lastNode) {
            removeLastNode(lastNode);
        } else {
            swapNodes(nodeToDelete, lastNode);
            removeLastNode(lastNode);
            bubbleDown(nodeToDelete);
        }

        size--;
        return true;
    }

    @Override
    public boolean contains(T value) { return contains(root, value);
    }

    private boolean contains(Node node, T value) {
        if (node == null) {
            return false;
        }
        if (value.compareTo(node.value) == 0) {
            return true;
        } else if (value.compareTo(node.value) < 0) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(Node current, List<T> result) {
        if (current != null) {
            inorderTraversal(current.left, result);
            result.add(current.value);
            inorderTraversal(current.right, result);
        }
    }

    private void bubbleUp(Node node) {
        while(node.parent != null && node.value.compareTo(node.parent.value) < 0) {
            swapNodes(node, node.parent);
            node = node.parent;
        }
    }

    private void bubbleDown(Node node) {
        while (node.left != null) {
            Node smallest = node.left;
            if (node.right != null && node.right.value.compareTo(node.left.value) < 0) {
                smallest = node.right;
            }
            if (node.value.compareTo(smallest.value) <= 0) {
                break;
            }
            swapNodes(node, smallest);
            node = smallest;
        }
    }

    private void swapNodes(Node node1, Node node2) {
        T tmp = node1.value;
        node1.value = node2.value;
        node2.value = tmp;
    }

    private Node getInsertionParent() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Node n = queue.poll();
            if (n.left == null || n.right == null) return n;
            queue.add(n.left);
            queue.add(n.right);
        }
        return null;
    }

    private Node getLastNode() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node last = null;
        while(!queue.isEmpty()) {
            last = queue.poll();
            if (last.left != null){
                queue.add(last.left);
            }
            if (last.right != null) {
                queue.add(last.right);
            }
        }
        return last;
    }

    private void removeLastNode(Node node) {
        if (node == root) {
            root = null;
            return;
        }
        Node parent = node.parent;
        if (parent.left == node) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    private Node findNode(Node root, T value) {
        if (root == null) return null;
        if (root.value.equals(value)) {
            return root;
        }
        Node found = findNode(root.left, value);
        if (found != null) {
            return found;
        }
        return findNode(root.right, value);
    }

    @Override
    public String type() { return "MinHeap"; }

    @Override
    public Color color() { return Color.BLACK; }

    public TreeNode<T> getRoot() { return root; }

    private class Node implements TreeNode<T>, Serializable {
        T value;
        Node left, right, parent;

        Node(T value) {
            this.value = value;
            left = right = parent = null;
        }

        @Override
        public T getValue() { return value; }

        @Override
        public TreeNode <T> getLeft() { return left; }

        @Override
        public TreeNode <T> getRight() { return right; }

        public TreeNode<T> getParent() { return parent; }

        public String getColor() { return null; }

    }



}
