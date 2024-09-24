import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    private int size;

    private class Node {
        Node prev;
        Node next;
        T val;

        public Node() {
            this.prev = null;
            this.next = null;
        }
        public Node(T val) {
            this.prev = null;
            this.next = null;
            this.val = val;
        }
        public Node(Node prev, Node next, T val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {

    }

    @Override
    public void addLast(T x) {

    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
