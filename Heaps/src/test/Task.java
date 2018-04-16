package test;

public class Task implements Comparable<Task> {

    // fields
    private Priority priority;
    private int rank;
    private String description;

    public Task(Priority priority, int rank, String desc) {
        this.priority = priority;
        this.rank = (rank > 9) ? 9 : ( (rank < 0) ? 0 : rank);  // nested ternary operators
        this.description = desc;
    }

    public int compareTo(Task other) {
        int result = 0;
        if (this.getPriority() < other.getPriority()) {
            result = -1;
        } else if (this.getPriority() > other.getPriority()) {
            result = 1;
        }
        return result;
    }

    public int getPriority() {
        return priority.getNumericPriority() * 10 + rank;
    }

    public String toString() {
        return priority.toString() + rank + ": " + description;
    }

}
