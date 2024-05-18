package cos.dstruct.klassroom.datastructures;

public class BSTree<T extends Comparable<T>> {


    BSTree() {
        null_node = new Node<>(null);
        root = null_node;
    }
    BSTree(T data) {
        null_node = new Node<>(null);
        root = new Node<>(data);
    }

    private static class Node<T> {

        Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
        Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
        Node<T> left;
        Node<T> right;
        T data;

    }

    public void insertData(T data) throws IllegalArgumentException {

        if (root == null_node) {
            root = new Node<>(data, null_node, null_node);
        }

        insertDataRec(root, data);
    }

    private Node<T> findNode(T data) {

        Node<T> node = root;

        while (node != null_node) {
            if (data.compareTo(node.data) > 0) {
                node = node.right;
            } else if (data.compareTo(node.data) < 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null_node;
    }

    private void insertDataRec(Node<T> node, T data) throws IllegalArgumentException {

        if (data.compareTo(node.data) > 0) {
            if (node.right == null_node) {
                node.right = new Node<>(data, null_node, null_node);
                return;
            }
            insertDataRec(node.right, data);
        } else if (data.compareTo(node.data) < 0) {
            if (node.left == null_node) {
                node.left = new Node<>(data, null_node, null_node);
                return;
            }
            insertDataRec(node.left, data);
        } else {
            throw new IllegalArgumentException("Duplicate Entry " + data + " detected");
        }
    }
    Node<T> root;
    Node<T> null_node;

}
