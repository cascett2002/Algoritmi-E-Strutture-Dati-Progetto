import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class JUnitPriorityQueue {

    Comparator<Integer> comparator = (o1, o2) -> Integer.compare(o1, o2);

    @Test
    public void testEmpty() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        assertTrue(priorityQueue.empty());
        priorityQueue.push(10);
        assertFalse(priorityQueue.empty());
    }

    @Test
    public void testPush() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        assertTrue(priorityQueue.push(10));
        assertTrue(priorityQueue.push(5));
        assertFalse(priorityQueue.push(10)); // Duplicato, dovrebbe restituire false
    }

    @Test
    public void testContains() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.push(2);
        assertTrue(priorityQueue.contains(2));
        assertFalse(priorityQueue.contains(8));
    }

    @Test
    public void testTop() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        assertNull(priorityQueue.top());
        priorityQueue.push(5);
        priorityQueue.push(10);
        assertEquals(Integer.valueOf(5), priorityQueue.top());
    }

    @Test
    public void testPop() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.push(10);
        priorityQueue.push(5);
        priorityQueue.pop();
        assertEquals(Integer.valueOf(10), priorityQueue.top());
    }

    @Test
    public void testRemove() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.push(8);
        priorityQueue.push(2);
        priorityQueue.push(11);
        priorityQueue.push(4);

        assertTrue(priorityQueue.remove(4));

        // Verifica che l'elemento 10 sia stato rimosso correttamente
        assertFalse(priorityQueue.contains(10));

        // Verifica che la coda sia ancora ordinata correttamente
        assertEquals(Integer.valueOf(2), priorityQueue.top());

    }
}
