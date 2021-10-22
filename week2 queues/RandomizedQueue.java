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
    private int first;      // index of first element of queue, in the front of the array at the beginning
    private int last;       // index of next available slot

    // need to add a index to ...
    private int nullIndex = 0;

    // construct an empty randomized queue ✅
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        nullIndex = 0;
        first = 0;
        last = 0;
    }

    // is the randomized queue empty? ✅
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue ✅
    public int size() {
        return n;
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
            if (q[(first + qIndex) % q.length] != null) {
                copy[copyCount] = q[(first + qIndex) % q.length];
                copyCount++;
            }
            qIndex++;
        }

        q = copy;
        first = 0;
        last = n;
    }

    // add the item ✅
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("The input item is null.");

        q[last++] = item;
        if (last == q.length) last = 0;     // wrap-around
        n++;
        if (n == q.length) resize(2 * q.length);
    }

    // remove and return a random item ✅
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("No element in queue.");

        // Item returnItem = q[first];
        // q[first] = null;
        // first++;
        // if (first == q.length) first = 0;

        // int index = (int) Math.random() * n;
        int index = StdRandom.uniform(q.length);
        Item returnItem = q[index];
        while (returnItem == null) {
            index = (index + 1) % q.length;
            returnItem = q[index];
        }
        q[index] = null;
        n--;

        // resize
        if (n > 0 && n == q.length / 4) resize(q.length / 2);

        return returnItem;
    }

    // return a random item (but do not remove it) ✅
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("No element in queue.");

        // Need to make sure that index doesn't point to a null element in q[]
        // int index = (int) Math.random() * q.length;
        int index = StdRandom.uniform(q.length);
        Item returnItem = q[index];
        while (returnItem == null) {
            index = (index + 1) % q.length;
            returnItem = q[index];
        }
        return returnItem;
    }

    // return an independent iterator over items in random order ✅
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
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        /*
            It should produce a result like the following:
            0-2 0-1 0-3 0-0 0-4
            1-2 1-1 1-4 1-0 1-3
            2-1 2-4 2-0 2-3 2-2
            4-2 4-3 4-4 4-1 4-0
            3-2 3-3 3-1 3-0 3-4
        */
    }
}
