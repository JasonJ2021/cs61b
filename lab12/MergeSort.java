import edu.princeton.cs.algs4.Queue;

import java.awt.desktop.QuitEvent;
import java.util.Iterator;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> queue = new Queue<>();
        for (Item item : items) {
            Queue<Item> eachOne = new Queue<>();
            eachOne.enqueue(item);
            queue.enqueue(eachOne);
        }
        return queue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> mergeQueue = new Queue<>();
//        Iterator<Item> q1Iterator = q1.iterator();
//        Iterator<Item> q2Iterator = q2.iterator();
//        Item item1 = null;
//        Item item2 = null;
//        while (q1Iterator.hasNext() || q2Iterator.hasNext() || item1 != null || item2 != null) {
//            if (item1 == null && q1Iterator.hasNext()) {
//                item1 = q1Iterator.next();
//            }
//            if (item2 == null && q2Iterator.hasNext()) {
//                item2 = q2Iterator.next();
//            }
//            if (item1 == null) {
//                mergeQueue.enqueue(item2);
//                item2 = null;
//            } else if (item2 == null) {
//                mergeQueue.enqueue(item1);
//                item1 = null;
//            } else {
//                if (item1.compareTo(item2) <= 0) {
//                    mergeQueue.enqueue(item1);
//                    item1 = null;
//                } else {
//                    mergeQueue.enqueue(item2);
//                    item2 = null;
//                }
//            }
//        }
        Item item = getMin(q1, q2);
        while (!q1.isEmpty() || !q2.isEmpty()) {
            mergeQueue.enqueue(item);
            item = getMin(q1, q2);
        }
        mergeQueue.enqueue(item);
        return mergeQueue;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if(items.isEmpty())return items;
        Queue<Queue<Item>> queue = makeSingleItemQueues(items);
        while (queue.size() > 1) {
            Queue<Item> q1 = queue.dequeue();
            Queue<Item> q2 = queue.dequeue();
            queue.enqueue(mergeSortedQueues(q1, q2));
        }
        return queue.dequeue();
    }


    public static void main(String[] args) {
        Queue<String> students1 = new Queue<>();
//        students1.enqueue("Alice");
//        students1.enqueue("Ethan");
//        students1.enqueue("Vanessa");
//        students1.enqueue("Black");
//        students1.enqueue("Dad");
//        students1.enqueue("Zack");

        System.out.println("==========Before Sort================");
        System.out.println(students1);
//        Queue<String> sortedQueue = MergeSort.mergeSort(students1);
//        System.out.println(sortedQueue);
//        System.out.println(makeSingleItemQueues(students1));
        Queue<String> sortedStudents = mergeSort(students1);
        System.out.println("==========After Sort=================");
        System.out.println(sortedStudents);
    }
}
