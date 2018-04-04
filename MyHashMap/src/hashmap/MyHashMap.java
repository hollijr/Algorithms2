package hashmap;

/**
 * HashTable that uses linear probing for collision resolution
 * @param <K> key  (for example, flight numbers)
 * @param <V> value  (for example, flight data)
 */
public class MyHashMap<K, V> {

    // constants for managing the table
    private static final double LOAD_FACTOR = 0.7;
    private static final int INITIAL_TABLE_SIZE = 13;
    private static final double RESIZE_FACTOR = 2;

    private KVPair[] table;

    private int size; // number of actual active elements in table
    private int usedSpaces; // number of spaces that are active or inactive

    // constructor
    public MyHashMap() {
        this(INITIAL_TABLE_SIZE);
    }

    public MyHashMap(int tableSize) {
        table = new KVPair[tableSize];
    }

    // get size of table
    public int size() {
        return size;
    }

    // is table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;  // reset the active elements tracker
        usedSpaces = 0;  // reset the all elements tracker
        table = new KVPair[INITIAL_TABLE_SIZE];
    }

    // overload for users who want to specify a starting table size
    public void clear(int tableSize) {
        size = 0;  // reset the active elements tracker
        usedSpaces = 0;  // reset the all elements tracker
        table = new KVPair[tableSize];
    }


    // add using linear probing
    public boolean add(K key, V value) {
        // check load factor to see if table needs to be resized
        if (tableSizeExceedsLF()) {
            rehash();
        }

        int code = key.hashCode();
        int index = code % table.length;

        if (table[index] == null) {
            System.out.println("Found a space!");
        } else {
            System.out.println("Collision!");
        }

        // check the slot to see if it already contains the key
        int indexFound = find(key);

        if (indexFound >= 0) {
            table[indexFound].value = value;  // update old value
            return false;
        }

        while (table[index] != null || table[index].active) {

            index = ++index % table.length;
        }

        table[index] = new KVPair(key, value);
        size++;
        usedSpaces++;
        return true;
    }

    public int find(K key) {

        if (tableUsageExceedsLF()) {
            rehash();
        }

        int code = key.hashCode();
        int index = code % table.length;

        while (table[index] != null) {

            // is this index where the key resides?
            if (table[index].key.equals(key)) {
                return index;
            }

            index = ++index % table.length ;  // otherwise, look at next slot
        }

        return -1;
    }

    // for adds
    public boolean tableSizeExceedsLF() {
        return (double) size / table.length >= LOAD_FACTOR;
    }

    // for removes
    public boolean tableUsageExceedsLF() {
        return (double) usedSpaces / table.length >= LOAD_FACTOR;
    }

    // resize the table
    private void rehash() {
        //TODO
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
