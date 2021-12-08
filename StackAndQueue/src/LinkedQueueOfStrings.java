import java.util.NoSuchElementException;

public class LinkedQueueOfStrings {
    private int n;
    // Q. Why we need the variable "last"?
    // A. 我们要将新的item加入到链表尾，所以需要"last"，否则需要从"first"开始遍历都最后一个Node，当链表很长时，时间会久。
    private Node first, last;

    private class Node {
        private String item;
        private Node next;
    }

    public LinkedQueueOfStrings() {
        n = 0;
        first = null;
        last = null;
    }

    // Deal with "last". Need to consider when the queue is empty.
    public void enqueue(String item) {
        Node newNode = new Node();
        newNode.item = item;
        if (last != null) {
            // let the last old Node pointing to the newNode. Then let "last" pointing to the newNode.
            last.next = newNode;
        }
        last = newNode;

        if (first == null) {
            first = last;
        }

        n++;
    }

    // Deal with "first".
    public String dequeue() {
        // Boundary check
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        String returnItem = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }

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
        LinkedQueueOfStrings queue = new LinkedQueueOfStrings();
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
