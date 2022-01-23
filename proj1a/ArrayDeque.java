public class ArrayDeque<T> {
    int size;
    T[] array;
    int nextFirst;
    int nextLast;

    /*
    if next Last > nextFirst
    array is full

    if size < 1/3 length
    then resize to 1/2 length
    * */
    public ArrayDeque() {
        size = 0;
        array = (T[]) new Object[8];
        nextLast = 0;
        nextFirst = array.length - 1;
    }

    private void resize(int capacity) {
        T[] newarray = (T[]) new Object[capacity];

        if(size == array.length || nextFirst > nextLast){
            /*
             * copy array[0] ~ array[nextLast - 1] to newarray[0]~ newarray[nextLast - 1]
             * copy array[nextFirst + 1] ~ array[array.lengh - 1] total array.length-nextFirst - 1 blocks to
             * newarray[newarray.length - 1 - array.length + nextFirst + 1 + 1] ~ newarray[newarray.length - 1]
             * for example :
             * */
            System.arraycopy(array,0,newarray , 0 , nextLast);
            System.arraycopy(array,nextFirst+1,newarray , newarray.length-array.length+nextFirst+1 , array.length - nextFirst - 1);
            nextFirst = nextFirst + newarray.length - array.length;
        }else{
            /*this situation
             *  nextFirst + 1 ~ nextLast - 1  hava data blocks
             *  only need to copy array[0] ~ a[nextLast - 1]
             * */
            System.arraycopy(array, 0 , newarray , 0 , nextLast );
        }

        array = newarray;
    }

    public void addFirst(T item) {
        if(size == array.length){
            resize(2*array.length);
        }
        array[nextFirst] = item;
        nextFirst = (nextFirst - 1) % array.length;
        size++;
    }

    public void addLast(T item) {
        if(size == array.length){
            resize(2*array.length);
        }
        array[nextLast] = item;
        nextLast = (nextLast + 1) % array.length;
        size++;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int count = size;
        int i = (nextFirst + 1) % array.length;
        while (count > 0) {
            System.out.print(array[i] + " ");
            i = (i + 1) % array.length;
            count--;
        }
        System.out.println();
    }

    public T removeFirst() {
        /*there should be a resize*/
        if(size < array.length / 3){
            resize(array.length / 2);
        }
        T item = array[(nextFirst + 1) % array.length];
        array[(nextFirst + 1) % array.length] = null;
        nextFirst = (nextFirst + 1) % array.length;
        size--;
        return item;
    }

    public T removeLast() {
        /*there should be a resize*/
        if(size < array.length / 3){
            resize(array.length / 2);
        }
        T item = array[(nextLast - 1) % array.length];
        array[(nextLast - 1) % array.length] = null;
        nextLast = (nextLast - 1) % array.length;
        size--;
        return item;
    }

    public T get(int index) {
        return array[index];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.printDeque();
    }
}
