import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int start;
    private int end;
    private int capacity;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        capacity = 8;
        start = 0;
        end = 0;
    }

    private void resize(int capacity_new) {
        T[] items_new = (T[]) new Object[capacity_new];
        int i = start;
        int j = 0;
        for (;j < capacity;j++) {
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
        for (int i = 0; i < capacity; i++) {
            returnList.add(items[i]);
        }
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
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
