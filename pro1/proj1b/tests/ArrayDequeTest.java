import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

     @Test
     @DisplayName("ArrayDeque has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
     void addFirstTest() {
         ArrayDeque<Integer> alist = new ArrayDeque<>();
         alist.addFirst(1);
         assertThat(alist.toList()).containsExactly(1, null, null, null, null, null, null, null).inOrder();
         alist.addFirst(2);
         assertThat(alist.toList()).containsExactly(1, null, null, null, null, null, null, 2).inOrder();
         alist.addFirst(3);
         assertThat(alist.toList()).containsExactly(1, null, null, null, null, null, 3, 2).inOrder();
         alist.addFirst(1);
         assertThat(alist.toList()).containsExactly(1, null, null, null, null, 1, 3, 2).inOrder();
         alist.addFirst(2);
         assertThat(alist.toList()).containsExactly(1, null, null, null, 2, 1, 3, 2).inOrder();
         alist.addFirst(3);
         assertThat(alist.toList()).containsExactly(1, null, null, 3, 2, 1, 3, 2).inOrder();
         alist.addFirst(1);
         assertThat(alist.toList()).containsExactly(1, null, 1, 3, 2, 1, 3, 2).inOrder();
         alist.addFirst(2);
         assertThat(alist.toList()).containsExactly(1, 2, 1, 3, 2, 1, 3, 2).inOrder();
         alist.addFirst(3);
         assertThat(alist.toList()).containsExactly(2, 1, 3, 2, 1, 3, 2, 1, null, null, null, null, null, null, null, 3).inOrder();
         alist.addFirst(4);
         assertThat(alist.toList()).containsExactly(2, 1, 3, 2, 1, 3, 2, 1, null, null, null, null, null, null, 4, 3).inOrder();
     }

     @Test
     void addLastTest() {
         ArrayDeque<Integer> alist = new ArrayDeque<>();
         alist.addLast(1);
         assertThat(alist.toList()).containsExactly(1, null, null, null, null, null, null, null).inOrder();
         alist.addLast(2);
         assertThat(alist.toList()).containsExactly(1, 2, null, null, null, null, null, null).inOrder();
         alist.addLast(3);
         assertThat(alist.toList()).containsExactly(1, 2, 3, null, null, null, null, null).inOrder();
         alist.addLast(4);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, null, null, null, null).inOrder();
         alist.addLast(5);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, 5, null, null, null).inOrder();
         alist.addLast(6);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, 5, 6, null, null).inOrder();
         alist.addLast(7);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, null).inOrder();
         alist.addLast(8);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8).inOrder();
         alist.addLast(9);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, null, null, null, null, null, null, null).inOrder();
         alist.addLast(10);
         assertThat(alist.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, null, null, null, null, null, null).inOrder();
     }

     @Test
     void randomAddTest() {
         ArrayDeque<Integer> alist = new ArrayDeque<>();
         alist.addLast(1);
         alist.addLast(2);
         alist.addFirst(3);
         alist.addFirst(4);
         alist.addFirst(5);
         alist.addLast(6);
         alist.addFirst(7);
         alist.addLast(8);

         alist.addFirst(9);
         alist.addLast(10);
         assertThat(alist.toList()).containsExactly( 7, 5, 4, 3, 1, 2, 6, 8, 10, null, null, null, null, null, null, 9).inOrder();
     }

     @Test
     void sizeTest() {
         ArrayDeque<Integer> alist = new ArrayDeque<>();
         alist.addLast(1);
         assertThat(alist.size()).isEqualTo(1);
         alist.addLast(2);
         assertThat(alist.size()).isEqualTo(2);
         alist.addFirst(3);
         assertThat(alist.size()).isEqualTo(3);
         alist.addFirst(4);
         assertThat(alist.size()).isEqualTo(4);
         alist.addFirst(5);
         assertThat(alist.size()).isEqualTo(5);
         alist.addLast(6);
         assertThat(alist.size()).isEqualTo(6);
         alist.addFirst(7);
         assertThat(alist.size()).isEqualTo(7);
         alist.addLast(8);
         assertThat(alist.size()).isEqualTo(8);
         alist.addFirst(9);
         assertThat(alist.size()).isEqualTo(9);
         alist.addLast(10);
         assertThat(alist.size()).isEqualTo(10);
         alist.removeFirst();
         assertThat(alist.size()).isEqualTo(9);
         alist.removeLast();
         assertThat(alist.size()).isEqualTo(8);
     }

     @Test
     void removeFirstTest() {
         ArrayDeque<Integer> alist = new ArrayDeque<>();
         alist.addLast(1);
         alist.addLast(2);
         alist.addFirst(3);
         alist.addFirst(4);
         alist.addFirst(5);
         alist.addLast(6);
         alist.addFirst(7);
         alist.addLast(8);
         alist.addFirst(9);
         alist.addLast(10);

         assertThat(alist.removeFirst()).isEqualTo(9);
         assertThat(alist.removeFirst()).isEqualTo(7);
         assertThat(alist.removeFirst()).isEqualTo(5);
         assertThat(alist.removeFirst()).isEqualTo(4);
         assertThat(alist.removeFirst()).isEqualTo(3);
     }

    @Test
    void removeLastTest() {
        ArrayDeque<Integer> alist = new ArrayDeque<>();
        alist.addLast(1);
        alist.addLast(2);
        alist.addFirst(3);
        alist.addFirst(4);
        alist.addFirst(5);
        alist.addLast(6);
        alist.addFirst(7);
        alist.addLast(8);
        alist.addFirst(9);
        alist.addLast(10);

        assertThat(alist.removeLast()).isEqualTo(10);
        assertThat(alist.removeLast()).isEqualTo(8);
        assertThat(alist.removeLast()).isEqualTo(6);
        assertThat(alist.removeLast()).isEqualTo(2);
        assertThat(alist.removeLast()).isEqualTo(1);
    }

}
