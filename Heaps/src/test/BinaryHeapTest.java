package test;

import heaps.BinaryHeap;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class BinaryHeapTest {

    // fields
    BinaryHeap<Task> heap = new BinaryHeap<>();

    // mock data
    final Task[] tasks = {
            new Task(Priority.HIGH, 1, "Finish homework"),
            new Task(Priority.MEDIUM, 2, "Go to gym"),
            new Task(Priority.LOW, 1, "check Facebook"),
            new Task(Priority.MEDIUM, 1, "get groceries"),
            new Task(Priority.MEDIUM, 3, "go to dinner"),
            new Task(Priority.LOW, 2, "play football"),
            new Task(Priority.HIGH, 1, "pay bills"),
            new Task(Priority.MEDIUM, 3, "Clean house"),
            new Task(Priority.HIGH, 2, "feed cats")
    };

    @Before
    public void setUp() throws Exception {
        heap = new BinaryHeap<>();  // construct empty heap
    }

    @Test
    public void testEmptyHeapIsEmpty() {
        assertTrue("Empty heap is not empty", heap.isEmpty());
    }

    @Test
    public void testOneElementHeapIsEmptyFalse() {
        heap.insert(tasks[0]);
        assertFalse("One-element heap is empty", heap.isEmpty());
    }

    @Test
    public void testEmptyHeapInsertGoodTask1() {
        heap.insert(tasks[0]);
        assertEquals("Task was not inserted", 1, heap.size());
    }

    @Test
    public void testEmptyHeapInsertGoodTask2() {
        heap.insert(tasks[0]);
        assertSame("Task was not inserted", tasks[0], heap.findMin());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testEmptyHeapInsertBadTask() {
        heap.insert(null);
    }

    @Test
    public void testContainsTrue() {
        heap.insert(tasks[0]);
        assertTrue("Task was not inserted", heap.contains(tasks[0]));
    }

    @Test
    public void testContainsFalse() {
        assertFalse("Task was found in empty heap", heap.contains(tasks[0]));
    }

    @Test
    public void testEmptyHeapDeleteMin() {
        assertNull("delete min returned non-null value", heap.deleteMin());
    }

    @Test
    public void testEmptyHeapFindMin() {
        assertNull("findMin returned non-null value", heap.findMin());
    }

    @Test
    public void clear() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void testEmptyHeapIterator() {
        for (Task t : heap) {
            fail("Iterator should not find value in empty heap");
        }
    }

    @Test
    public void testMultiElemHeapIterator() {
        // initial tasks before iteration
        Task[] initTasks = new Task[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            initTasks[i] = tasks[i];
            heap.insert(tasks[i]);
        }

        // create a second Task array
        Task[] foundTasks = new Task[initTasks.length];

        // iterate and store tasks
        int i = 0;
        for (Task t : heap) {
            foundTasks[i++] = t;
        }

        // compare both arrays after they are sorted
        Arrays.sort(initTasks);
        Arrays.sort(foundTasks);
        assertArrayEquals("Values from iterator don't match starting values", initTasks, foundTasks);
    }

    @Test(expected=ConcurrentModificationException.class)
    public void testModWhileIterating() {
        // load data for iterator
        for (Task t : tasks) {
            heap.insert(t);
        }

        // iterate over heap and deleteMin
        for (Task t : heap) {
            heap.deleteMin();
        }
    }
}