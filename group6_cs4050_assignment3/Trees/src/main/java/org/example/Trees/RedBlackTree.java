package org.example.Trees;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> implements Tree<T>, Serializable {
    private Node root;
    private int size;


    private Node find(Node root, T value) {
        if(root == null) {
            return null;
        }
        if(value.compareTo(root.getValue()) == 0) {
            return root;
        }
        if(value.compareTo(root.getValue()) < 0) {
            return find(root.left, value);
        }
        else {
            return find(root.right, value);
        }
    }

    @Override
    public void insert(T value) {
        Node newNode = new Node(value);
        root = insert(root, newNode);
        //If new node is root, just insert as black.
        if(newNode == root) {
            root.isRed = false;
            size++;
        }
        //If parent of new node is black, do nothing.
        else if(newNode.parent != null && !newNode.parent.isRed) {
            size++;
        }
        else {
            redBlackify(newNode);
            size++;
        }
    }

    private Node insert(Node root, Node newNode) {
        if (root == null) {
            return newNode;
        }

        if(newNode.value.compareTo(root.value) < 0) {
            root.left = insert(root.left, newNode);
            root.left.parent = root;
        }
        else if(newNode.value.compareTo(root.value) > 0) {
            root.right = insert(root.right, newNode);
            root.right.parent = root;
        }

        return root;
    }

    private void redBlackify(Node node) {
        Node parent = node.parent;

        //Case 1: node is the root.
        if(root == node) {
            node.isRed = false;
            return;
        }

        //If the parent is black, return;
        if(!parent.isRed) {
            return;
        }

        Node grandparent = parent.parent;

        //Case 2: If grandparent is null, color parent black.
        if(grandparent == null) {
            parent.isRed = false;
            return;
        }

        Node uncle;
        if(grandparent.left == parent) {
            uncle = grandparent.right;
        }
        else {
            uncle = grandparent.left;
        }

        //Case 3: if uncle is red, recolor parent uncle and grandparent.
        if(uncle != null && uncle.isRed) {
            parent.isRed = false;
            grandparent.isRed = true;
            uncle.isRed = false;

            redBlackify(grandparent);
            return;
        }
        //Checks left->right and right->left.
        if(parent == grandparent.left && node == parent.right) {
            rotateLeft(parent);
            parent = node;
            node = parent.left;
        }
        else if (parent == grandparent.right && node == parent.left) {
                rotateRight(parent);
                parent = node;
                node = parent.right;
        }

        //Recolor parent and grandparent.
        parent.isRed = false;
        grandparent.isRed = true;

        //Checks left->left and right->right.
        if (node == parent.left && parent == grandparent.left) {
            rotateRight(grandparent);
        }
        else {
            rotateLeft(grandparent);
        }
    }

    private void rotateLeft(Node node) {

        Node rightChild = node.right;
        node.right = rightChild.left;
        if(rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        }
        else if (node == node.parent.left) {
            node.parent.left = rightChild;
        }
        else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        if(leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        }
        else if (node == node.parent.right) {
            node.parent.right = leftChild;
        }
        else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean delete(T value) {
        if (root == null) {
            return false;
        }

        // Find the node to delete
        Node nodeToDelete = find(root, value);
        if (nodeToDelete == null) {
            return false; // Node not found
        }

        Node replacementNode;
        Node childNode;

        // Case when node has at most one non-null child
        if (nodeToDelete.left == null || nodeToDelete.right == null) {
            replacementNode = nodeToDelete;
        } else {
            // Node has two children, find the in-order successor
            replacementNode = findMin(nodeToDelete.right);
        }

        // Get the child of replacementNode
        if (replacementNode.left != null) {
            childNode = replacementNode.left;
        } else {
            childNode = replacementNode.right;
        }

        // Update parent reference for childNode
        if (childNode != null) {
            childNode.parent = replacementNode.parent;
        }

        // Update replacementNode's parent to point to childNode
        if (replacementNode.parent == null) {
            // replacementNode is the root
            root = childNode;
        } else if (replacementNode == replacementNode.parent.left) {
            replacementNode.parent.left = childNode;
        } else {
            replacementNode.parent.right = childNode;
        }

        // If we're deleting a node with two children, copy the successor's value
        if (replacementNode != nodeToDelete) {
            nodeToDelete.value = replacementNode.value;
        }

        // If we removed a BLACK node, we need to fix violations
        if (!replacementNode.isRed) {
            redBlackifyDelete(childNode, replacementNode.parent);
        }

        size--;
        return true;
    }

    private void redBlackifyDelete(Node node, Node parent) {
        while (node != root && (node == null || !node.isRed)) {
            if (node == null) {
                break;
            }

            Node sibling;

            if (node == parent.left) {
                sibling = parent.right;

                //Case 1: Sibling is red.
                if (sibling.isRed) {
                    sibling.isRed = false;
                    parent.isRed = true;
                    rotateLeft(parent);
                    sibling = parent.right;
                }

                //Case 2: Sibling is black and both its children are black.
                if (!sibling.isRed && !sibling.right.isRed) {
                    sibling.isRed = true;
                    node = parent;
                    parent = node.parent;
                } else {
                    //Case 3: Sibling is black, left child is red, right child is black.
                    if (!sibling.right.isRed) {
                        if (sibling.left != null) {
                            sibling.left.isRed = false;
                        }
                        sibling.isRed = true;
                        rotateRight(sibling);
                        sibling = parent.right;
                    }

                    //Case 4: Sibling is black, right child is red.
                    sibling.isRed = parent.isRed;
                    parent.isRed = false;
                    if (sibling.right != null) {
                        sibling.right.isRed = false;
                    }
                    rotateLeft(parent);
                    node = root; //Breaks loop.
                }
            } else { //Same cases but switch left and right.
                sibling = parent.left;

                //Case 1: Sibling is red.
                if (sibling.isRed) {
                    sibling.isRed = false;
                    parent.isRed = true;
                    rotateRight(parent);
                    sibling = parent.left;
                }

                //Case 2: Sibling is black and both its children are black.
                if (!sibling.right.isRed && !sibling.left.isRed) {
                    sibling.isRed = true;
                    node = parent;
                    parent = node.parent;
                } else {
                    //Case 3: Sibling is black, right child is red, left child is black.
                    if (!sibling.left.isRed) {
                        if (sibling.right != null) {
                            sibling.right.isRed = false;
                        }
                        sibling.isRed = true;
                        rotateLeft(sibling);
                        sibling = parent.left;
                    }

                    //Case 4: Sibling is black, left child is red.
                    sibling.isRed = parent.isRed;
                    parent.isRed = false;
                    if (sibling.left != null) {
                        sibling.left.isRed = false;
                    }
                    rotateRight(parent);
                    node = root; //Breaks loop.
                }
            }
        }

        //Make sure root black.
        if (node != null) {
            node.isRed = false;
        }
    }


    private void fixDoubleBlack(Node node) {
        if(node.isRed) {
            return;
        }
        if(node == root) {
            return;
        }

        Node parent;
        if(node.parent != null) {
            parent = node.parent;
        }
        else {
            parent = node;
        }

        Node sibling;
        if(parent.left != null && parent.left == node) {
            parent.left = null;
            sibling = parent.right;
        }
        else {
            parent.right = null;
            sibling = parent.left;
        }

        //Handle double black.
        //Case 1: sibling is red, children are either black or null. Turns into either case 2, 3, or 4.
        if((sibling != null && sibling.isRed)) {
            parent.isRed = false;
            sibling.isRed = true;
            rotateLeft(parent);

            sibling = parent.left == node ? parent.right : parent.left;
            node = sibling.parent;
        }
        //Case 2: sibling is black and the right child is red.
        if((sibling != null && !sibling.isRed) && (sibling.right != null && sibling.right.isRed)) {
            sibling.isRed = true;
            parent.isRed = false;
            rotateRight(parent);
            return;
        }
        //Case 3: Sibling is black and both children are black
        if((sibling != null && !sibling.isRed) && (sibling.left == null || !sibling.left.isRed) && (sibling.right == null || !sibling.right.isRed)) {
            sibling.isRed = true;
            if(parent.isRed) {
                parent.isRed = false;
                return;
            }
            else {
                fixDoubleBlack(parent);
                return;
            }
        }
        //Case 4: sibling is black, the right child is black, the left child is red.
        if((sibling != null && !sibling.isRed) && (sibling.right == null || !sibling.right.isRed) && (sibling.left != null && sibling.left.isRed)) {
            sibling.isRed = true;
            sibling.left.isRed = false;
            rotateRight(sibling);
            fixDoubleBlack(sibling.parent);
            return;
        }
    }

    @Override
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node node, T value) {
        if(node == null) {
            return false;
        }
        if(value.compareTo(node.getValue()) == 0) {
            return true;
        }
        if(value.compareTo(node.getValue()) < 0) {
            return contains(node.left, value);
        }
        else {
            return contains(node.right, value);
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

    private void inorderTraversal(Node root, List<T> result) {
        if (root != null) {
            inorderTraversal(root.left, result);
            result.add(root.value);
            inorderTraversal(root.right, result);
        }
    }

    public String type() {
        return "RBT";
    }

    public Color color() {
        return Color.BLACK;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    private class Node implements TreeNode<T> , Serializable{
        private T value;
        private Node left, right, parent;
        private boolean isRed;

        Node(T value) {
            this.value = value;
            this.isRed = true;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        @Override
        public T getValue() { return value; }

        public void setIsRed(boolean red) { isRed = red; }
        public boolean isRed() { return isRed; }

        @Override
        public TreeNode<T> getLeft() { return left; }

        @Override
        public TreeNode<T> getRight() { return right; }

        public TreeNode<T> getParent() {
            return parent;
        }

        public String getColor() { if(isRed) {return "RED";} else {return "BLACK";} }
    }
}
