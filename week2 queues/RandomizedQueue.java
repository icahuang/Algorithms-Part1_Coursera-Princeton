/* *****************************************************************************
 *  Name: RunjieHuang
 *  Date: 25th July, 2021
 *  Description: Coursera Algorithm mooc week2 homework
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] q;       // ATTENTION: the length of q is not always equal to "n"
    private int n;          // number of elements in queue

    private int currentEmptyIndex;  // need to add a index pointing to the empty box in array

    // construct an empty randomized queue ✅
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        currentEmptyIndex = 0;
    }

    // is the randomized queue empty? ✅
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue ✅
    public int size() {
        return n;
    }

    // associate function for enqueue(Item item)
    private void updateCurrentEmptyIndex() {
        while (q[currentEmptyIndex] != null) {
            currentEmptyIndex = (currentEmptyIndex + 1) % q.length;
        }
    }

    // associate function for dequeue() and sample()
    private int findRandomIndex() {
        int index = StdRandom.uniform(q.length);
        while (q[index] == null) {
            index = (index + 1) % q.length;
        }

        return index;
    }

    // resize the queue ✅
    private void resize(int newCapacity) {
        assert newCapacity >= n;

        Item[] copy = (Item[]) new Object[newCapacity];

        // for (int i = 0; i < n; i++)
        //     copy[i] = q[(first + i) % q.length];

        int copyCount = 0;      // index of copy[]
        int qIndex = 0;      // to access all elements in q[]
        while (copyCount < n) {
            if (q[qIndex] != null) {
                copy[copyCount] = q[qIndex];
                copyCount++;
            }
            qIndex++;
        }

        q = copy;
    }

    // add the item ✅
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("The input item is null.");

        q[currentEmptyIndex] = item;
        n++;

        if (n == q.length) {
            resize(q.length * 2);
            currentEmptyIndex = n;
        } else {
            updateCurrentEmptyIndex();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("No element in queue.");

        // int index = (int) Math.random() * n;
        // int index = StdRandom.uniform(q.length);

        int index = findRandomIndex();

        Item returnItem = q[index];
        q[index] = null;
        n--;

        // resize
        if (n > 0 && n == q.length / 4) resize(q.length / 2);

        return returnItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("No element in queue.");

        // int index = (int) Math.random() * q.length;
        // int index = StdRandom.uniform(q.length);

        int index = findRandomIndex();

        return q[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int count = 0;
        Item[] iteratorQueue = q.clone();

        public boolean hasNext() { return count < n; }

        public void remove() { throw new UnsupportedOperationException(); }

        // need to change to randomly output the whole queue
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item outputItem = null;

            int index = StdRandom.uniform(iteratorQueue.length);
            outputItem = iteratorQueue[index];
            while (outputItem == null) {
                index = (index + 1) % iteratorQueue.length;
                outputItem = iteratorQueue[index];
            }
            iteratorQueue[index] = null;
            count++;

            return outputItem;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue1 = new RandomizedQueue<Integer>();

        for (int i = 0; i < 100; i++) {
            queue1.enqueue(i);
        }


        for (int i = 0; i < 100; i++) {
            // if (i % 3 == 0) {
                System.out.print(queue1.dequeue() + "." + "(i=" + i +")" + " ");
                // System.out.print(queue1.sample() + ", ");
            // }
        }
        System.out.println();
        System.out.println("the size of queue: " + queue1.size());

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        int n = 5;
        // test iterator
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }
        for (int a : queue) {
            for (int b : queue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
        /*
            It should produce a result like the following:(but the order might be different.)
            0-2 0-1 0-3 0-0 0-4
            1-2 1-1 1-4 1-0 1-3
            2-1 2-4 2-0 2-3 2-2
            4-2 4-3 4-4 4-1 4-0
            3-2 3-3 3-1 3-0 3-4
        */
    }
}