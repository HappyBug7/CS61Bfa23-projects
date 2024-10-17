package deque;

import java.util.Comparator;

public class MaxArrayDequeComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        if (o1 instanceof Integer) {
            return (Integer) o1 - (Integer) o2;
        }
        if (o1 instanceof String) {
            return ((String) o1).compareTo((String) o2);
        }
        return 0;
    }
}
