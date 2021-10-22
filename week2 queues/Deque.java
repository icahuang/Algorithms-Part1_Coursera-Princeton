/* *****************************************************************************
 *  Name: RunjieHuang
 *  Date: 25th July, 2021
 *  Description: Coursera Algorithm mooc week2 homework
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int n;  // record the number of elements

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;

        if (isEmpty()) last = first;
        else oldFirst.prev = first;

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;

        if (isEmpty()) first = last;
        else oldLast.next = last;

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        n--;
        return item;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        int n = 10;
        for (int i = 0; i < n; i++)
            deque.addFirst(i);
        StdOut.println("Is the deque empty? : " + deque.isEmpty());
        StdOut.println("What's the size of the queue? : " + deque.size());

        for (int i = 0; i < n; i++)
            StdOut.println(deque.removeLast());

        StdOut.println("What's the size of the queue? : " + deque.size());
        StdOut.println("Is the deque empty? : " + deque.isEmpty());

        String a;
    }
}
