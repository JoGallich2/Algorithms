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

    public void insert(T value) {
        Node newNode = new Node(value);
        root = insert(root, newNode);
        redBlackify(newNode);
        size++;
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

        //Case 1: parent is null.
        if(parent == null) {
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
        }
        //Parent is left child of grandparent.
        else if(parent == grandparent.left) {
            //Case 4.1: Uncle is black and node is left->right of grandparent.
            if(node == parent.right) {
                rotateLeft(parent);

                parent = node;
            }

            //Case 5.1: Uncle black and node is left->left of grandparent.
            rotateRight(grandparent);

            //Recolor parent and grandparent.
            parent.isRed = false;
            grandparent.isRed = true;
        }
        //Parent is the right child of grandparent.
        else {
            //Case 4.2: Uncle is black and node is right->left of grandparent.
            if (node == parent.left) {
                rotateRight(parent);

                parent = node;
            }

            //Case 5.2: Uncle is black and node is right->right of grandparent.
            rotateLeft(grandparent);

            parent.isRed = false;
            grandparent.isRed = true;
        }

        root.isRed = false;
    }

    private void rotateLeft(Node node) {
        //First implementation
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
        //First implementation
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

    public boolean delete(T value) {
        //First implementation
        Node node = find(root, value);
        if(node == null) {
            return false;
        }

        Node nodeToFix = deleteNode(node);
        if(nodeToFix != null) {
            redBlackifyDelete(nodeToFix);
        }
        size--;
        return true;
    }

    private Node deleteNode(Node node) {
        //If node has no children.
        if(node.left == null && node.right == null) {
            Node parent;
            if(node.parent != null) {
                parent = node.parent;
            }
            else {
                parent = node;
            }

            if(parent.left != null && parent.left == node) {
                parent.left = null;
            }
            else {
                parent.right = null;
            }
            return parent;
        }

        //If the node has two children.
        if(node.left != null && node.right != null) {
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            return deleteNode(successor);
        }
        //Node has one child.
        Node child;
        if(node.left != null) {
            child = node.left;
        }
        else {
            child = node.right;
        }

        if(node.parent == null) {
            root = child;
        }
        else if (node == node.parent.left) {
            node.parent.left = child;
        }
        else {
            node.parent.right = child;
        }

        if(child != null) {
            child.parent = node.parent;
        }

        if(!node.isRed) {
            if(child != null) {
                return child;
            }
            else {
                return node;
            }
        }

        //Does not need to be red-black balanced.
        return null;
    }


    private Node getSibling(Node node) {
        Node parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        }
        else {
            return parent.left;
        }
    }

    private void redBlackifyDelete(Node node) {
        if(node == null) {
            return;
        }
        //Case 1: node is the root.
        while(node != root) {
            if (node.parent == null) {
                break;
            }

            Node parent = node.parent;
            Node sibling = getSibling(node);

            if(sibling == null) {
                node = node.parent;
                continue;
            }
            if(sibling.isRed) {
                parent.isRed = true;
                sibling.isRed = false;

                if(node == parent.left) {
                    rotateLeft(parent);
                }
                else if(node == parent.right) {
                    rotateRight(parent);
                }

                parent = node.parent;
                sibling = getSibling(node);
                if(sibling == null) {
                    node = node.parent;
                    continue;
                }
            }

            //Case 3: parent, sibling, and sibling's children are black.
            if(!parent.isRed && !sibling.isRed && !sibling.left.isRed && !sibling.right.isRed) {
                sibling.isRed = true;
                node = parent;
                continue;
            }

            //Case 4: parent is red, sibling and its children are black.
            if(parent.isRed && !sibling.isRed && !sibling.left.isRed && !sibling.right.isRed) {
                sibling.isRed = true;
                parent.isRed = false;
                return;
            }

            //Case 5 sibling is black. sibling has red left child, black right child, and the node is the left child of its parent.
            if(!sibling.isRed) {
                if(node == parent.left && sibling.left.isRed && !sibling.right.isRed) {
                    sibling.isRed = true;
                    sibling.left.isRed = true;

                    rotateRight(sibling);

                    parent = node.parent;
                    sibling = getSibling(node);
                }
                else if (node == parent.right && !sibling.left.isRed && sibling.right.isRed) {
                    sibling.isRed = true;
                    sibling.right.isRed = true;

                    rotateLeft(sibling);

                    parent = node.parent;
                    sibling = getSibling(node);
                }
            }

            //Case 6: sibling black, sibling right child red, node is left child of parent.
            sibling.isRed = parent.isRed;
            parent.isRed = false;
            if(node == parent.left) {
                sibling.right.isRed = false;
                rotateLeft(parent);
            }
            else if(node == parent.right) {
                sibling.left.isRed = false;
                rotateRight(parent);
            }
        }
        if(node != null) {
            node.isRed = false;
        }


    }

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
