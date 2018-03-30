package gameoflive.dao;

import gameoflive.PlayingField;
import gameoflive.PlayingFieldIface;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diyanov-a
 */
public class FSPlayingField implements PlayingFieldDAO {

    private static File file;
    private static FSPlayingField fspf;

    private FSPlayingField() {
    }

    public static synchronized FSPlayingField getInstance() {
        if (fspf == null) {
            fspf = new FSPlayingField();
            file = new File("src\\playField.txt");
            if (file.delete()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(FSPlayingField.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return fspf;
    }

    List<List<Boolean>> pfFileRead(int dayNumber) {
        List<List<Boolean>> field = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str = null;
            int rowCounter = 0;
            while ((str = br.readLine()) != null) {
                if (str.startsWith("#" + dayNumber)) {
                    while (!(str = br.readLine()).contains(">")) {
                        field.add(rowCounter, decode(str));
                        rowCounter++;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return field;
    }

    int pfFileWrite(PlayingField pf) {
        int result = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.append("#" + pf.getStepCounter() + "\r\n");
            for (List<Boolean> row : pf.getField()) {
                bw.append(code(row));
            }
            bw.append(">\r\n");
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
    public int saveCurrentDay(PlayingFieldIface pf) {
        return pfFileWrite((PlayingField) PlayingField.getInstance());
    }

    private String code(List<Boolean> row) {
        StringBuilder sb = new StringBuilder();
        for (Object value : row) {
            if ((boolean) value) {
                sb.append("1");
            }
            if (!(boolean) value) {
                sb.append("0");
            }
        }
        sb.append("\r\n");
        return sb.toString();
    }

    private List<Boolean> decode(String str) {
        List<Boolean> row = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                row.add(Boolean.TRUE);
            }
            if (str.charAt(i) == '0') {
                row.add(Boolean.FALSE);
            }
        }
        return row;
    }


    boolean fileWrite(String str) {
        boolean result = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.append(new Date() + "\r\n");
            result = true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
