package concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final Queue<T> queue;
    public final int capacity;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void enqueue(T t) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait();
        }
        queue.add(t);
        notify();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T t = queue.poll();
        notify();
        return t;
    }

    public synchronized int size() {
        return queue.size();
    }
}
