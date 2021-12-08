import java.util.NoSuchElementException;

public class ResizingArrayQueueOfStrings {
    private int n;
    private String[] s;
    // "first": pointing to the first in item. (At the left of the array compared to "last")
    // "last": pointing to the last in item
    private int first, last;

    public ResizingArrayQueueOfStrings() {
        n = 0;
        s = new String[1];
        first = 0;
        last = 0;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = s[(first + i) % s.length];
        }
        s = copy;
        first = 0;
        last = n;
    }

    public void enqueue(String item) {
        if (n == s.length) {
            resize(2 * s.length);
        }
        s[last] = item;
        last = (last + 1) % s.length;

        n++;
    }

    public String dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (n > 0 && n == s.length / 4) {
            resize(s.length / 2);
        }

        String returnItem = s[first];
        s[first] = null;
        first = (first + 1) % s.length;

        n--;
        return returnItem;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    static public void main(String[] args) {
        ResizingArrayQueueOfStrings queue = new ResizingArrayQueueOfStrings();
        int length = 20;
        for (int i = 0; i < length; i++) {
            queue.enqueue(Integer.toString(i));
        }
        for (int i = 0; i < length - 2; i++) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();
        System.out.println("The size of queue: " + queue.size());
        System.out.println("Is empty? " + queue.isEmpty());

        queue.enqueue("100");
        queue.enqueue("200");
        System.out.print(queue.dequeue() + " ");
        System.out.print(queue.dequeue() + " ");
        System.out.print(queue.dequeue() + " ");
        System.out.print(queue.dequeue() + " ");
        System.out.println();
        System.out.println("The size of queue: " + queue.size());
        System.out.println("Is empty? " + queue.isEmpty());

        // Will output a NoSuchElementException. It's expected.
        System.out.print(queue.dequeue() + " ");

        /* Output:
        0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
        The size of queue: 2
        Is empty? false
        18 19 100 200
        The size of queue: 0
        Is empty? true
        Exception in thread "main" java.util.NoSuchElementException
        at ResizingArrayQueueOfStrings.dequeue(ResizingArrayQueueOfStrings.java:39)
        at ResizingArrayQueueOfStrings.main(ResizingArrayQueueOfStrings.java:85)
        */
    }
}
