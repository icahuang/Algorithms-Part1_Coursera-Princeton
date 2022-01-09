/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

public class InTest {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();

        while(!in.isEmpty()) {
            System.out.println(in.readInt() + " ");
        }
    }
}
