package daa.ds;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void insertAndPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(3, heap.size());
        assertEquals(10, heap.peekMin());
    }

    @Test
    void extractMinReturnsSmallest() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.peekMin());
        assertEquals(2, heap.size());
    }

    @Test
    void handlesOneElement() {
        MinHeap heap = new MinHeap();

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void handlesDuplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void emptyHeapThrowsException() {
        MinHeap heap = new MinHeap();

        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void growsWhenFull() {
        MinHeap heap = new MinHeap(2);

        heap.insert(5);
        heap.insert(3);
        heap.insert(1);

        assertEquals(3, heap.size());
        assertEquals(1, heap.peekMin());
    }

    @Test
    void heapPropertyHoldsAfterEveryInsert() {
        MinHeap heap = new MinHeap();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            heap.insert(random.nextInt());
            assertTrue(heap.isValidHeap());
        }
    }

    @Test
    void heapPropertyHoldsAfterEveryExtract() {
        MinHeap heap = new MinHeap();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            heap.insert(random.nextInt());
        }

        while (!heap.isEmpty()) {
            heap.extractMin();
            assertTrue(heap.isValidHeap());
        }
    }

    @Test
    void extractedValuesAreNonDecreasing() {
        MinHeap heap = new MinHeap();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            heap.insert(random.nextInt());
        }

        int previous = heap.extractMin();

        while (!heap.isEmpty()) {
            int current = heap.extractMin();
            assertTrue(previous <= current);
            previous = current;
        }
    }

    @Test
    void buildHeapCreatesValidHeap() {
        MinHeap heap = new MinHeap();

        int[] data = {9, 4, 7, 1, 3, 6, 2, 8, 5};

        heap.buildHeap(data);

        assertEquals(data.length, heap.size());
        assertTrue(heap.isValidHeap());
        assertEquals(1, heap.peekMin());
    }

    @Test
    void buildHeapProducesSortedExtraction() {
        MinHeap heap = new MinHeap();
        Random random = new Random(42);

        int[] data = new int[1000];

        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }

        heap.buildHeap(data);

        int previous = heap.extractMin();

        while (!heap.isEmpty()) {
            int current = heap.extractMin();
            assertTrue(previous <= current);
            previous = current;
        }
    }
}