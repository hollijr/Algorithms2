package hashmap;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * HashTable that uses linear probing for collision resolution
 * @param <K> key  (for example, flight numbers)
 * @param <V> value  (for example, flight data)
 */
public class LPHashTable<K, V> implements Iterable<V> {

    // constants for managing the table
    private static final double LOAD_FACTOR = 0.7;
    private static final int INITIAL_TABLE_SIZE = 7;
    private static final double RESIZE_FACTOR = 2;

    private KVPair[] table;

    private int size; // number of objects in table
    private int tombstones; // number of inactive objects
    private int modcount;  // for fail-fast iterator

    // constructor
    public LPHashTable() {
        this(INITIAL_TABLE_SIZE);
    }

    public LPHashTable(int tableSize) {
        table = new KVPair[tableSize];
    }

    // get size of table
    public int size() {
        return size - tombstones;
    }

    // is table empty?
    public boolean isEmpty() {
        return (size - tombstones) == 0;
    }

    public void clear() {
        size = 0;  // reset the active elements tracker
        tombstones = 0;
        table = new KVPair[INITIAL_TABLE_SIZE];
    }

    // overload for users who want to specify a starting table size
    public void clear(int tableSize) {
        size = 0;  // reset the active elements tracker
        tombstones = 0;
        table = new KVPair[tableSize];
    }

    /** Add a value to the table based on a new key.  Keys must be unique.
     * Adding an existing (active) key will update the value associated with the key.
     * Use FCFS linear probing to resolve collisions.
     * @param key
     * @param value
     */
    public void add(K key, V value) {

        // check key isn't null
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        // check load factor to see if table needs to be resized
        if (tableSizeExceedsLF()) {
            resize();
        }

        int index = getHashIndex(key);

        /**** start: just for testing ******/
        if (table[index] == null) {
            System.out.println("Found a space!");
        } else {
            System.out.println("Collision!");
        }
        /**** end: just for testing ******/

        // sequentially probe table while the slots contain
        // active KVPairs.  At each active KVPair, check if it
        // matches the key -- if so, update it and return.
        modcount++;
        while (table[index] != null && table[index].active) {

            // if key matches, update existing object
            if (table[index].key.equals(key)) {
                table[index].value = value;
                table[index].active = true;
                return;
            }
            index = ++index % table.length;
        }

        // current key is not active so create a new
        // KVPair object for it and store it in the
        // empty or tombstoned slot that was found,
        if (table[index] != null) tombstones--;
        table[index] = new KVPair(key, value);
        size++;

    }

    // Computes the hash index of a key
    private int getHashIndex(K key) {
        int code = key.hashCode();
        return code % table.length;
    }

    /** Finds (gets) the value for a given key.
     * @param key
     * @return value if key is found in table; null otherwise.
     */
    public V find(K key) {

        int index = getHashIndex(key);

        while (table[index] != null) {

            // is this index where the key resides?
            if (table[index].key.equals(key)) {
                if (!table[index].active) break;
                return (V) table[index].value;
            }

            index = ++index % table.length ;  // otherwise, look at next slot
        }

        return null;
    }

    // determines if load factor is exceeded by current size
    // only used on add.
    private boolean tableSizeExceedsLF() {
        return (double) size / table.length >= LOAD_FACTOR;
    }

    // resize the table
    private void resize() {

        System.out.println("Resizing table!");

        KVPair[] oldTable = table;

        table = new KVPair[(int)(oldTable.length * RESIZE_FACTOR)];
        size = 0;
        tombstones = 0;

        for (KVPair kvpair : oldTable) {
            // re-add active objects to the new, larger table
            if (kvpair != null && kvpair.active) {
                add((K) kvpair.key, (V) kvpair.value);
            }
        }
        System.out.println("Debug: \n\tsize: " + size + "\n" +
                "\tlength: " + table.length + "\n\ttombstones: " + tombstones);
    }

    /**
     * Removes the value associated with the key from the table.  Does so
     * using lazy deletion -- marks KVPair as inactive.  Will be cleaned up
     * on next resize of table.  We'll track # of occupied-but-inactive ("tombstone") slots
     * because we may want to add some threshold for % of tombstones compared to size
     * or ?? but requires more analysis to determine necessity and approach -- not
     * something we'll solve here.
     * @param key
     */
    public void delete(K key) {

        // check key isn't null
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int index = getHashIndex(key);

        // sequentially probe table while the slots contain
        // active KVPairs.  At each active KVPair, check if it
        // matches the key -- if so, update it and return.
        while (table[index] != null) {

            // if found, tombstone it
            if (table[index].key.equals(key)) {
                table[index].active = false;
                tombstones++;
                modcount++;
                return;
            }
            index = ++index % table.length;
        }
    }

    // testing only
    public int getTableLength() {
        return table.length;
    }

    @Override
    public Iterator<V> iterator() {
        return new HTIterator();
    }

    /***** INNER CLASSES ************/

    // define inner class that contains both key K and value V
    private class KVPair<K, V> {

        private K key;
        private V value;

        // track whether this object is active (i.e., deleted or not)
        private boolean active;

        public KVPair(K key, V value) {
            this.key = key;
            this.value = value;
            this.active = true;
        }

        public String toString() {
            return value.toString();
        }
    }

    // iterator over values in the table
    private class HTIterator implements Iterator<V> {

        private int index;
        private int origcount;

        public HTIterator() {
            origcount = modcount;
            getNextIndex();
        }

        private void getNextIndex() {
            while (index < table.length && (table[index] == null || !table[index].active)) index++;
        }

        @Override
        public boolean hasNext() {
            return index < table.length;
        }

        @Override
        public V next() {
            if (origcount != modcount) throw new RuntimeException("Cannot modify data during iteration");
            if (!hasNext()) throw new NoSuchElementException();
            int i = index;
            index++;
            getNextIndex();
            return (V) table[i].value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
