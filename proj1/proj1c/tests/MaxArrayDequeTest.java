import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    @Test
    public void IteratorTest() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);
        arrayDeque.addLast(4);
        assertThat(arrayDeque).containsExactly(1, 2, 3, 4);

        LinkedListDeque<Integer> linkedListDeque = new LinkedListDeque<>();
        linkedListDeque.addLast(1);
        linkedListDeque.addLast(2);
        linkedListDeque.addLast(3);
        linkedListDeque.addLast(4);
        assertThat(linkedListDeque).containsExactly(1,2,3,4);
    }

    @Test
    public void EqualsTest() {
        ArrayDeque<Integer> arrayDeque1 = new ArrayDeque<>();
        arrayDeque1.addLast(1);
        arrayDeque1.addLast(2);
        arrayDeque1.addLast(3);
        arrayDeque1.addLast(4);

        ArrayDeque<Integer> arrayDeque2 = new ArrayDeque<>();
        arrayDeque2.addLast(1);
        arrayDeque2.addLast(2);
        arrayDeque2.addLast(3);
        arrayDeque2.addLast(4);
        assertThat(arrayDeque1).isEqualTo(arrayDeque2);


        LinkedListDeque<Integer> llist1 = new LinkedListDeque<>();
        llist1.addLast(1);
        llist1.addLast(2);
        llist1.addLast(3);
        llist1.addLast(4);

        LinkedListDeque<Integer> llist2 = new LinkedListDeque<>();
        llist2.addLast(1);
        llist2.addLast(2);
        llist2.addLast(3);
        llist2.addLast(4);
        assertThat(llist1).isEqualTo(llist2);
    }

    @Test
    public void toStringTest() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);
        arrayDeque.addLast(4);
        assertThat(arrayDeque.toString()).isEqualTo("[1, 2, 3, 4]");

        LinkedListDeque<Integer> linkedListDeque = new LinkedListDeque<>();
        linkedListDeque.addLast(1);
        linkedListDeque.addLast(2);
        linkedListDeque.addLast(3);
        linkedListDeque.addLast(4);
        assertThat(linkedListDeque.toString()).isEqualTo("[1, 2, 3, 4]");
    }

    public void LinkedListDequeTest() {

    }
}
