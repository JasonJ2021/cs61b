public class LinkedListDeque<T> {
    private int size;
    private ListNode sentinel;

    private class ListNode {
        T element;
        ListNode next;
        ListNode prev;

    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new ListNode();
        sentinel.element = null;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(T item) {
        ListNode node = new ListNode();
        node.element = item;
        node.next = sentinel.next;
        node.prev = sentinel;
        sentinel.next.prev = node;
        sentinel.next = node;
        size++;
    }

    public void addLast(T item) {
        ListNode node = new ListNode();
        node.element = item;
        node.next = sentinel;
        node.prev = sentinel.prev;
        sentinel.prev.next = node;
        sentinel.prev = node;
        size++;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        ListNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.element + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        ListNode first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size--;
        return first.element;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        ListNode last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        size--;
        return last.element;
    }

    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        ListNode p = sentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.element;
    }

    private T getHelper(int index, ListNode cur) {
        if (index == 0) {
            return cur.element;
        }
        return getHelper(index - 1, cur.next);
    }

    public T getRecursive(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        return getHelper(index, sentinel.next);
    }
}
