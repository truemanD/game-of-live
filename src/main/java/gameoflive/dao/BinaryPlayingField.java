package gameoflive.dao;


import gameoflive.PlayingFieldIface;
import gameoflive.dao.binary.MyBitFrame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author diyanov-a
 */
public class BinaryPlayingField implements PlayingFieldDAO {

    private File file;
    private static BinaryPlayingField INSTANCE;
    private int fieldSize;

    private BinaryPlayingField(int fieldSize) {
        file = new File("PlayingField.bin");
        this.fieldSize = fieldSize;
        if (file.delete()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(BinaryPlayingField.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static synchronized BinaryPlayingField getInstance(int fieldSize) {
        if (INSTANCE == null) {
            INSTANCE = new BinaryPlayingField(fieldSize);
        }
        return INSTANCE;
    }

    private List<List<Boolean>> pfFileRead(int dayNumber) {
        List<List<Boolean>> field = new ArrayList<>();
        int rowSize = fieldSize * fieldSize % 8 == 0 ? fieldSize * fieldSize / 8 : fieldSize * fieldSize / 8 + 1;
        try {
            byte[] f = Files.readAllBytes(file.toPath());
            byte[] ba;
            int sBite = (((dayNumber - 1) * rowSize) + dayNumber - 1);
            int eBite = sBite + rowSize;
            ba = Arrays.copyOfRange(f, sBite, eBite);
            field = toField(ba);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return field;
    }

    private int pfFileWrite(PlayingFieldIface pfi) {
        int result = 0;
        try (OutputStream os = new FileOutputStream(file, true)) {
            os.write(toByteArray(pfi.getField()));
            os.flush();
            os.close();
            result = pfi.getStepCounter();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<List<Boolean>> loadDay(int dayNumber) {
        return pfFileRead(dayNumber);
    }

    @Override
    public int saveCurrentDay(PlayingFieldIface pfi) {
        return pfFileWrite(pfi);
    }

    private List<List<Boolean>> toField(byte[] ba) {
        List<List<Boolean>> field = new ArrayList<>();
        MyBitFrame bf = new MyBitFrame(fieldSize);
        bf.set(ba);
        for (int x = 0; x < fieldSize; x++) {
            ArrayList<Boolean> row = new ArrayList<Boolean>();
            for (int y = 0; y < fieldSize; y++) {
                if (bf.getValue(x, y)) {
                    row.add(Boolean.TRUE);
                } else {
                    row.add(Boolean.FALSE);
                }
            }
            field.add(row);
        }
        return field;
    }

    private byte[] toByteArray(List<List<Boolean>> field) {
        MyBitFrame bf = new MyBitFrame(fieldSize);
        for (int x = 0; x < field.size(); x++) {
            List<Boolean> row = field.get(x);
            for (int y = 0; y < row.size(); y++) {
                bf.set(x, y, row.get(y));
            }
        }
        BitSet res = bf.getFrame_mask();
        return res.toByteArray();
    }


}
