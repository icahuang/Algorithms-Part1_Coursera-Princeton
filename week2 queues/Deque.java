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
            current = current.prev;
            return item;
        }
    }

    // 此版本中，first在右侧，last在左侧
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
        first.prev = oldFirst;
        first.next = null;

        if (isEmpty()) last = first;
        else oldFirst.next = first;

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = null;
        last.next = oldLast;

        if (isEmpty()) first = last;
        else oldLast.prev = last;

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.prev;
        if (first != null) {
            first.next = null;
        } else {
            // when (first == null), the whole deque is empty. So we need to set "last" to null to remove the element
            last = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.next;
        if (last != null) {
            last.prev = null;
        } else {
            // when (last == null), the whole deque is empty. So we need to set "first" to null to remove the element
            first = null;
        }
        n--;
        return item;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(11);
        int re = deque.removeLast();
        System.out.println(re);
        deque.addLast(22);
        deque.removeLast();

        int n = 10;
        for (int i = 0; i < n; i++)
            deque.addFirst(i);
        StdOut.println("Is the deque empty? : " + deque.isEmpty());
        StdOut.println("What's the size of the queue? : " + deque.size());
        StdOut.println();

        for (int i : deque) {
            StdOut.print(i + " ");
        }
        StdOut.println();

        for (int i = 0; i < n / 2; i++) {
            StdOut.println(deque.removeFirst());
            StdOut.println(deque.removeLast());
        }

        StdOut.println("What's the size of the queue? : " + deque.size());
        StdOut.println("Is the deque empty? : " + deque.isEmpty());
    }
}
