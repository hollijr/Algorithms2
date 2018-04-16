package heaps;

import java.util.Iterator;

public interface IPriorityQueue<T extends Comparable<T>> extends Iterable<T> {

    // ADT methods
    public void insert(T element);
    public T deleteMin();
    public T findMin();  // peek()
    // decrease(T element, int amount);
    // increase(T element, int amount);
    // remove(T element);

    // other standard methods
    public int size();
    public boolean isEmpty();
    public void clear();
    public boolean contains(T element);
    public Iterator<T> iterator();

}
