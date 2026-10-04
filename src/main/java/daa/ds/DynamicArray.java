package daa.ds;

import daa.metrics.Metrics;

public class DynamicArray implements IntList {

    private static final int DEFAULT_CAPACITY = 10;

    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        data = new int[initialCapacity];
        size = 0;
        metrics = new Metrics();
    }

    @Override
    public void add(int value) {
        ensureCapacity();

        data[size] = value;
        size++;
    }

    @Override
    public void add(int index, int value) {
        checkPositionIndex(index);
        ensureCapacity();

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.step();
            metrics.move();
        }

        data[index] = value;
        size++;
    }

    @Override
    public int remove(int index) {
        checkElementIndex(index);

        int removed = data[index];
        metrics.step();

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.step();
            metrics.move();
        }

        size--;
        return removed;
    }

    @Override
    public int get(int index) {
        checkElementIndex(index);

        metrics.step();
        return data[index];
    }

    @Override
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            metrics.step();
            metrics.comparison();

            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }

    private void ensureCapacity() {
        if (size < data.length) {
            return;
        }

        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            metrics.step();
            metrics.move();
        }

        data = newData;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }
}