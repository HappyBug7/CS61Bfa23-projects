package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int start;
    private int end;
    private int capacity;

    private class ArrayDequeIterator implements Iterator<T> {
        public int currentIndex = 0;

        public ArrayDequeIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            T returnItem = items[currentIndex];
            currentIndex++;
            return returnItem;
        }
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        capacity = 8;
        start = 0;
        end = 0;
    }

    private void resize(int capacity_new) {
        if (capacity_new > capacity) {
            T[] items_new = (T[]) new Object[capacity_new];
            int i = start;
            int j = 0;
            for (;j < capacity; j++) {
                items_new[j] = items[i];
                if (i==capacity-1) {
                    i=0;
                } else {
                    i++;
                }
            }
            start = 0;
            end = capacity-1;
            capacity = capacity_new;
            items = items_new;
        } else {
            T[] items_new = (T[]) new Object[capacity_new];
            int i = start;
            int j = 0;
            for (;j < size; j++) {
                items_new[j] = items[i];
                if (i==capacity-1) {
                    i=0;
                } else {
                    i++;
                }
            }
            start = 0;
            end = size-1;
            capacity = capacity_new;
            items = items_new;
        }
    }

    @Override
    public void addFirst(T x) {
        if (size == 0) {
            items[start] = x;
            size++;
            return;
        }
        if (size >= capacity) {
            resize(capacity * 2);
        }
        if (start == 0) {
            start = capacity-1;
        } else {
            start--;
        }
        items[start] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == 0) {
            items[end] = x;
            size++;
            return;
        }
        if (size >= capacity) {
            resize(capacity * 2);
        }
        if (end == capacity-1) {
            end = 0;
        } else {
            end++;
        }
        items[end] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList  = new ArrayList<>();
        int i = start;
        while (i != end) {
            returnList.add(items[i]);
            if (i == capacity - 1) {
                i = 0;
            } else {
                i++;
            }
        }
        returnList.add(items[i]);
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size <= capacity * 0.25) {
            resize(capacity / 4);
        }
        int index = start;
        if (start == capacity - 1) {
            start = 0;
        } else {
            start++;
        }
        size--;
        return items[index];
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size <= capacity * 0.25) {
            resize(capacity / 4);
        }
        int index = end;
        if (end == 0) {
            end = capacity-1;
        } else {
            end--;
        }
        size--;
        return items[index];
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int index_real = (start + index) % capacity;
        return items[index_real];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public boolean contains(T item) {
        for (T i : items) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof ArrayDeque arr) {
            if (size != arr.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!arr.contains(get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}
