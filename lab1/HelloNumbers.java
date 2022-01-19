public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int sum =  x ; 
        while (x < 10) {
            System.out.print(sum + " ");
            x = x + 1;
            sum += x;
        }
    }
}
