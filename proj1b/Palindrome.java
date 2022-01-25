public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word){
        if(word.length() == 0 || word.length() == 1){
            return true;
        }
        for(int i = 0 ; i < word.length() ; i++){
            if(word.charAt(i) != word.charAt(word.length() - i - 1)){
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome(String word , CharacterComparator c){
        Deque<Character> deque = wordToDeque(word);
        while(deque.size() > 1){
            char a = deque.removeFirst();
            char b = deque.removeLast();
            if(!c.equalChars(a,b)){
                return false;
            }
        }
        return true;
    }
}
