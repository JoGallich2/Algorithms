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
            Node sibling;
            if(node == root) {
                root = null;
                return null;
            }
            Node parent;
            if(node.parent != null) {
                parent = node.parent;
            }
            else {
                parent = node;
            }

            if(parent.left != null && parent.left == node) {
                parent.left = null;
                sibling = parent.right;
            }
            else {
                parent.right = null;
                sibling = parent.left;
            }
            if(node.isRed) {
                return null;
            }
            //Handle double black.
            else {
                //If parent red, sibling black
                if(sibling != null && !sibling.isRed && parent.isRed) {
                    //If sibling has children.
                    if(sibling.left != null || sibling.right != null) {
                        parent.isRed = false;
                        sibling.isRed = true;
                        //Does parent have a parent?
                        if(parent.parent != null) {
                            //If so, make the sibling red if it exists.
                            Node parentSibling = (parent == parent.parent.left) ? parent.parent.right : parent.parent.left;
                            if(parentSibling != null) {
                                parentSibling.isRed = true;
                            }
                        }
                    }
                    //Sibling has no children.
                    else {
                        parent.isRed = false;
                        sibling.isRed = true;
                    }
                }
                //else? always return parent or only sometimes?
                return parent;
            }

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


//    private Node getSibling(Node node) {
//        Node parent = node.parent;
//        if (node == parent.left) {
//            return parent.right;
//        }
//        else {
//            return parent.left;
//        }
//    }

    private void redBlackifyDelete(Node node) {
        while (node != root && !node.isRed) {
            if (node.parent == null) {
                break;
            }

            // If node is left child.
            if (node == node.parent.left) {
                Node sibling = node.parent.right;

                if (sibling == null) {
                    node = node.parent;
                    continue;
                }

                if (sibling.isRed) {
                    // Sibling is red. Recolor sibling and parent, and rotate to make sibling black.
                    sibling.isRed = false;
                    node.parent.isRed = true;
                    rotateLeft(node.parent);
                    sibling = (node.parent.left == node) ? node.parent.right : node.parent.left;
                }

                if ((sibling.left == null || !sibling.left.isRed) &&
                        (sibling.right == null || !sibling.right.isRed)) {
                    // Sibling and its children are black. Make sibling red, then go to parent to fix higher up the tree.
                    sibling.isRed = true;
                    node = node.parent;
                } else {
                    if (sibling.right == null || !sibling.right.isRed) {
                        // Sibling's right child is black. Recolor sibling and the left child, then rotate.
                        if (sibling.left != null) {
                            sibling.left.isRed = false;
                        }
                        sibling.isRed = true;
                        rotateRight(sibling);
                        sibling = (node.parent.left == node) ? node.parent.right : node.parent.left;
                    }

                    // Sibling's right child is red. Recolor sibling and parent, then rotate.
                    sibling.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    if (sibling.right != null) {
                        sibling.right.isRed = false;
                    }
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                // Same cases as above but checking for the right child.
                Node sibling = node.parent.left;

                if (sibling == null) {
                    node = node.parent;
                    continue;
                }

                if (sibling.isRed) {
                    // Sibling is red
                    sibling.isRed = false;
                    node.parent.isRed = true;
                    rotateRight(node.parent);
                    sibling = (node.parent.left == node) ? node.parent.right : node.parent.left;
                }

                //Does this if make any sense?
                //if(sibling == null) {
                //    continue;
                //}
                if ((sibling.left == null || !sibling.left.isRed) &&
                        (sibling.right == null || !sibling.right.isRed)) {
                    // Sibling and its children are black
                    sibling.isRed = true;
                    node = node.parent;
                } else {
                    if (sibling.left == null || !sibling.left.isRed) {
                        // Sibling's left child is black
                        if (sibling.right != null) {
                            sibling.right.isRed = false;
                        }
                        sibling.isRed = true;
                        rotateLeft(sibling);
                        sibling = (node.parent.left == node) ? node.parent.right : node.parent.left;
                    }

                    // Sibling's left child is red.
                    sibling.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    if (sibling.left != null) {
                        sibling.left.isRed = false;
                    }
                    rotateRight(node.parent);
                    node = root;
                }
            }
        }
        node.isRed = false;
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
