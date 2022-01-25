import org.junit.Test;

import static org.junit.Assert.*;

public class TestArrayDequeGold {
    /**
     * random < 1 -- addFirst;
     * random < 2 -- addLast;
     * random < 3 -- removeFirst;
     * random < 4 -- removeLast;
     * <p>
     * 1 represent addFirst
     * 2 represent addLast
     * 3 represent removeFirst
     * 4 represent removeLast
     */
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();

        ArrayDequeSolution<Integer> operations = new ArrayDequeSolution<>();
        int i = 0;
        String message = "";
        while (true) {
            i++;
            double random = StdRandom.uniform(4);
            if (random < 1) {
                stu.addFirst(i);
                sol.addFirst(i);
                message = message + "addFirst(" + i + ")\n";
            } else if (random < 2) {
                stu.addLast(i);
                stu.addLast(i);
                message = message + "addLast(" + i + ")\n";
            } else if (random < 3) {
                if (!stu.isEmpty() && !sol.isEmpty()) {
                    int a = stu.removeFirst();
                    int b = sol.removeFirst();
                    message = message + "removeFirst()\n";
                    assertEquals(message, a, b);
                }
            } else {
                if (!stu.isEmpty() && !sol.isEmpty()) {
                    int a = stu.removeLast();
                    int b = sol.removeLast();
                    message = message + "removeLast()\n";
                    assertEquals(message, a, b);
                }
            }
        }
    }
}
