public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 1) {
            char a = deque.removeFirst();
            char b = deque.removeLast();
            if (a - b != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator c) {
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 1) {
            char a = deque.removeFirst();
            char b = deque.removeLast();
            if (!c.equalChars(a, b)) {
                return false;
            }
        }
        return true;
    }
}
