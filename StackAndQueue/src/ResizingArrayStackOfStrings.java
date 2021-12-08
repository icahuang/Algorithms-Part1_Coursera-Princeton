import java.util.NoSuchElementException;

public class ResizingArrayStackOfStrings {
    // n is not only the quantity of String in Array s, but also s[n] points to the next first null in Array s.
    private int n;
    private String[] s;

    public ResizingArrayStackOfStrings() {
        s = new String[1];
        n = 0;
    }

    public void push(String item) {
        if (n == s.length) {
            resize(2 * s.length);
        }

        s[n] = item;
        n++;
    }

    public String pop() {
        if (n <= 0) {
            throw new NoSuchElementException();
        }

        n--;
        String popItem = s[n];
        s[n] = null;

        if (n > 0 && n == s.length / 4) {
            resize(s.length / 2);
        }

        return popItem;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public int size() { return n; }

    public boolean isEmpty() {
        return n == 0;
    }

    public static void main(String[] args) {
        ResizingArrayStackOfStrings stack = new ResizingArrayStackOfStrings();

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

        // Will output a NoSuchElementException. It's expected.
        System.out.print(stack.pop() + " ");

        /*
        Expected Output:
        19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
        The size of stack: 2
        Is empty? false
        200 100 1 0
        The size of stack: 0
        Is empty? true
        Exception in thread "main" java.util.NoSuchElementException
        */
    }
}
