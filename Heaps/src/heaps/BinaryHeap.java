package heaps;

import java.util.Iterator;

public class BinaryHeap<E extends Comparable<E>> implements IPriorityQueue<E> {

    // constants
    private static final int INITIAL_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;
    private static final int MIN_INDEX = 1;

    // instance variables
    private E[] heap;
    private int size; // represents the number of element in the heap

    // constructor
    public BinaryHeap() {
        heap = (E[]) new Comparable[INITIAL_CAPACITY + 1];
    }

    public BinaryHeap(E[] elements) {
        heap = (E[]) new Comparable[elements.length + 1];

        // copy the elements to our heap
        for (int i = 0; i < elements.length; i++) {
            heap[i + 1] = elements[i];
        }
        buildHeap();
    }

    @Override
    public void insert(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element to the heap!");
        }

        // is heap full?  if so, resize it
        if (size == heap.length - 1) {
            resize();
        }

        heap[++size] = element;  // puts element at end of array
        swim(size);
    }

    private void resize() {
        // create a new heap that is larger
        E[] oldHeap = heap;
        heap = (E[]) new Comparable[oldHeap.length * RESIZE_FACTOR];

        // copy elements from old heap to new heap
        for (int i = 0; i < oldHeap.length; i++) {
            heap[i] = oldHeap[i];
        }
    }

    // percolates the value up to its correct place
    // according to heap-order property
    private void swim(int index) {
        // loop while the child node is less than its parent
        // and while we haven't gone past the root
        while (index > MIN_INDEX && isLess(index, index/2)) {
            // swap child and parent
            swap(index, index/2);
            index /= 2;  // make index the parent's index
        }
    }

    // helper method to perform comparisons
    private boolean isLess(int first, int second) {
        return heap[first].compareTo(heap[second]) < 0;
    }

    // helper method to swap two elements in the heap
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public E deleteMin() {
        // if heap is empty, return null
        if (isEmpty()) {
            return null;
        }

        // get the minimum object to return
        E returnElement = heap[MIN_INDEX];

        // move the last leaf to the root
        swap(MIN_INDEX, size--);
        heap[size + 1] = null;  // be a good citizen for my garbage collector

        // restore the heap-order property
        sink(MIN_INDEX);

        return returnElement; // returns the min
    }

    private void sink(int parent) {

        // loop while we're not at the bottom/end of
        // the heap
        while (parent * 2 <= size) {

            // get the left child
            int child = 2 * parent;

            // check if the right child is smaller
            if (child + 1 <= size && isLess(child + 1, child)) child++;

            // compare smaller child to the parent
            if (!isLess(child, parent)) break;  // done

            // swap them!
            swap(child, parent);
            parent = child;
        }
    }

    @Override
    public E findMin() {
        return heap[MIN_INDEX];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        heap = (E[]) new Comparable[INITIAL_CAPACITY + 1];
    }

    @Override
    public boolean contains(E element) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    // Floyd's method implementation
    private void buildHeap() {
        // loop from mid-array forward
        for (int i = size / 2; i >= MIN_INDEX; i--) {
            sink(i);
        }
    }
}
