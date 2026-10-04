package daa.ds;

import daa.metrics.Metrics;

public class MyLinkedList implements IntList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Metrics metrics;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
        metrics = new Metrics();
    }

    @Override
    public void add(int value) {
        Node newNode = new Node(value);

        if (size == 0) {
            head = newNode;
            tail = newNode;
            metrics.move();
            metrics.move();
        } else {
            tail.next = newNode;
            tail = newNode;
            metrics.move();
            metrics.move();
        }

        size++;
    }

    @Override
    public void add(int index, int value) {
        checkPositionIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
            metrics.move();
            metrics.move();

            if (size == 0) {
                tail = newNode;
                metrics.move();
            }

            size++;
            return;
        }

        Node previous = nodeAt(index - 1);

        newNode.next = previous.next;
        previous.next = newNode;
        metrics.move();
        metrics.move();

        size++;
    }

    @Override
    public int remove(int index) {
        checkElementIndex(index);

        if (index == 0) {
            int removedValue = head.value;

            head = head.next;
            metrics.step();
            metrics.move();

            size--;

            if (size == 0) {
                tail = null;
                metrics.move();
            }

            return removedValue;
        }

        Node previous = nodeAt(index - 1);
        Node removed = previous.next;
        metrics.step();

        previous.next = removed.next;
        metrics.move();

        if (removed == tail) {
            tail = previous;
            metrics.move();
        }

        size--;
        return removed.value;
    }

    @Override
    public int get(int index) {
        checkElementIndex(index);
        return nodeAt(index).value;
    }

    @Override
    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            metrics.comparison();

            if (current.value == value) {
                return true;
            }

            current = current.next;
            metrics.step();
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

    private Node nodeAt(int index) {
        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.step();
        }

        return current;
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