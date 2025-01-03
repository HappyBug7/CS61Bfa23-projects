import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private final Node sentinel;
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
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node head = sentinel.next;
        sentinel.next = new Node(sentinel, head, x);
        head.prev = sentinel.next;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node last = sentinel.prev;
        sentinel.prev = new Node(last, sentinel, x);
        last.next = sentinel.prev;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList  = new ArrayList<>();
        Node curr = sentinel.next;
        while (curr != sentinel) {
            returnList.add(curr.val);
            curr = curr.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node head = sentinel.next;
        sentinel.next = head.next;
        head.next.prev = sentinel;
        return head.val;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node last = sentinel.prev;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        return last.val;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node curr = sentinel.next;
        int i = 0;
        while (i < index) {
            curr = curr.next;
            i++;
        }
        return curr.val;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node curr) {
        if (index == 0) {
            return curr.val;
        }
        return getRecursiveHelper(index - 1, curr.next);
    }
}
