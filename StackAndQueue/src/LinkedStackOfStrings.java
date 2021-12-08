import java.util.NoSuchElementException;

public class LinkedStackOfStrings {
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

    public static void main(String[] args) {
	    LinkedStackOfStrings stack = new LinkedStackOfStrings();
	    int length = 20;
	    for (int i = 0; i < length; i++) {
	        stack.push(Integer.toString(i));
	    }
	    for (int i = 0; i < length - 2; i++) {
	        System.out.print(stack.pop() + " ");
        }
        System.out.println();
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
        System.out.print(stack.pop() + " ");

    }
}
