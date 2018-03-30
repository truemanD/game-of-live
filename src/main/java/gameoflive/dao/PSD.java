package gameoflive.dao;

import gameoflive.PlayingFieldIface;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by bogdanov-an on 08.11.2017.
 */
public class PSD implements PlayingFieldDAO {


    private AtomicBoolean firstSave = new AtomicBoolean(true);
    private final  DataOutputStream dos;
    final int x_size;
    final int y_size;

    public PSD(File file, int x_size, int y_size) {
        try {
            this.x_size = x_size;
            this.y_size = y_size;
            dos = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            throw  new  RuntimeException(e);
        }
    }

    @Override
    public int saveCurrentDay(PlayingFieldIface pf) {

        try {
            check(pf);
            if (firstSave.get()) {
                writeHeader(x_size, y_size);
                firstSave.set(false);
            }

            BitSet bs = new BitSet(x_size * y_size);
            for (int x = 0; x < pf.getField().size(); x++) {
                for (int y = 0; y < pf.getField().get(0).size(); y++) {
                    if (pf.getField().get(x).get(y)) bs.set(x_size * y + x);
                }
            }


            writeBitset(bs);

            return 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<List<Boolean>> loadDay(int dayNumber) {
        return null;
    }

    private void check(PlayingFieldIface pf) {
        if(pf.getField().size() != x_size || pf.getField().get(0).size() != y_size) throw new RuntimeException("wrong dimension");
    }

    private void writeHeader(int x_size, int y_size) throws IOException {

        dos.writeByte(0xCC);
        dos.writeInt(x_size);
        dos.writeInt(y_size);
    }

    private void writeBitset(BitSet bs) throws IOException {
        dos.writeByte(0xBA);
        final byte[] bytes = bs.toByteArray();
        dos.writeInt(bytes.length);
        dos.write(bytes);
        dos.flush();
    }

}
