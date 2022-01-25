import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.

    @Test
    public void testOffByone1() {
        char a = '1';
        char b = '2';
        assertFalse(offByOne.equalChars(a, b));
    }

    @Test
    public void testOffByone2() {
        char a = 'a';
        char b = 'a';
        assertTrue(offByOne.equalChars(a, b));
    }

    @Test
    public void testOffByone3() {
        char a = '%';
        char b = '%';
        assertTrue(offByOne.equalChars(a, b));
    }
}
