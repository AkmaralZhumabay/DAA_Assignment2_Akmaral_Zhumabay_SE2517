package daa.ds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void addAndGet() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void growsWhenFull() {
        DynamicArray array = new DynamicArray(2);

        array.add(1);
        array.add(2);
        array.add(3);

        assertEquals(3, array.size());
        assertEquals(3, array.get(2));
    }

    @Test
    void addAtIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void addAtFirstAndLastIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(0, 5);
        array.add(array.size(), 20);

        assertEquals(5, array.get(0));
        assertEquals(10, array.get(1));
        assertEquals(20, array.get(2));
    }

    @Test
    void removeAtIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(20, array.remove(1));
        assertEquals(2, array.size());
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    @Test
    void containsValues() {
        DynamicArray array = new DynamicArray();

        array.add(5);
        array.add(10);
        array.add(10);

        assertTrue(array.contains(5));
        assertTrue(array.contains(10));
        assertFalse(array.contains(99));
    }

    @Test
    void handlesOneElement() {
        DynamicArray array = new DynamicArray();

        array.add(42);

        assertEquals(42, array.get(0));
        assertEquals(42, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void invalidIndexesThrowException() {
        DynamicArray array = new DynamicArray();

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 5));

        array.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(2, 5));
    }

    @Test
    void metricsAreCounted() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        array.resetMetrics();
        array.get(1);

        assertEquals(1, array.getMetrics().getSteps());

        array.resetMetrics();
        array.contains(20);

        assertEquals(2, array.getMetrics().getSteps());
        assertEquals(2, array.getMetrics().getComparisons());
    }
}