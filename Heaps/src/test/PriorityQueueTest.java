package test;

import heaps.BinaryHeap;
import heaps.IPriorityQueue;

public class PriorityQueueTest {

    public static void main(String[] args) {

        // mock data
        Task[] tasks = {
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

        IPriorityQueue<Task> pq = new BinaryHeap<>();

        // load in all our tasks
        for (Task task : tasks) {
            pq.insert(task);
        }

        // check the results
        for (Task t : pq) {
            System.out.println(t);
        }

    }
}
