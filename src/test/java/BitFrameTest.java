import gameoflive.dao.binary.MyBitFrame;
import org.junit.Assert;
import org.junit.Test;

public class BitFrameTest {

    @Test
    public void test() {
        int length = 3;
        MyBitFrame frame = new MyBitFrame(length);
        frame.set(0, 0, true);
        frame.render();
        Assert.assertEquals(frame.getFrame_mask().toByteArray().length, length);
        if (frame.getFrame_mask().toByteArray().length == length) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
}
