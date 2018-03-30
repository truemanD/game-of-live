package gameoflive.dao.binary;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class MyBitFrame {

    private int fieldSize;
    private BitSet frame_mask;

    public MyBitFrame(int newFieldSize) {
        fieldSize = newFieldSize;
        frame_mask = new BitSet(fieldSize * fieldSize);
        int idx = 7;
        if (fieldSize * fieldSize % 8 == 0) {
            idx = 1;
        }
        frame_mask.set(((fieldSize * fieldSize) + idx), true);
    }

    public void set(int x, int y, boolean flag) {
        frame_mask.set(bit_index(x, y), flag);
    }

    public void set(int x, int y) {
        frame_mask.set(bit_index(x, y));
    }

    public void set(byte[] bytearray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytearray);
        for (int i = 0; i < bytearray.length; i++) {
            byte thebyte = byteBuffer.get(i);
            for (int j = 0; j < 8; j++) {
                frame_mask.set(i * 8 + j, isBitSet(thebyte, j));
            }
        }
    }

    public void clear(int x, int y) {
        frame_mask.set(bit_index(x, y), false);
    }

    private int bit_index(int x, int y) {
        if (x >= fieldSize || y >= fieldSize) {
            return -1;
        }
        return y * fieldSize + x;
    }

    public boolean getValue(int x, int y) {
        if (x >= fieldSize || y >= fieldSize) {
            return false;
        }
        return frame_mask.get(bit_index(x, y));
    }

    public BitSet getFrame_mask() {
        return frame_mask;
    }

    private static Boolean isBitSet(byte b, int bit) {
        return (b & (1 << bit)) != 0;
    }

    public void render() {

        for (int i = 0; i < fieldSize * fieldSize; i++) {
            if (i != 0 && i % fieldSize == 0) {
                System.out.println("");
            }
            System.out.print(frame_mask.get(i) ? "X" : "-"); // "X":"0"
            System.out.print(" ");
        }

        System.out.println(String.format("\n\r%0" + fieldSize + "d", 0).replace("0", "_"));
        System.out.println("total bits:" + frame_mask.length());
        System.out.println("total bytes:" + frame_mask.toByteArray().length);
        System.out.println("total bytes:" + frame_mask.toString());
    }
}
