import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static OffByOne offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindrome1() {
        String a = "aba";
        assertTrue(palindrome.isPalindrome(a));
    }

    @Test
    public void testPalindrome2() {
        String a = "cat";
        assertFalse(palindrome.isPalindrome(a));
    }

    @Test
    public void testPalindrome3() {
        String a = "noon";
        assertTrue(palindrome.isPalindrome(a));
    }

    @Test
    public void testPalindrome4() {
        String a = "racecar";
        assertTrue(palindrome.isPalindrome(a));
    }

    @Test
    public void testPalindrome5() {
        String a = "a";
        assertTrue(palindrome.isPalindrome(a, offByOne));
    }

    @Test
    public void testPalindrome6() {
        String a = "cac";
        assertFalse(palindrome.isPalindrome(a, offByOne));
    }

    @Test
    public void testPalindrome7() {
        String a = "acdb";
        assertTrue(palindrome.isPalindrome(a, offByOne));
    }

    @Test
    public void testPalindrome8() {
        String a = "fhig";
        assertTrue(palindrome.isPalindrome(a, offByOne));
    }
}
