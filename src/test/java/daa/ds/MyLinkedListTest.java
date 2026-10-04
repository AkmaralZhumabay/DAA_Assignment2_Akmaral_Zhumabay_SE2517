package daa.ds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    void addAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtFirstAndLastIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(0, 5);
        list.add(list.size(), 20);

        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(20, list.get(2));
    }

    @Test
    void removeAtIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(20, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void removeFirstAndLast() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(list.size() - 1));
        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void containsValuesAndDuplicates() {
        MyLinkedList list = new MyLinkedList();

        list.add(5);
        list.add(10);
        list.add(10);

        assertTrue(list.contains(5));
        assertTrue(list.contains(10));
        assertFalse(list.contains(99));
    }

    @Test
    void handlesOneElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(42);

        assertEquals(42, list.get(0));
        assertEquals(42, list.remove(0));
        assertEquals(0, list.size());
    }

    @Test
    void invalidIndexesThrowException() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 5));

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, 5));
    }

    @Test
    void metricsAreCounted() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        list.resetMetrics();
        assertEquals(30, list.get(2));
        assertEquals(2, list.getMetrics().getSteps());

        list.resetMetrics();
        assertTrue(list.contains(30));
        assertEquals(2, list.getMetrics().getSteps());
        assertEquals(3, list.getMetrics().getComparisons());
    }
}