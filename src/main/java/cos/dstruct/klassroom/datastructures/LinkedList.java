package cos.dstruct.klassroom.datastructures;

import java.util.*;

public class LinkedList<T> implements Iterable {

    public LinkedList(T data) {
        curr = new Node<>(null, null, data);
        curr.next = curr;
        curr.prev = curr;
    }

    public LinkedList(ArrayList<T> list) {
        curr = new Node<>(null, null, list.get(0));
        curr.next = curr;
        curr.prev = curr;
        list.remove(0);
        for (T obj : list) {
            insert(obj);
        }
    }

    public class LLIterator implements Iterator<T> {

        public LLIterator(Node<T> start_node) {
            current_node = start_node;
            this.start_node = start_node;
            startup = false;
        }

        @Override
        public boolean hasNext() {
            return !((current_node == start_node) && startup);
        }

        @Override
        public T next() {
            Node<T> tmp = current_node;
            current_node = current_node.next;
            startup = true;
            return tmp.data;
        }
        Node<T> current_node;
        Node<T> start_node;
        Boolean startup;

    }

    protected static class Node<T> {

        Node(Node<T> next, Node<T> prev, T data) {
            this.next = next;
            this.prev = prev;
            this.data = data;
        }

        @Override
        public String toString() {
            return "Data: " + data + " prev: " + prev.getClass() + "next: " + next.getClass();
        }
        Node<T> next;
        Node<T> prev;
        T data;

    }

    public void insert(T data) {
        Node<T> new_node = new Node<T>(curr.next, curr, data);
        curr.next.prev = new_node;
        curr.next = new_node;
    }

    public void next() {
        curr = curr.next;
    }

    @Override
    public Iterator<T> iterator() {
        return new LLIterator(curr);
    }

    public void remove() {
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        curr = curr.next;
    }

    public T getData() {
        return curr.data;
    }
    Node<T> curr;

}
