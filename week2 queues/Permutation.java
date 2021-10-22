/* *****************************************************************************
 *  Name: RunjieHuang
 *  Date: 25th July, 2021
 *  Description: Coursera Algorithm mooc week2 homework
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String currentInput = StdIn.readString();
            randomQueue.enqueue(currentInput);
        }

        for (int i = 0; i < k; i++) {
            String currentDeque = randomQueue.dequeue();
            StdOut.println(currentDeque);
        }
    }
}
