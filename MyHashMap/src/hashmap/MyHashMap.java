package hashmap;

/**
 * HashTable that uses linear probing for collision resolution
 * @param <K> key  (for example, flight numbers)
 * @param <V> value  (for example, flight data)
 */
public class MyHashMap<K, V> {

    // constants for managing the table
    private static final double LOAD_FACTOR = 0.7;
    private static final int INITIAL_TABLE_SIZE = 7;
    private static final double RESIZE_FACTOR = 2;

    private KVPair[] table;

    private int size; // number of filled elements in table
    private int tombstones; // number of spaces that are inactive

    // constructor
    public MyHashMap() {
        this(INITIAL_TABLE_SIZE);
    }

    public MyHashMap(int tableSize) {
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
        tombstones = 0;  // reset the all elements tracker
        table = new KVPair[INITIAL_TABLE_SIZE];
    }

    // overload for users who want to specify a starting table size
    public void clear(int tableSize) {
        size = 0;  // reset the active elements tracker
        tombstones = 0;  // reset the all elements tracker
        table = new KVPair[tableSize];
    }


    /**
     * Add a value to the table based on a new key.  Keys must be unique.
     * Adding an existing (active) key will update the value associated
     * with the key.  Use FCFS linear probing to resolve collisions.
     * @param key
     * @param value
     * @return
     */
    public void add(K key, V value) {

        // check that key is not null
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        // check load factor to see if table needs to be resized
        if (tableSizeExceedsLF()) {
            resize();
        }

        int index = getHashIndex(key);

        /********** start: this is for testing purposes *************/
        if (table[index] == null) {
            System.out.println("Found a space!");
        } else {
            System.out.println("Collision!");
        }
        /********** end: this is for testing purposes *************/

        // sequentially probe table while the slots contain
        // active KVPairs.  At each active KVPair, check if it matches
        // the key -- if so, update it and return immediately.
        while (table[index] != null && table[index].active) {

            // if key matches, update the existing object
            if (table[index].key.equals(key)) {
                table[index].value = value;
                table[index].active = true;
                return;
            }

            index = ++index % table.length;
        }

        // current key is not active so create a new KVPair
        // object for it and store it in the empty or
        // tombstoned slot that was found.
        if (table[index] != null) tombstones--;
        table[index] = new KVPair(key, value);
        size++;
    }

    // finds the table index based on the key
    private int getHashIndex(K key) {
        int code = key.hashCode();
        return code % table.length;
    }

    /**
     * Finds (gets) the value for a given key.  If key doesn't exist in table,
     * returns null;
     * @param key
     * @return value if key exists; otherwise, null.
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

    // for adds
    public boolean tableSizeExceedsLF() {
        return (double) size / table.length >= LOAD_FACTOR;
    }


    // resize the table
    private void resize() {
        //TODO
    }

    /**
     * Remove the value assocaited the key from the table.
     * Does so using lazy deletion -- marks the KVPair as inactive.
     * Will be cleaned up on next table resize.
     */
    public void delete(K key) {

        // check key isn't null
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int index = getHashIndex(key);

        // sequentially proble table while the slots contain active KVPairs.
        // At each active KVPair, check if it matches the key -- if so,
        // update it to inactive and return.
        while (table[index] != null) {

            // see if the keys match
            if (table[index].key.equals(key)) {
                table[index].active = false;
                tombstones++;
                return;
            }
            index = ++index % table.length;
        }
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

}
