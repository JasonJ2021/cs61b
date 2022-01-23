import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testEqual(){
        Integer a = 128;
        Integer b = 128;
        assertTrue("Integer a = 128 != b = 128" ,Flik.isSameNumber(a,b));
    }
}
