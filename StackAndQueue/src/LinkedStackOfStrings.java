import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStackOfStrings implements Iterable<String> {
    private Node first;
    private int n;

    private class Node {
        private String item;
        private Node next;
    }

    public LinkedStackOfStrings() {
        n = 0;
        first = null;
    }

    public void push(String s) {
        Node newNode = new Node();
        newNode.item = s;
//        if (head == null) {
//            head = newNode;
//        }
//        else {
        Node oldNode = first;
        newNode.next = oldNode;
        first = newNode;
//        }
        n++;
    }

    public String pop() {
        // Boundary check
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        String dequeueString = first.item;
        first = first.next;
        n--;

        return dequeueString;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Iterator<String> iterator() { return new LinkedIterator(); }

    private class LinkedIterator implements Iterator<String> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public String next()
        {
            String item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {
        LinkedStackOfStrings stack = new LinkedStackOfStrings();
        int length = 20;
        for (int i = 0; i < length; i++) {
            stack.push(Integer.toString(i));
        }

        System.out.println("------iterable test--------");
        for (String s : stack) {
            System.out.print(s + " ");
        }
        System.out.println("\n----------------------\n");

        System.out.println("------pop test--------");
        for (int i = 0; i < length - 2; i++) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println("\n----------------------\n");

        System.out.println("The size of stack: " + stack.size());
        System.out.println("Is empty? " + stack.isEmpty());

        stack.push("100");
        stack.push("200");
        System.out.print(stack.pop() + " ");
        System.out.print(stack.pop() + " ");
        System.out.print(stack.pop() + " ");
        System.out.print(stack.pop() + " ");
        System.out.println();
        System.out.println("The size of stack: " + stack.size());
        System.out.println("Is empty? " + stack.isEmpty());

        // this line will throw java.util.NoSuchElementException
        System.out.print(stack.pop() + " ");

    }
}
