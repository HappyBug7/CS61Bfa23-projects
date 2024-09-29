package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T max() {
        T max = null;
        for (int i = 0; i < size()-1; i++) {
            if (comparator.compare(get(i), get(i+1)) > 0) {
                max = super.get(i);
            }
        }
        return max;
    }

    public T max(Comparator<T> comparator) {
        T max = null;
        for (int i = 0; i < size()-1; i++) {
            if (comparator.compare(get(i), get(i+1)) > 0) {
                max = super.get(i);
            }
        }
        return max;
    }
}
