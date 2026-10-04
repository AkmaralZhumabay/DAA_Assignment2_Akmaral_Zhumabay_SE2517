package daa.ds;

import daa.metrics.Metrics;

public class MinHeap {

    private static final int DEFAULT_CAPACITY = 10;

    private int[] heap;
    private int size;
    private final Metrics metrics;

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        heap = new int[initialCapacity];
        size = 0;
        metrics = new Metrics();
    }

    public void insert(int value) {
        ensureCapacity();

        heap[size] = value;
        size++;
        bubbleUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        metrics.step();
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap[0];
        metrics.step();

        if (size == 1) {
            size = 0;
            return min;
        }

        heap[0] = heap[size - 1];
        metrics.step();
        metrics.move();

        size--;
        bubbleDown(0);

        return min;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.step();
            int parentValue = heap[parent];

            metrics.step();
            int currentValue = heap[index];

            metrics.comparison();
            if (parentValue <= currentValue) {
                break;
            }

            heap[parent] = currentValue;
            heap[index] = parentValue;
            metrics.move();
            metrics.move();

            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.step();
                int leftValue = heap[left];

                metrics.step();
                int smallestValue = heap[smallest];

                metrics.comparison();
                if (leftValue < smallestValue) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.step();
                int rightValue = heap[right];

                metrics.step();
                int smallestValue = heap[smallest];

                metrics.comparison();
                if (rightValue < smallestValue) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            metrics.step();
            int currentValue = heap[index];

            metrics.step();
            int smallestValue = heap[smallest];

            heap[index] = smallestValue;
            heap[smallest] = currentValue;
            metrics.move();
            metrics.move();

            index = smallest;
        }
    }

    private void ensureCapacity() {
        if (size < heap.length) {
            return;
        }

        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
            metrics.step();
            metrics.move();
        }

        heap = newHeap;
    }

    boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[i] > heap[left]) {
                return false;
            }

            if (right < size && heap[i] > heap[right]) {
                return false;
            }
        }

        return true;
    }
}