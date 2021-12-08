import java.util.Stack;

public class JavaStackTest {
    private static Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) {
        boolean isEmpty = stack.isEmpty();
        boolean empty = stack.empty();
        System.out.println("isEmpty: " + isEmpty);
        System.out.println("empty: " + empty);
    }
}
