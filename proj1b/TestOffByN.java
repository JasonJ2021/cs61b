import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {
    @Test
    public void testoffByN1(){
        OffByN o = new OffByN(5);
        assertTrue(o.equalChars('a','f'));
        assertTrue(o.equalChars('f','a'));
        assertFalse(o.equalChars('f','h'));
    }
}
